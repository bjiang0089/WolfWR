package CSC540.WolfWR.views;

import CSC540.WolfWR.WolfWRApp;
import CSC540.WolfWR.models.Store;
import CSC540.WolfWR.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Global View contains actions that are on a company-wide scale or did not fit in the purview of any other user.
 * @author Brandon Jiang
 */
@Transactional
@Component
public class GlobalView {

    /** Generates reports for the company */
    @Autowired
    private TransactionService transServ;

    /** Service with methods for interacting with the Store table */
    @Autowired
    private StoreService storeServ;


    /**
     * Main loop that runs the Global view
     * @param scan scanner to allow user input from the keyboard
     */
    public void view(Scanner scan) {
        String input = null;
        //BillingStaffView view = new BillingStaffView();
        while (true) {

            System.out.println("Select an action with the number provided:");

            System.out.println("[0] Return to Home Page");
            System.out.println("[1] Generate Global Sales Report (day, month, year)");
            System.out.println("[2] Generate Global Sales Report (start - end)");
            System.out.println("[3] Add New Location");
            System.out.print("> ");

            input = scan.nextLine().trim();

            if (input.equalsIgnoreCase("0")) {
                return;
            }

            switch (input.trim().toLowerCase()) {
                case "1":
                    generateStoreSalesReport(scan);
                    break;
                case "2":
                    generateBoundStoreSalesReport(scan);
                    break;
                case "3":
                    addNewLocation(scan);
                    break;
                default:
                    System.out.println("\nUnknown action\n");
            }
        }

    }

    /**
     * Create a report of all transactions within one of three fixed lengths of time (day, month, year).
     * User will be prompted for a timeframe and the first day of the timeframe
     * @param scan scanner to allow user input from the keyboard
     */
    public void generateStoreSalesReport(Scanner scan) {

        String input = null;

        System.out.println("\nPlease select a time period for the report:");
        System.out.println("[1] Day");
        System.out.println("[2] Month");
        System.out.println("[3] Year");
        System.out.print("> ");

        input = scan.nextLine().trim();
        String timeframe = "";
        switch (input) {
            case "1":
                timeframe = "day";
                break;
            case "2":
                timeframe = "month";
                break;
            case "3":
                timeframe = "year";
                break;
            default:
                System.out.println("Invalid Input\n");
                return;
        }

        System.out.println("Please provide the *START* date for the report as mm-dd-yyyy:");
        System.out.print("> ");
        input = scan.nextLine().trim();
        LocalDate start = null;
        try {
            start = LocalDate.parse(input, WolfWRApp.timeFormat);
        } catch (Exception e) {
            System.out.println("Unable to parse start date\n");
            return;
        }
        transServ.generateGlobalSalesReport(timeframe, start);
    }

    /**
     * Create a report of all transactions within a given timeframe
     * User will be prompted for the start and end dates
     * @param scan scanner to allow user input from the keyboard
     */
    public void generateBoundStoreSalesReport(Scanner scan) {
        String input = null;

        System.out.println("Please provide the *START* date for the report as mm-dd-yyyy:");
        System.out.print("> ");
        input = scan.nextLine().trim();
        LocalDate start = null;
        try {
            start = LocalDate.parse(input, WolfWRApp.timeFormat);
        } catch (Exception e) {
            System.out.println("Unable to parse start date\n");
            return;
        }

        System.out.println("Please provide the *END* date for the report as mm-dd-yyyy:");
        System.out.print("> ");
        input = scan.nextLine().trim();
        LocalDate end = null;
        try {
            end = LocalDate.parse(input, WolfWRApp.timeFormat);
        } catch (Exception e) {
            System.out.println("Unable to parse start date\n");
            return;
        }

        if (end.isBefore(start)) {
            System.out.println("End date cannot be after the Start date.");
            return;
        }

        transServ.generateBoundSalesReport(start, end);
    }

    /**
     * Creates a new store location
     * User will be prompted for the Store informationå
     * @param scan scanner to allow user input from the keyboard
     */
    public void addNewLocation( Scanner scan ) {
        String input = null;
        Store s = getNewStore();
        System.out.println("Please enter the address of the new location:");
        System.out.print("> ");
        String address = scan.nextLine().trim();

        System.out.println("Please enter the phone number of the new location as numerical string:");
        System.out.print("> ");
        String phone = scan.nextLine().trim();

        try {
            long i = Long.parseLong(phone);
        } catch (Exception e) {
            System.out.println("Phone numbers must be numical values");
            return;
        }

        s.setAddress(address);
        s.setPhone(phone);
        storeServ.save(s);
        System.out.println("New Store Created!\n");
    }

    /**
     * Creates a new store with the next ID
     * @return new Store object with empty fields
     */
    public Store getNewStore() {
        Store s = new Store();
        s.setStoreID(storeServ.generateID() );

        return s;
    }
}
