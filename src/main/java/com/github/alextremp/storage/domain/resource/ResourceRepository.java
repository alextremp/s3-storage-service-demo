package com.github.alextremp.storage.domain.resource;

import com.github.alextremp.storage.domain.destination.Destination;

public interface ResourceRepository {

  Destination save(StreamableResource streamableResource, ResourceOptions resourceOptions);
}
