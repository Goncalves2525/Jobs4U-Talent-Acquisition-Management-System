package jobOpeningManagement.parsing;

import antlr.CandidateRequirementsGrammarValidationParser;
import org.antlr.v4.runtime.tree.ErrorNode;

public class CandidateRequirementsCustomListener extends antlr.CandidateRequirementsGrammarValidationBaseListener {
    private boolean isValid = true;

    @Override
    public void visitErrorNode(ErrorNode node) {
        isValid = false;
    }

    @Override
    public void exitValue(CandidateRequirementsGrammarValidationParser valueContext) {

    }

    public boolean isValid() {
        return isValid;
    }
}
