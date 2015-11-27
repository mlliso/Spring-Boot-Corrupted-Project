package pl.mcx.corrupted.project.integration.tcp;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "toSmTcp")
public interface SMGateway {

    byte[] sendAndReceiveBytes(byte[] in);
}
