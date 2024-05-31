package applicationManagement.application;
import jobOpeningManagement.application.GenerateCandidateFieldsFileController;
import org.junit.jupiter.api.Test;
import plugins.Plugin;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateCandidateFieldsFileControllerTest {

    @Test
    void testLoadPlugins() {
        GenerateCandidateFieldsFileController controller = new GenerateCandidateFieldsFileController();
        List<Plugin> plugins = controller.loadPlugins();

        assertNotNull(plugins);
    }
}
