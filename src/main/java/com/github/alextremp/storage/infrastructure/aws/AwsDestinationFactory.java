package com.github.alextremp.storage.infrastructure.aws;

import com.github.alextremp.storage.domain.destination.Destination;
import com.github.alextremp.storage.domain.destination.DestinationFactory;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class AwsDestinationFactory implements DestinationFactory {

  private static final String HTTPS_PROTOCOL = "https://";
  private static final String AWS_S3_DOMAIN_TEMPLATE = "s3-%s.amazonaws.com";

  private final S3ResourceRepositoryOptions options;

  public AwsDestinationFactory(S3ResourceRepositoryOptions options) {
    this.options = options;
  }

  @Override
  @SneakyThrows
  public Destination create(String path) {
    StringBuilder rootBuilder = new StringBuilder();
    if (options.hasOriginReference()) {
      rootBuilder.append(HTTPS_PROTOCOL)
            .append(options.getOriginFor());
    } else if (options.hasCustomEndpoint()) {
      rootBuilder.append(options.getEndpoint())
            .append(Destination.SEPARATOR)
            .append(options.getBucket());
    } else {
      rootBuilder.append(HTTPS_PROTOCOL)
            .append(String.format(AWS_S3_DOMAIN_TEMPLATE, options.getRegion()))
            .append(Destination.SEPARATOR)
            .append(options.getBucket());
    }
    return new Destination(rootBuilder.toString(), path);
  }
}
