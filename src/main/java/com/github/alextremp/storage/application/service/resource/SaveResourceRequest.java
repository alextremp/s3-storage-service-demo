package com.github.alextremp.storage.application.service.resource;

import java.io.InputStream;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class SaveResourceRequest {

  String path;
  InputStream inputStream;
  Long contentLength;
  Integer maxAge;
}
