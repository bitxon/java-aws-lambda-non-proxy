package bitxon.aws.spring;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfig {

    @Bean
    public LocalStackContainer localStack() {
        return new LocalStackContainer(DockerImageName.parse("localstack/localstack:latest"))
            .withServices(LocalStackContainer.Service.DYNAMODB)
            .withCopyToContainer(
                MountableFile.forClasspathResource("scripts"),
                "/etc/localstack/init/ready.d"
            );
    }

    @Bean
    public DynamicPropertyRegistrar propertiesOverride(LocalStackContainer localStack) {
        return registry -> {
            registry.add("spring.cloud.aws.endpoint", localStack::getEndpoint);
            registry.add("spring.cloud.aws.region.static", localStack::getRegion);
            registry.add("spring.cloud.aws.credentials.access-key", localStack::getAccessKey);
            registry.add("spring.cloud.aws.credentials.secret-key", localStack::getSecretKey);
            registry.add("TABLE_NAME", () -> "Orders");
        };
    }

}
