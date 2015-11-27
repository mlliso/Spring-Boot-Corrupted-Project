package pl.mcx.corrupted.project.integration.external.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.FailoverClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.ByteArrayLengthHeaderSerializer;
import org.springframework.messaging.MessageHandler;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ExternalServiceConfiguration {

    private int timeout = 5000;

    @Bean
    @Scope(value = "prototype")
    public FailoverClientConnectionFactory serviceConnectionFactory()
            throws Exception {
        List<String> endpoints = Lists.newArrayList("localhost:12345", "localhost:123456");

        List<AbstractClientConnectionFactory> connectionFactories = new ArrayList<>();
        for (String endpoint : endpoints) {
            final String[] endpointParts = endpoint.split(":");
            connectionFactories.add(createConnectionFactory(endpointParts[0],
                    Integer.valueOf(endpointParts[1])));
        }
        FailoverClientConnectionFactory failoverClientConnectionFactory = new FailoverClientConnectionFactory(
                connectionFactories);
        failoverClientConnectionFactory.setSingleUse(true);
        return failoverClientConnectionFactory;
    }

    @Bean
    @ServiceActivator(inputChannel = "toExternalServiceTcp")
    public MessageHandler tcpExternalServiceGate(
            @Qualifier("serviceConnectionFactory") FailoverClientConnectionFactory failoverServiceConnectionFactory) {
        TcpOutboundGateway gate = new TcpOutboundGateway();
        gate.setConnectionFactory(failoverServiceConnectionFactory);
        gate.setRemoteTimeout(timeout);
        gate.setRequestTimeout(timeout);
        return gate;
    }

    private TcpNetClientConnectionFactory createConnectionFactory(final String host,
                                                                  final Integer port) throws Exception {
        TcpNetClientConnectionFactory tcpNetClientConnectionFactory =
                new TcpNetClientConnectionFactory(host, port);
        final ByteArrayLengthHeaderSerializer byteArrayLengthHeaderSerializer =
                new ByteArrayLengthHeaderSerializer(ByteArrayLengthHeaderSerializer.HEADER_SIZE_UNSIGNED_SHORT);

        tcpNetClientConnectionFactory.setDeserializer(byteArrayLengthHeaderSerializer);
        tcpNetClientConnectionFactory.setSerializer(byteArrayLengthHeaderSerializer);
        tcpNetClientConnectionFactory.setSingleUse(true);
        tcpNetClientConnectionFactory.setSoTimeout(timeout);
        return tcpNetClientConnectionFactory;
    }
}
