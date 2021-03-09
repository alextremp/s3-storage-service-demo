package com.github.alextremp.storage.domain.resource;

import java.io.InputStream;

public class StreamableResource {

  private final InputStream stream;
  private final Long contentLength;

  public StreamableResource(InputStream stream, Long contentLength) {
    this.stream = stream;
    this.contentLength = contentLength;
  }

  public InputStream stream() {
    return stream;
  }

  public Long contentLength() {
    return contentLength;
  }
}
