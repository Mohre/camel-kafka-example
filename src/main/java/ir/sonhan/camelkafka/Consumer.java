package ir.sonhan.camelkafka;

import org.apache.camel.builder.AggregationStrategies;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Consumer extends RouteBuilder {

    @Value("${consumer.start}")
    private String startPoint;

    @Value("${consumer.end}")
    private String endPoint;

    @Override
    public void configure() {
        from(startPoint)
                .aggregate(constant(true), AggregationStrategies.bean(Integer.class, "sum"))
                .completionInterval(6000)
                .setBody(simple ("Sum: ${body}"))
                .to(endPoint);
    }
}
