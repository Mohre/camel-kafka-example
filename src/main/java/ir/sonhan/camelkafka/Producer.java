package ir.sonhan.camelkafka;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Producer extends RouteBuilder {

    @Override
    public void configure() {
        Random random = new Random();
        from("timer://producerTimer?fixedRate=true&period=1000")
                .process(exchange -> exchange.getIn().setBody(random.nextInt(10)))
                .to("kafka:math?brokers=localhost:9092")
                .log("# ${body}");
    }
}