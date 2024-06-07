package applicationManagement.application;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ListApplicationsControllerTest {

    @Test
    void applicationNotFound (){
        ListApplicationsController ctrl = new ListApplicationsController();

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        System.setOut(new PrintStream(result));
        ctrl.getApplication(1000L);

        assertEquals("Applications not found!\n", result.toString());
    }
}