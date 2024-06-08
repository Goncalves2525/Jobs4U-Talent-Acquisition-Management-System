package applicationManagement.application;

import jobOpeningManagement.application.GenerateCandidateFieldsFileController;
import jobOpeningManagement.repositories.JobOpeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import plugins.Plugin;
import plugins.PluginLoader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CandidateControllerTest {
    private GenerateCandidateFieldsFileController controller;
    private  PluginLoader pluginLoader;

    @Mock
    private JobOpeningRepository jobOpeningRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new GenerateCandidateFieldsFileController(jobOpeningRepository);
    }

    @Test
    void ensureCandidates() {
        List<Plugin> plugins = controller.loadPlugins();
        assertNotNull(plugins);
    }
}
