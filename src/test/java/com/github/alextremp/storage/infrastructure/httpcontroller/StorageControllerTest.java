package com.github.alextremp.storage.infrastructure.httpcontroller;

import com.github.alextremp.storage.infrastructure.framework.testing.AbstractIntegrationTest;
import java.io.IOException;
import java.net.URL;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.endsWith;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StorageControllerTest extends AbstractIntegrationTest {

  @Test
  public void itShouldStoreAFile() throws IOException {
    String givenFilename = "demo-image.png";
    String givenPath = "/demo/" + givenFilename;
    URL givenResource = getClass().getResource("/fixtures/" + givenFilename);

    given()
          .multiPart("file", givenFilename, givenResource.openStream(), MediaType.IMAGE_PNG_VALUE)
          .param("path", givenPath)
          .param("maxAge", 3600)
          .when().post("/storage")
          .then()
          .assertThat(status().isOk())
          .body("url", endsWith(givenPath));
  }

}