package com.github.alextremp.storage.infrastructure.aws;

import com.github.alextremp.storage.domain.destination.Destination;
import com.github.alextremp.storage.domain.destination.DestinationFactory;
import com.github.alextremp.storage.domain.resource.ResourceOptions;
import com.github.alextremp.storage.domain.resource.ResourceRepository;
import com.github.alextremp.storage.domain.resource.StreamableResource;
import java.net.URLConnection;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Repository
public class S3ResourceRepository implements ResourceRepository {

  private final S3Client s3Client;
  private final S3ResourceRepositoryOptions repositoryOptions;
  private final DestinationFactory destinationFactory;

  public S3ResourceRepository(S3Client s3Client,
                              S3ResourceRepositoryOptions repositoryOptions,
                              DestinationFactory destinationFactory) {
    this.s3Client = s3Client;
    this.repositoryOptions = repositoryOptions;
    this.destinationFactory = destinationFactory;
  }

  @Override
  public Destination save(StreamableResource streamableResource, ResourceOptions resourceOptions) {
    PutObjectRequest.Builder requestBuilder = PutObjectRequest.builder()
          // the target bucket
          .bucket(repositoryOptions.getBucket())
          // the target path in the bucket
          .key(resourceOptions.getPath());

    // setting the content-type makes it web-friendly when being read
    requestBuilder.contentType(URLConnection.guessContentTypeFromName(resourceOptions.getPath()));
    // it's highly recommendable to specify the cache-control for presupposed repetitive reads, specially if it's combined with CloudFront
    requestBuilder.cacheControl(String.format("public, max-age=%s", resourceOptions.getMaxAge()));

    if (!repositoryOptions.hasOriginReference()) {
      // when needed to be read directly from S3, no need if it's a bucket linked to CloudFront
      requestBuilder.acl(ObjectCannedACL.PUBLIC_READ);
    }

    PutObjectRequest objectRequest = requestBuilder.build();
    s3Client.putObject(objectRequest, RequestBody.fromInputStream(streamableResource.stream(), streamableResource.contentLength()));

    return destinationFactory.create(resourceOptions.getPath());
  }
}
