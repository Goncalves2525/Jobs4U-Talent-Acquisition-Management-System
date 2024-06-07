package presentation.CustomerManager;

import appUserManagement.domain.Role;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import jobOpeningManagement.application.ListJobOpeningsController;
import jobOpeningManagement.application.EditJobOpeningController;
import jobOpeningManagement.domain.Address;
import jobOpeningManagement.domain.JobOpening;
import textformat.AnsiColor;

public class EditJobOpeningUI {
    EditJobOpeningController ctrlEdit = new EditJobOpeningController();
    ListJobOpeningsController ctrlList = new ListJobOpeningsController();

    static Role managerRole;

    protected void doShow(AuthzUI authzUI) {
        ConsoleUtils.buildUiHeader("Edit Job Opening");

        // get user role, to be used as parameter on restricted user actions
        managerRole = authzUI.getValidBackofficeRole();
        if (!managerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
            return;
        }

        System.out.println("Job Openings:");
        Iterable<JobOpening> jobOpenings = ctrlList.listJobOpenings();

        // Check if jobOpenings is empty
        if (!jobOpenings.iterator().hasNext()) {
            System.out.println("No job openings found.");
        }else {
            // Iterate over jobOpenings if it's not empty
            for (JobOpening jobOpening : jobOpenings) {
                System.out.println("Job Opening ID: " + jobOpening.getId() + " | Job reference: " + jobOpening.getJobReference() + " | Title: " + jobOpening.getTitle() + " | Description: " + jobOpening.getDescription() + " | State: " + jobOpening.getState());
            }
            String id = ConsoleUtils.readLineFromConsole("Insert the Job Opening ID");
            JobOpening jobOpening = ctrlEdit.getJobOpening(id);
            if (jobOpening == null) {
                ConsoleUtils.showMessageColor("Job Opening not found.", AnsiColor.RED);
            } else {
                boolean end = false;
                while(!end){
                    System.out.println("Which field do you want to edit?");
                    System.out.println("1 - Title: " + jobOpening.title());
                    System.out.println("2 - Address : " + jobOpening.address());
                    System.out.println("3 - Number of vacancies: " + jobOpening.numberOfVacancies());
                    System.out.println("0 - Save\n");

                    int option = ConsoleUtils.readIntegerFromConsole("Option: ");
                    switch (option) {
                        case 1:
                            String title = ConsoleUtils.readLineFromConsole("Insert the new title:");
                            jobOpening.setTitle(title);
                            break;
                        case 2:
                            Address address = setAddress();
                            if (address != null){
                                jobOpening.setAddress(address);
                                break;
                            }
                            else{
                                ConsoleUtils.showMessageColor("Invalid address.", AnsiColor.RED);
                                break;
                            }
                        case 3:
                            int numberOfVacancies = ConsoleUtils.readIntegerFromConsole("Insert the new number of vacancies:");
                            jobOpening.setNumberOfVacancies(numberOfVacancies);
                            break;
                        case 0:
                            end = true;
                            break;
                        default:
                            ConsoleUtils.showMessageColor("Invalid option.", AnsiColor.RED);
                            break;
                    }
                }

                boolean success = ctrlEdit.updateJopOpening(jobOpening);
                if (success) {
                    ConsoleUtils.showMessageColor("Job Opening updated successfully.", AnsiColor.GREEN);
                } else {
                    ConsoleUtils.showMessageColor("Job Opening not updated.", AnsiColor.RED);
                }
            }
        }
    }

    public Address setAddress() {
        String street = ConsoleUtils.readLineFromConsole("Insert the street:");
        String city = ConsoleUtils.readLineFromConsole("Insert the city:");
        String postalCode = ConsoleUtils.readLineFromConsole("Insert the postal code:");

        return new Address(street, city, postalCode);
    }
}
