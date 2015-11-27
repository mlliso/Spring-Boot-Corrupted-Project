package pl.mcx.corrupted.project.api;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.mcx.corrupted.project.process.BasicProcess;

import java.util.Map;

@RequestMapping(value = "/",
                consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class HostController {

    @Produce(uri = BasicProcess.ENDPOINT)
    private ProducerTemplate basicProcess;
    @Autowired
    private CamelContext camelContext;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Object executeProcess(@RequestBody Map<String, Object> requestBody) {

        Exchange requestExchange = new DefaultExchange(camelContext);
        requestExchange.setPattern(ExchangePattern.InOut);
        requestExchange.getIn().setBody(requestBody);

        Exchange responseExchange = basicProcess.send(requestExchange);

        HttpStatus httpStatus = (HttpStatus) responseExchange.getOut().getHeader(Exchange.HTTP_RESPONSE_CODE);
        if (httpStatus == null) {
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity<>(responseExchange.getOut().getBody(), httpStatus);
    }
}
