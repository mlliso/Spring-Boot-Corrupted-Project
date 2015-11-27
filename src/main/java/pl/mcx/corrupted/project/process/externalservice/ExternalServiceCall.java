package pl.mcx.corrupted.project.process.externalservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mcx.corrupted.project.integration.tcp.ExternalServiceGateway;

import java.nio.ByteBuffer;
import java.util.Map;

@Component
public final class ExternalServiceCall {

    public static final String ENDPOINT = "direct:externalServiceCall";

    @Autowired
    private ExternalServiceGateway gateway;

    public void execute(final Map<String, Object> body, final Map<String, Object> headers) throws Exception {
        byte[] bytes = ((ByteBuffer) headers.get("requestBuffer")).array();
        headers.put("reponseBuffer", gateway.viaTcp(bytes));

    }

    public String getEndpoint() {
        return ENDPOINT;
    }
}
