package applicationManagement.application;
import applicationManagement.domain.Application;
import jobOpeningManagement.application.GenerateAnswerCollectionFileController;
import jobOpeningManagement.repositories.JobOpeningRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import plugins.PluginLoader;
import plugins.Plugin;

import java.lang.reflect.Method;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class GenerateAnswerCollectionFileTest {

    @Mock
    private PluginLoader pluginLoader;

    @Mock
    private JobOpeningRepository jobOpeningRepository;

    private GenerateAnswerCollectionFileController ctrl;

    private Plugin plugin;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ctrl = new GenerateAnswerCollectionFileController(jobOpeningRepository);
        plugin = mock(Plugin.class);
    }

//    @Test
//    void ensureAFileIsGenerated()throws Exception {
//        when(pluginLoader.loadPlugin(anyString())).thenReturn(plugin);
//
//        Object pluginInstance = mock(Object.class);
//        when(plugin.getPluginInstance()).thenReturn(pluginInstance);
//
//        Method exportMethod = pluginInstance.getClass().getMethod("exportTemplateFile", String.class);
//
//        ctrl.generateAnswerCollectionFile("pluginPath");
//
//        verify(exportMethod).invoke(pluginInstance, "plugins/interview/txt/answerSheet.txt");
//    }
}
