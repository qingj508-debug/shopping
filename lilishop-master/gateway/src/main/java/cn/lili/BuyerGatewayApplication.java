package cn.lili;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Buyer gateway entry point (port 8888). Routes /buyer/** requests to business microservices.
 */
@SpringBootApplication
public class BuyerGatewayApplication {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(BuyerGatewayApplication.class, args);
    }
}