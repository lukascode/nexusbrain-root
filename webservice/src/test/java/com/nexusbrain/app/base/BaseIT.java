package com.nexusbrain.app.base;

import com.nexusbrain.app.NexusbrainApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@SpringBootTest(classes = NexusbrainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(MockitoTestExecutionListener.class)
@TestPropertySource(locations = "/application-test.properties")
@ActiveProfiles("test")
public abstract class BaseIT extends AbstractTestNGSpringContextTests {

    @Autowired
    protected TestRestTemplate testRestTemplate;

}
