package jobOpeningManagement.application;

import applicationManagement.domain.Candidate;
import applicationManagement.repositories.CandidateRepository;
import infrastructure.persistance.PersistenceContext;

//import org.antlr.v4.runtime.CharStreams;
//import org.antlr.v4.runtime.CommonTokenStream;
//import org.antlr.v4.runtime.tree.ParseTree;
//import org.antlr.v4.runtime.tree.ParseTreeWalker;

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
        Optional<Candidate> candidate = getCandidateByEmail(email);
        if(candidate.isEmpty()){
            return false;
        }

        //validate the file's synthax, using ANTLR


        candidate.get().setRequirementsFilePath(filePath);
        //create a local version of the file in the project, so that it can be called in the future




        return true;
    }

//    private boolean isValidFile(String filePath) {
//        try {
//            String content = new String(Files.readAllBytes(Paths.get(filePath)));
//            CandidateFileLexer lexer = new CandidateFileLexer(CharStreams.fromString(content));
//            CommonTokenStream tokens = new CommonTokenStream(lexer);
//            CandidateFileParser parser = new CandidateFileParser(tokens);
//            ParseTree tree = parser.file();
//
//            CandidateFileCustomListener listener = new CandidateFileCustomListener();
//            ParseTreeWalker walker = new ParseTreeWalker();
//            walker.walk(listener, tree);
//
//            return listener.isValid();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

}
