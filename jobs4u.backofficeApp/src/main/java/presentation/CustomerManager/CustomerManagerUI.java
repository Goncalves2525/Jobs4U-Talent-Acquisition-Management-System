package presentation.CustomerManager;


import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import textformat.AnsiColor;

import java.util.ArrayList;
import java.util.List;

public class CustomerManagerUI {


    public void doShow(AuthzUI authzUI) throws Exception {

        // set option variable, list of options, selection message, and exit name (eg.: exit / cancel / etc.)
        int option;
        List<String> options = new ArrayList<>();

        

        options.add("Register Customer");                               // 1
        options.add("Register Job Opening");                            // 2
        options.add("List Job Openings");                               // 3
        options.add("Select Interview Model");                          // 4
        options.add("List Candidate Personal Data");                    // 5
        options.add("Test Plugin");                                     // 6
        options.add("List Applications For Job Opening");               // 7
        options.add("Generate Interview File");                         // 8
        options.add("Select Job Requirements Specifications");          // 9
        options.add("Edit Job Opening");                                // 10
        options.add("Generate Job Requirement Specification File");     // 11
        options.add("Check Application Data");                          // 12
        options.add("Notification Menu");                               // 13
        options.add("Verify Job Requirements Specification");           // 14

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
                    ListJobOpeningsUI listJobOpeningsUI = new ListJobOpeningsUI();
                    listJobOpeningsUI.doShow(authzUI);
                    break;
                case 4:
                    SelectInterviewModelUI selectInterviewModelUI = new SelectInterviewModelUI();
                    selectInterviewModelUI.doShow(authzUI);
                    break;
                case 5:
                    ListCandidatePersonalDataUI listCandidatePersonalDataUI = new ListCandidatePersonalDataUI();
                    listCandidatePersonalDataUI.doShow(authzUI);
                    break;
                case 6:
                    TestPluginUI testPluginUI = new TestPluginUI();
                    testPluginUI.doShow(authzUI);
                    break;
                case 7:
                    ListApplicationsUI listApplicationsUI = new ListApplicationsUI();
                    listApplicationsUI.doShow(authzUI);
                    break;
                case 8:
                    GenerateInterviewQuestionsFileUI generateAnswerCollectionFileUI = new GenerateInterviewQuestionsFileUI();
                    generateAnswerCollectionFileUI.doShow(authzUI);
                    break;
                case 9:
                    SelectJobRequirementSpecificationUI selectJobRequirementSpecificationUI = new SelectJobRequirementSpecificationUI();
                    selectJobRequirementSpecificationUI.doShow(authzUI);
                    break;
                case 10:
                    EditJobOpeningUI editJobOpeningUI = new EditJobOpeningUI();
                    editJobOpeningUI.doShow(authzUI);
                    break;
                case 11:
                    GenerateJobRequirementSpecificationQuestionsFileUI generateJobRequirementSpecificationFileUI = new GenerateJobRequirementSpecificationQuestionsFileUI();
                    generateJobRequirementSpecificationFileUI.doShow(authzUI);
                    break;
                case 12:
                    CheckApplicationDataUI checkApplicationDataUI = new CheckApplicationDataUI();
                    checkApplicationDataUI.doShow(authzUI);
                    break;
                case 13:
                    NotificationUI notificationUI = new NotificationUI();
                    notificationUI.doShow(authzUI);
                case 14:
                    RequirementsVerificationUI requirementsVerificationUI = new RequirementsVerificationUI();
                    requirementsVerificationUI.doShow(authzUI);
                default:
                    ConsoleUtils.showMessageColor("Invalid option! Try again.", AnsiColor.RED);
            }
        } while (option != 0);
    }
}
