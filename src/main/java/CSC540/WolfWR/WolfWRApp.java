package CSC540.WolfWR;

import CSC540.WolfWR.models.Member;
import CSC540.WolfWR.services.MemberService;
import CSC540.WolfWR.views.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * WolfWRApp holds the main methods to program execution
 * @author Brandon Jiang
 */
@SpringBootApplication
public class WolfWRApp  implements CommandLineRunner{


    /** Loads the starter data provided into the database */
    @Autowired
    private DataLoader loader;
    /** Class that has all actions that are performed by billing staff */
    @Autowired
    private BillingStaffView billing;
    /** Class that has all actions that are performed by registration staff */
    @Autowired
    private RegistrationView registration;

    /** Class that has all actions that are performed by warehouse staff */
    @Autowired
    private WarehouseView warehouse;

    /** Class that has all actions that are performed by managerial staff */
    @Autowired
    private ManagerView manager;

    /** Class that has all actions that are performed by customers */
    @Autowired
    private CustomerView customer;

    /** Class that has all actions that are performed on a company-wide scale */
    @Autowired
    private GlobalView global;

    /** Expected format for time for String to LocalDate */
    public static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    /**
     * Main method that runs the program. Starts the spring application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(WolfWRApp.class, args);
    }

    /**
     * Main program loop containing the home page
     * Uses helper methods from *View classes to execute specific tasks
     * @param args command line arguments
     */
    @Override
    public void run(String... args) throws Exception {
        Scanner scan = new Scanner(System.in);
        System.out.println("\n\nWelcome to Wolf Wholesale\n\n");



        while(true) {
            System.out.println("\nPlease select your role (input the number associated with your role) or q to quit: ");
            System.out.println("[0] Load Data");
            System.out.println("[1] Manager");
            System.out.println("[2] Billing Staff");
            System.out.println("[3] Registration Staff");
            System.out.println("[4] Warehouse Staff");
            System.out.println("[5] Customer");
            System.out.println("[6] Global / Corporate");
            System.out.print("> ");

            String line = scan.nextLine().trim();
            if (line.trim().equals("q")){
                System.out.println("Goodbye");
                break;
            }

            switch(line) {
                case "0":
                    System.out.println("Loading Data to the database. . .\n");
                    loader.loadData();
                    System.out.println("Data Loaded!!!\n");
                    break;
                case "1":
                    System.out.println("Manager View. . .\n");
                    manager.view(scan);
                    break;
                case "2":
                    System.out.println("Billing Staff View. . .\n");
                    billing.view(scan);
                    break;
                case "3":
                    System.out.println("Registration Staff View. . .\n");
                    registration.view(scan);
                    break;
                case "4":
                    System.out.println("Warehouse Staff View. . .\n");
                    warehouse.view(scan);
                    break;
                case "5":
                    System.out.println("Customer View. . .\n");
                    customer.view(scan);
                    break;

                case "6":
                    System.out.println("Global View. . .");
                    global.view(scan);
                    break;

                default:
                    System.out.println("Unknown role selected\n");
            }
        }
        scan.close();
    }
}
