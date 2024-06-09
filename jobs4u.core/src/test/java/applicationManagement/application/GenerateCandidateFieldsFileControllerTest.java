package applicationManagement.application;
import jobOpeningManagement.application.GenerateCandidateFieldsFileController;
import jobOpeningManagement.repositories.JobOpeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import plugins.Plugin;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateCandidateFieldsFileControllerTest {

    @Mock
    private JobOpeningRepository jobOpeningRepository;

    private GenerateCandidateFieldsFileController controller;

    @BeforeEach
    public void setup() {
        controller = new GenerateCandidateFieldsFileController(jobOpeningRepository);
    }


    @Test
    void testLoadPlugins() {
        List<Plugin> plugins = controller.loadPlugins();
        assertNotNull(plugins);
    }
}
