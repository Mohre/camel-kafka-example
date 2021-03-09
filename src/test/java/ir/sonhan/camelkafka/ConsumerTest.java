package ir.sonhan.camelkafka;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Consumer.class)
@TestPropertySource("/test.properties")
public class ConsumerTest extends CamelTestSupport {

    @Autowired
    Consumer consumer;

    @EndpointInject("mock:result")
    protected MockEndpoint mock;

    @Produce("direct:start")
    protected ProducerTemplate producer;

    @Override
    protected RouteBuilder createRouteBuilder(){
        return consumer;
    }

    @Test
    public void consumer_aggregateWithDelay() throws Exception {
        mock.expectedMessageCount(2);
        mock.expectedBodiesReceived("Sum: 10", "Sum: 5");

        int body = 1;
        for (; body < 5; body++)
            producer.sendBody(body);

        Thread.sleep(7000);
        producer.sendBody(body);

        assertMockEndpointsSatisfied();
    }

}