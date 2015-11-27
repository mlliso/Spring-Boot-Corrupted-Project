package pl.mcx.corrupted.project.integration.tcp;

import org.springframework.integration.annotation.MessagingGateway;


@MessagingGateway(defaultRequestChannel = "toExternalServiceTcp")
public interface ExternalServiceGateway {

    byte[] viaTcp(byte[] in);
}
