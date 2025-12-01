package bitxon.aws.quarkus;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.util.Map;

public class TestcontainersLifecycleManager implements QuarkusTestResourceLifecycleManager {
    private LocalStackContainer localStack;

    @Override
    public void init(Map<String, String> initArgs) {
        localStack = new LocalStackContainer(DockerImageName.parse("localstack/localstack:latest"))
            .withServices(LocalStackContainer.Service.DYNAMODB)
            .withCopyToContainer(
                MountableFile.forClasspathResource("scripts"),
                "/etc/localstack/init/ready.d"
            );
    }

    @Override
    public Map<String, String> start() {
        localStack.start();

        return Map.of(
            "quarkus.dynamodb.endpoint-override", localStack.getEndpoint().toString(),
            "quarkus.dynamodb.aws.region", localStack.getRegion(),
            "quarkus.dynamodb.aws.credentials.type", "static",
            "quarkus.dynamodb.aws.credentials.static-provider.access-key-id", localStack.getAccessKey(),
            "quarkus.dynamodb.aws.credentials.static-provider.secret-access-key", localStack.getSecretKey(),
            "TABLE_NAME", "Orders"
        );
    }

    @Override
    public void stop() {
        if (localStack != null) {
            localStack.stop();
        }
    }
}
