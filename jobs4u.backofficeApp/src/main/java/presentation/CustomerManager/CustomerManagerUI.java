package presentation.CustomerManager;

import applicationManagement.repositories.ApplicationRepository;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import infrastructure.persistance.PersistenceContext;
import textformat.AnsiColor;

import java.util.ArrayList;
import java.util.List;

public class CustomerManagerUI {
    ApplicationRepository appRepo = PersistenceContext.repositories().applications();

    public void doShow(AuthzUI authzUI) throws Exception {

        // set option variable, list of options, selection message, and exit name (eg.: exit / cancel / etc.)
        int option;
        List<String> options = new ArrayList<>();

        options.add("Register Customer");                               // 1
        options.add("Register Job Opening");                            // 2
        options.add("Edit Job Opening");                                // 3
        options.add("List Job Openings");                               // 4
        options.add("List Candidate Personal Data");                    // 5
        options.add("List Applications For Job Opening");               // 6
        options.add("Generate Job Requirement Specification File");     // 7
        options.add("Select Job Requirements Specifications File");     // 8
        options.add("Verify Job Requirements Specification File");      // 9
        options.add("Register Interview Date");                         // 10
        options.add("Generate Interview File");                         // 11
        options.add("Select Interview Model");                          // 12
        options.add("Evaluate Interview Model");                        // 13
        options.add("List Applications by Interview Grade");            // 14
        options.add("Check Application Data");                          // 15
        options.add("Notification Menu");                               // 16
        options.add("Define Recruitment Phase");                        // 17

        String message = "What do you want to do?";
        String exit = "Exit";

        // run options menu
        do {
            // build UI header:
            ConsoleUtils.buildUiHeader("Jobs4U Backoffice for Customer Manager");
            // display menu:
            option = ConsoleUtils.showAndSelectIndex(options, message, exit);
            switch (option) {
                case 0:
                    break;
                case 1:
                    RegisterCustomerUI registerCustomerUI = new RegisterCustomerUI();
                    registerCustomerUI.doShow(authzUI);
                    break;
                case 2:
                    RegisterJobOpeningUI registerJobOpeningUI = new RegisterJobOpeningUI();
                    registerJobOpeningUI.doShow(authzUI);
                    break;
                case 3:
                    EditJobOpeningUI editJobOpeningUI = new EditJobOpeningUI();
                    editJobOpeningUI.doShow(authzUI);
                    break;
                case 4:
                    ListJobOpeningsUI listJobOpeningsUI = new ListJobOpeningsUI();
                    listJobOpeningsUI.doShow(authzUI);
                    break;
                case 5:
                    ListCandidatePersonalDataUI listCandidatePersonalDataUI = new ListCandidatePersonalDataUI();
                    listCandidatePersonalDataUI.doShow(authzUI);
                    break;
                case 6:
                    ListApplicationsUI listApplicationsUI = new ListApplicationsUI(appRepo);
                    listApplicationsUI.doShow(authzUI);
                    break;
                case 7:
                    GenerateJobRequirementSpecificationQuestionsFileUI generateJobRequirementSpecificationFileUI = new GenerateJobRequirementSpecificationQuestionsFileUI();
                    generateJobRequirementSpecificationFileUI.doShow(authzUI);
                    break;
                case 8:
                    SelectJobRequirementSpecificationUI selectJobRequirementSpecificationUI = new SelectJobRequirementSpecificationUI();
                    selectJobRequirementSpecificationUI.doShow(authzUI);
                    break;
                case 9:
                    RequirementsVerificationUI requirementsVerificationUI = new RequirementsVerificationUI();
                    requirementsVerificationUI.doShow(authzUI);
                    break;
                case 10:
                    RegisterInterviewDateUI registerInterviewDateUI = new RegisterInterviewDateUI();
                    registerInterviewDateUI.doShow(authzUI);
                    break;
                case 11:
                    GenerateInterviewQuestionsFileUI generateAnswerCollectionFileUI = new GenerateInterviewQuestionsFileUI();
                    generateAnswerCollectionFileUI.doShow(authzUI);
                    break;
                case 12:
                    SelectInterviewModelUI selectInterviewModelUI = new SelectInterviewModelUI();
                    selectInterviewModelUI.doShow(authzUI);
                    break;
                case 13:
                    EvaluateInterviewUI evaluateInterviewUI = new EvaluateInterviewUI();
                    evaluateInterviewUI.doShow(authzUI);
                    break;
                case 14:
                    ListOrderedInterviewGradeUI listOrderedInterviewGradeUI = new ListOrderedInterviewGradeUI();
                    listOrderedInterviewGradeUI.doShow(authzUI);
                    break;
                case 15:
                    CheckApplicationDataUI checkApplicationDataUI = new CheckApplicationDataUI();
                    checkApplicationDataUI.doShow(authzUI);
                    break;
                case 16:
                    NotificationUI notificationUI = new NotificationUI();
                    notificationUI.doShow(authzUI);
                    break;
                case 17:
                    DefineRecruitmentPhaseUI defineRecruitmentPhaseUI = new DefineRecruitmentPhaseUI();
                    defineRecruitmentPhaseUI.doShow(authzUI);
                    break;
                default:
                    ConsoleUtils.showMessageColor("Invalid option! Try again.", AnsiColor.RED);
            }
        } while (option != 0);
    }
}
