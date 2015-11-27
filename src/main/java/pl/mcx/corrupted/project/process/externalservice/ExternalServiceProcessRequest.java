package pl.mcx.corrupted.project.process.externalservice;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public final class ExternalServiceProcessRequest extends SpringRouteBuilder {

    public static final String ENDPOINT = "direct:externalServiceProcessRequest";

    /**
     * Configures process of sending request to external Service.
     *
     * @throws Exception when process will fail
     */
    @Override
    public void configure() throws Exception {
        onException(Exception.class).handled(true).to("direct:error");

        from(ENDPOINT).routeId("externalServiceProcessFinancialProcessRequest")
                .to(ExternalServiceCall.ENDPOINT);
    }
}
