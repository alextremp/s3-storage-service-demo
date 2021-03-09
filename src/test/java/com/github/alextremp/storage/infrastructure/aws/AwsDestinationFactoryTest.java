package com.github.alextremp.storage.infrastructure.aws;

import com.github.alextremp.storage.domain.destination.Destination;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AwsDestinationFactoryTest {

  @Test
  public void itShouldGenerateACustomEndpointUrl() {
    String givenPath = "/what/ever.png";
    S3ResourceRepositoryOptions givenOptions = new S3ResourceRepositoryOptions()
          .setEndpoint("http://localhost:14000")
          .setBucket("my.storage.bucket")
          .setRegion("eu-west-1");
    AwsDestinationFactory factory = new AwsDestinationFactory(givenOptions);
    Destination destination = factory.create(givenPath);
    assertEquals(destination.url().toExternalForm(), "http://localhost:14000/my.storage.bucket/what/ever.png");
  }

  @Test
  public void itShouldGenerateAS3Url() {
    String givenPath = "/what/ever.png";
    S3ResourceRepositoryOptions givenOptions = new S3ResourceRepositoryOptions()
          .setBucket("my.storage.bucket")
          .setRegion("eu-west-1");
    AwsDestinationFactory factory = new AwsDestinationFactory(givenOptions);
    Destination destination = factory.create(givenPath);
    assertEquals(destination.url().toExternalForm(), "https://s3-eu-west-1.amazonaws.com/my.storage.bucket/what/ever.png");
  }

  @Test
  public void itShouldGenerateACDNUrl() {
    String givenPath = "/what/ever.png";
    S3ResourceRepositoryOptions givenOptions = new S3ResourceRepositoryOptions()
          .setBucket("my.storage.bucket")
          .setRegion("eu-west-1")
          .setOriginFor("s.dcdn.es");
    AwsDestinationFactory factory = new AwsDestinationFactory(givenOptions);
    Destination destination = factory.create(givenPath);
    assertEquals(destination.url().toExternalForm(), "https://s.dcdn.es/what/ever.png");
  }
}