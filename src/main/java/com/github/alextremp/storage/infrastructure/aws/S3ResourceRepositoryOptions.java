package com.github.alextremp.storage.infrastructure.aws;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
@NoArgsConstructor
public class S3ResourceRepositoryOptions {
  String region;
  String accessKey;
  String secretKey;
  String bucket;
  String endpoint;
  String originFor;

  public Boolean hasCustomEndpoint() {
    return StringUtils.isNotBlank(endpoint);
  }

  public Boolean hasOriginReference() {
    return StringUtils.isNotBlank(originFor);
  }
}
