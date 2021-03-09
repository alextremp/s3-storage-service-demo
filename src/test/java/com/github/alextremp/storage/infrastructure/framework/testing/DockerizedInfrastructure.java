package com.github.alextremp.storage.infrastructure.framework.testing;

import java.io.File;
import javax.annotation.PreDestroy;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;
import org.testcontainers.containers.wait.strategy.WaitStrategy;

import static java.lang.String.format;
import static org.testcontainers.containers.wait.strategy.Wait.forListeningPort;
import static org.testcontainers.containers.wait.strategy.Wait.forLogMessage;
import static org.testcontainers.containers.wait.strategy.WaitAllStrategy.Mode.WITH_INDIVIDUAL_TIMEOUTS_ONLY;

@Component
class DockerizedInfrastructure {

  private static final File DOCKER_COMPOSE = new File("docker-compose.yml");

  private static WaitStrategy S3_STORAGE_WAIT_STRATEGY = new WaitAllStrategy(WITH_INDIVIDUAL_TIMEOUTS_ONLY)
        .withStrategy(forListeningPort())
        .withStrategy(forLogMessage(".*make_bucket.*", 1));

  private static final String S3_STORAGE_SERVICE = "s3-storage";
  private static final Integer S3_STORAGE_PORT = 4566;
  private static final String SPRING_S3_STORAGE_ENDPOINT = "s3-storage.endpoint";

  private final DockerComposeContainer dockerServices;

  public DockerizedInfrastructure(ConfigurableApplicationContext configurableApplicationContext) {
    dockerServices = new DockerComposeContainer(DOCKER_COMPOSE)
          .withLocalCompose(true)
          .waitingFor("s3-storage", S3_STORAGE_WAIT_STRATEGY)
          .withExposedService(S3_STORAGE_SERVICE, S3_STORAGE_PORT);

    dockerServices.start();

    TestPropertyValues.of(
          format("%s=%s", SPRING_S3_STORAGE_ENDPOINT, "http://localhost:" + dockerServices.getServicePort(S3_STORAGE_SERVICE, S3_STORAGE_PORT))
    ).applyTo(configurableApplicationContext.getEnvironment());
  }

  @PreDestroy
  void preDestroy() {
    dockerServices.stop();
  }
}
