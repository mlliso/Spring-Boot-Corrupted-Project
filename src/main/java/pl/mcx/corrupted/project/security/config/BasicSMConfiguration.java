package pl.mcx.corrupted.project.security.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.FailoverClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.messaging.MessageHandler;
import pl.mcx.corrupted.project.integration.tcp.SMGateway;
import pl.mcx.corrupted.project.security.sm.basic.BasicSM;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class BasicSMConfiguration {

    private int timeout = 5000;

    @Bean
    public FailoverClientConnectionFactory basicSmConnectionFactory() {

        List<AbstractClientConnectionFactory> connectionFactories = new ArrayList<>();
        connectionFactories.add(new TcpNetClientConnectionFactory("localhost", 4444));
        connectionFactories.add(new TcpNetClientConnectionFactory("localhost", 5555));
        FailoverClientConnectionFactory failoverClientConnectionFactory = new FailoverClientConnectionFactory(
                connectionFactories);
        failoverClientConnectionFactory.setSingleUse(false);
        return failoverClientConnectionFactory;
    }

    @Bean
    @ServiceActivator(inputChannel = "toSmTcp")
    public MessageHandler toSMTcpGate(
            @Qualifier("basicSmConnectionFactory") final FailoverClientConnectionFactory basicSmConnectionFactory
    ) {
        TcpOutboundGateway gate = new TcpOutboundGateway();
        gate.setConnectionFactory(basicSmConnectionFactory);
        gate.setRemoteTimeout(timeout);
        gate.setRequestTimeout(timeout);
        return gate;
    }


    @Bean
    public BasicSM basicSM(final SMGateway smGateway) {
        return new BasicSM(smGateway);
    }

}
