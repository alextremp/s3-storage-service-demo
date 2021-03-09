package com.github.alextremp.storage.infrastructure.httpcontroller;

import com.github.alextremp.storage.application.service.resource.SaveResourceRequest;
import com.github.alextremp.storage.application.service.resource.SaveResourceResponse;
import com.github.alextremp.storage.application.service.resource.SaveResourceUseCase;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage")
public class StorageController {

  private final SaveResourceUseCase saveResourceUseCase;

  public StorageController(SaveResourceUseCase saveResourceUseCase) {
    this.saveResourceUseCase = saveResourceUseCase;
  }

  @PostMapping
  @SneakyThrows
  public ResponseEntity<SaveResourceResponse> post(
        @RequestParam String path,
        @RequestParam MultipartFile file,
        @RequestParam(required = false, defaultValue = "31536000") Integer maxAge) {

    SaveResourceRequest request = new SaveResourceRequest()
          .setPath(path)
          .setInputStream(file.getInputStream())
          .setContentLength(file.getSize())
          .setMaxAge(maxAge);

    SaveResourceResponse response = saveResourceUseCase.execute(request);
    return ResponseEntity.ok(response);
  }
}
