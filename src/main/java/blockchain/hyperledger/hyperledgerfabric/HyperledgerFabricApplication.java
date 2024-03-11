package blockchain.hyperledger.hyperledgerfabric;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;

@SpringBootApplication(exclude = {
        ContextInstanceDataAutoConfiguration.class,
        ContextStackAutoConfiguration.class,
        ContextRegionProviderAutoConfiguration.class
})
public class HyperledgerFabricApplication {

    public static void main(String[] args) {
        SpringApplication.run(HyperledgerFabricApplication.class, args);
    }
}
