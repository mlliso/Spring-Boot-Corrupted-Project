package pl.mcx.corrupted.project.process;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;
import pl.mcx.corrupted.project.process.externalservice.ExternalServiceProcess;


@Component
public final class BasicProcess extends SpringRouteBuilder {

    public static final String ENDPOINT = "direct:basicProcess";

    @Override
    public void configure() throws Exception {

        from(ENDPOINT).routeId("basicProcess")
                .to(ExternalServiceProcess.ENDPOINT);
    }
}
