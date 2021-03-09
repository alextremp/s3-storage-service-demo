package com.github.alextremp.storage.domain.destination;

public interface DestinationFactory {

  Destination create(String path);
}
