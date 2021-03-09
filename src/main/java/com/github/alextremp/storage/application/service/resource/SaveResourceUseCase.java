package com.github.alextremp.storage.application.service.resource;

import com.github.alextremp.storage.domain.destination.Destination;
import com.github.alextremp.storage.domain.resource.ResourceOptions;
import com.github.alextremp.storage.domain.resource.ResourceRepository;
import com.github.alextremp.storage.domain.resource.StreamableResource;
import org.springframework.stereotype.Service;

@Service
public class SaveResourceUseCase {

  private final ResourceRepository resourceRepository;

  public SaveResourceUseCase(ResourceRepository resourceRepository) {
    this.resourceRepository = resourceRepository;
  }

  public SaveResourceResponse execute(SaveResourceRequest request) {
    StreamableResource streamableResource = new StreamableResource(
          request.getInputStream(),
          request.getContentLength()
    );
    ResourceOptions options = new ResourceOptions()
          .setPath(request.getPath())
          .setMaxAge(request.getMaxAge());
    Destination destination = resourceRepository.save(streamableResource, options);
    return new SaveResourceResponse()
          .setUrl(destination.url().toExternalForm());
  }
}
