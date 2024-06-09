package jobOpeningManagement.application;

import applicationManagement.domain.Candidate;
import applicationManagement.repositories.CandidateRepository;
import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.parsing.CandidateRequirementsCustomListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class UploadCandidateRequirementsController {

    private final CandidateRepository candidateRepository = PersistenceContext.repositories().candidates();

    public Iterable<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Optional<Candidate> getCandidateByEmail(String email) {
        return candidateRepository.ofIdentity(email);
    }

    public boolean uploadCandidateRequirementsFile(String email, String filePath) {
        Optional<Candidate> candidateOpt = candidateRepository.ofIdentity(email);
        if (candidateOpt.isEmpty() || !isValidFile(filePath)) {
            return false;
        }

        Candidate existingCandidate = candidateOpt.get();
        existingCandidate.setRequirementsFilePath(filePath);

        try {
            // Save the updated candidate
            candidateRepository.save(existingCandidate);
            return true;
        } catch (Exception e) {
            // Log the error details
            System.out.println("Error saving candidate requirements file");
            return false;
        }
    }

    private boolean isValidFile(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            gen.antlr.CandidateRequirementsGrammarValidationLexer lexer = new gen.antlr.CandidateRequirementsGrammarValidationLexer(CharStreams.fromString(content));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            antlr.CandidateRequirementsGrammarValidationParser parser = new antlr.CandidateRequirementsGrammarValidationParser(tokens);
            ParseTree tree = parser.file();

            CandidateRequirementsCustomListener listener = new CandidateRequirementsCustomListener();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk((ParseTreeListener) listener, tree);

            return listener.isValid();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
