package com.github.alextremp.storage.infrastructure.framework.configuration;

import com.github.alextremp.storage.infrastructure.aws.S3ResourceRepositoryOptions;
import java.net.URI;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

@Configuration
@ComponentScan("com.github.alextremp.storage.infrastructure.aws")
public class AwsConfiguration {

  @Bean
  @ConfigurationProperties(prefix = "s3-storage")
  public S3ResourceRepositoryOptions s3ResourceRepositoryOptions() {
    return new S3ResourceRepositoryOptions();
  }

  @Bean
  public S3Client s3Client(S3ResourceRepositoryOptions s3ResourceRepositoryOptions) {
    S3ClientBuilder s3ClientBuilder = S3Client.builder()
          .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(
                s3ResourceRepositoryOptions.getAccessKey(),
                s3ResourceRepositoryOptions.getSecretKey()
          )))
          .region(Region.of(s3ResourceRepositoryOptions.getRegion()));

    if (s3ResourceRepositoryOptions.hasCustomEndpoint()) {
      s3ClientBuilder.endpointOverride(URI.create(s3ResourceRepositoryOptions.getEndpoint()));
    }
    return s3ClientBuilder.build();
  }
}
