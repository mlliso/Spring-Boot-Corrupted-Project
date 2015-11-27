package pl.mcx.corrupted.project.process.externalservice;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;


@Component
public final class ExternalServiceProcess extends SpringRouteBuilder {
    public static final String ENDPOINT = "direct:externalServiceProcess";

    /**
     * Configures business process with External Service.
     *
     * @throws Exception for EService process
     */
    @Override
    public void configure() throws Exception {
        onException(Exception.class).handled(true).to("direct:error");

        from(ENDPOINT).routeId("externalServiceProcess")
                .to(ExternalServiceProcessRequest.ENDPOINT);
    }
}
