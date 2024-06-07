package applicationManagement.application;
import applicationManagement.domain.Application;
import applicationManagement.domain.RequirementsResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class SelectInterviewModelTest {

    @Test
    void ensureApplicationDoesNotHaveInterviewModel() {
        // Arrange
        Application application = new Application("jobReference", null, null, null, null, null, null,"","", RequirementsResult.REJECTED);
        // Act
        boolean result = application.checkIfApplicationHasInterviewModel();
        // Assert
        Assertions.assertFalse(result);
    }
}
