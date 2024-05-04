package applicationManagement.application;

import org.junit.jupiter.api.Test;
import plugins.Plugin;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CandidateControllerTest {

    @Test
    void ensureCandidates() {
        GenerateCandidateFieldsFileController controller = new GenerateCandidateFieldsFileController();
        List<Plugin> plugins = controller.loadPlugins();

        assertNotNull(plugins);
    }
}
