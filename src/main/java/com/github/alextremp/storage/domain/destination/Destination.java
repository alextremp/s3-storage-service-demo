package com.github.alextremp.storage.domain.destination;

import java.net.URL;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

public class Destination {

  public static final String SEPARATOR = "/";
  private final String root;
  private final String path;

  public Destination(String root, String path) {
    this.root = root;
    this.path = StringUtils.removeStart(path, SEPARATOR);
  }

  @SneakyThrows
  public URL url() {
    return new URL(root + SEPARATOR + path);
  }
}
