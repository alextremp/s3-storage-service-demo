package com.github.alextremp.storage.infrastructure.framework.testing;

import com.github.alextremp.storage.infrastructure.framework.StorageApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.mockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
      classes = {DockerizedInfrastructure.class, StorageApplication.class},
      properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
public class AbstractIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @BeforeEach
  public void setUp() {
    mockMvc(mvc);
  }
}
