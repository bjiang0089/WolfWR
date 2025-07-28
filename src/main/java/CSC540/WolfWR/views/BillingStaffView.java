package CSC540.WolfWR.views;

import CSC540.WolfWR.WolfWRApp;
import CSC540.WolfWR.models.*;
import CSC540.WolfWR.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * BillingStaffView holds the loop and supporting functions to allow billing staff to
 * do their job, such as generating sales reports, and dispensing rewards to members.
 * @author Brandon Jiang
 */
@Component
public class BillingStaffView {

    /** Service that aids in creating reports and rewards */
    @Autowired
    private TransactionService transServ;

    /** Service that aids in generating bills to pay to suppliers */
    @Autowired
    private MerchandiseService merchServ;

    /** Service to find members by ID number */
    @Autowired
    private MemberService memberServ;

    /** Service to help find supplier by ID */
    @Autowired
    private SupplierService supplierServ;

    /** Service to help find stores by ID */
    @Autowired
    private StoreService storeServ;


    /**
     * Holds th main loop that runs displaying actions that can be taken by billing staff
     * Helper methods contain the logic for each action
     * @param scan scanner to allow user input from the keyboard
     */
    public void view(Scanner scan) {
        String input = null;
        //BillingStaffView view = new BillingStaffView();
        while (true) {

            System.out.println("Select an action with the number provided:");

            System.out.println("[0] Return to Home Page");
            System.out.println("[1] Generate Bill for Supplier");
            System.out.println("[2] Calculate Membership Rewards");
            System.out.println("[3] Generate Sales Report (day, month, year)");
            System.out.println("[4] Generate Sales Report (start - end)");

            System.out.print("> ");

            input = scan.nextLine().trim();

            if (input.equalsIgnoreCase("0")) {
                return;
            }

            switch (input.trim().toLowerCase()) {
                case "1":
                    // Make call to helper method
                    generateBill(scan);
                    break;
                case "2":
                    tabulateRewards(scan);
                        break;
                case "3":
                    generateStoreSalesReport(scan);
                    break;
                case "4":
                    generateBoundStoreSalesReport(scan);
                    break;
                default:
                    System.out.println("\nUnknown action\n");
            }
        }

    }

    /**
     * Logic for creating a sales report between any timeframe at a single store
     * User will be prompted for the store and the dates the report should be generated for
     * @param scan scanner to allow user input from the keyboard
     */
    public void generateBoundStoreSalesReport(Scanner scan) {
        String input = null;
        List<Store> locations = storeServ.findAll();

        Store current = null;
        System.out.println("\nPlease select a store:");
        listStores(locations);


        try {
            int idx = Integer.parseInt( scan.nextLine().trim() );
            current = locations.get(idx - 1);
        } catch (Exception e) {
            System.out.println("Invalid Store\n");
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

        transServ.generateBoundStoreSalesReport(start, end, current);
    }

    /**
     * Logic for creating a report for a store in fixed timeframes (day, month, year)
     * User will be prompted to select a store, length of time, and the start of the timeframe (inclusive)
     * @param scan scanner to allow user input from the keyboard
     */
    public void generateStoreSalesReport(Scanner scan) {

        String input = null;
        List<Store> locations = storeServ.findAll();

        Store current = null;
        System.out.println("\nPlease select a store:");
        listStores(locations);


        try {
            int idx = Integer.parseInt( scan.nextLine().trim() );
            current = locations.get(idx - 1);
        } catch (Exception e) {
            System.out.println("Invalid Store\n");
            return;
        }

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
        transServ.generateStoreSalesReport(timeframe, start, current);
    }

    /**
     * Logic for creating a bill to a supplier
     * User will be prompted to select a supplier and a timeframe to search for deliveries
     * @param scan scanner to allow user input from the keyboard
     */
    public void generateBill(Scanner scan) {

        // TODO: Generate list of suppliers
        List<Supplier> suppliers = supplierServ.findAll();
        System.out.println("\nChoose a supplier to pay:\n> ");
        displaySuppliers(suppliers);

        Supplier s = null;
        try {
            s = suppliers.get( Integer.parseInt(scan.nextLine().trim()) );
        } catch (Exception e) {
            System.out.println("Invalid Supplier\n");
            return;
        }

        System.out.println("Choose a time frame.");
        System.out.print("Input the start date as mm-dd-yyyy:\n> ");
        LocalDate start = null;
        LocalDate end = null;

        try {
            start = LocalDate.parse(scan.nextLine().trim(), WolfWRApp.timeFormat);
            System.out.print("\nInput the end date as mm-dd-yyyy:\n> ");
            end = LocalDate.parse(scan.nextLine().trim(), WolfWRApp.timeFormat);
        } catch (Exception e) {
            System.out.println("Invalid date\n");
            return;
        }

        List<Merchandise> deliveries = merchServ.deliveriesByTimeAndSupplier(start, end, s);
        System.out.println();
        double grandTotal = showDeliveries(deliveries);
        System.out.printf("Pay %s $%.2f\n\n", s.getSupplierName(), grandTotal);

    }

    /**
     * Logic for calculating the rewards earned by a member during a rewards period
     * User will be prompted to select a member and the last day of the rewards period.
     * Default timeframe is 1 year.
     * @param scan scanner to allow user input from the keyboard
     */
    public void tabulateRewards(Scanner scan) {

        // Generate list of members and select
        List<Member> members = memberServ.findAll();

        System.out.println("\nChoose a member:");
        displayMembers(members);
        Member m = null;
        try {
            int idx = Integer.parseInt( scan.nextLine().trim() );
            m = members.get(idx - 1);
        } catch (Exception e) {
            System.out.println("Invalid Member\n");
            return;
        }
        System.out.print("Input the *END* of the rewards period as mm-dd-yyyy\n> ");
        LocalDate end = null;

        try {
            end = LocalDate.parse(scan.nextLine().trim(), WolfWRApp.timeFormat);
        } catch (Exception e) {
            System.out.println("Invalid Date.\n");
            return;
        }

        List<Transaction> transactions = transServ.processRewards(m, end);
        double total = tabulateTotalPurchases(transactions);

        System.out.print("""
                
                Input rewards percentage as a whole number between 0 and 100.
                Example: Platinum members get 2%. Input '2'.
                >\s""");

        int percent = 0;
        try {
             percent = Integer.parseInt(scan.nextLine().trim());
             if (percent < 0 || percent > 100) {
                System.out.println("Invalid rewards rate.\n");
                return;
             }
        } catch (Exception e) {
            System.out.println("Invalid Percentage\n");
            return;
        }

        System.out.printf("\nMember %s %s purchased $%.2f this year.\nThey earned %.2f in rewards.\n\n",
                m.getFirstName(), m.getLastName(), total, total * 0.01 * percent);
    }

    /**
     * Helper method to display all merchandise in a list
     * @param deliveries list of merchandise to display
     * @return the grand total the store needs to pay the supplier for the merchandise shown
     */
    public double showDeliveries(List<Merchandise> deliveries) {
        double grandTotal = 0;
        for (Merchandise m: deliveries) {
            double total = m.getBuyPrice() * m.getQuantity();
            System.out.printf("Product %20s, Buy Price%4.2f, Quantity: %4d, Total: %4.2f\n",
                    m.getProductName(), m.getBuyPrice(), m.getQuantity(), total);
            grandTotal += total;
        }
        return grandTotal;
    }

    /**
     * Helper method to list all suppliers and assigns them a numerical value equal to their index in the list
     * @param suppliers list of suppliers to display
     */
    private void displaySuppliers(List<Supplier> suppliers) {
        for (int i = 0; i < suppliers.size(); i++) {
            System.out.printf("[%d] %s\n", i, suppliers.get(i).getSupplierName());
        }
        System.out.print("> ");
    }

    /**
     * Helper method to sum up the totals of all transactions in the list
     * @param trans list of transaction to tabulate
     * @return the total of all the transactions
     */
    private double tabulateTotalPurchases(List<Transaction> trans) {
        double total = 0;
        for (Transaction t: trans) {
            total += t.getTotalPrice();
        }
        return total;
    }

    /**
     * Helper method to display all members in a list and assigns each one a value equal to 1 + their index in the list
     * @param members list of members to display
     */
    private void displayMembers(List<Member> members) {
        for (int i = 0; i < members.size(); i++) {
            Member m = members.get(i);
            System.out.printf("[%d] %10s %10s. %s Member\n", i + 1, m.getFirstName(), m.getLastName(), m.getMembershipLevel());
        }
        System.out.print("> ");
    }

    /**
     * Helper method to list all store locations and assigns each one a value equal to 1 + its index in the list
     * @param locs list of stores to display
     */
    private void listStores(List<Store> locs) {
        for(int i = 0; i < locs.size(); i++) {
            Store s = locs.get(i);
            System.out.printf("[%d] %s\n", i + 1, s.getAddress());
        }
        System.out.print("> ");
    }
}
