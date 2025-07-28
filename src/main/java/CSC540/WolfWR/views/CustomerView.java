package CSC540.WolfWR.views;

import CSC540.WolfWR.WolfWRApp;
import CSC540.WolfWR.models.*;
import CSC540.WolfWR.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Customer View allows members to view their transaction history and make purchases.
 * @author Brandon Jiang
 */
@Transactional
@Component
public class CustomerView {

    /** Connection to the database */
    @Autowired
    private DataSource source;

    /**
     * Finds all store locations
     */
    @Autowired
    private StoreService storeServ;

    /**
     * Finds the inventory in a store
     */
    @Autowired
    private MerchandiseService merchServ;

    /** Service with methods for manipulating the Transaction table */
    @Autowired
    private TransactionService transServ;

    /** Finds all members */
    @Autowired
    private MemberService memberServ;

    /** Finds discounts on products */
    @Autowired
    private DiscountService discountServ;


    /**
     * Holds th main loop that runs displaying actions that can be taken by billing staff
     * Helper methods contain the logic for each action
     * @param scan scanner to allow user input from the keyboard
     */
    public void view(Scanner scan) {
        List<Member> members = memberServ.findAll();
        System.out.println("Please sign in by selecting the number associated with your account:");
        displayMembers(members);
        String input = null;

        input = scan.nextLine().trim();
        Member activeMember = null;
        try {
            activeMember = members.get( Integer.parseInt(input) - 1 );
        } catch (Exception e) {
            System.out.println("Error Signing In");
            return;
        }

        while (true) {
            System.out.println("\nSelect an action with the number provided:");
            System.out.println("[0] Return to Home Page");
            System.out.println("[1] Make a Purchase");
            System.out.println("[2] View History");
            System.out.print("> ");

            input = scan.nextLine().trim();

            switch (input) {
                case "0":
                    System.out.println("Returning to Home Page. . .\n");
                    return;
                case "1":
                    try {
                        makePurchase(scan, activeMember);
                    } catch (Exception e) {
                        System.out.println("Error while processing transaction.");
                    }

                    break;
                case "2":
                    viewHistory(activeMember);
                    break;
                default:
                    System.out.println("\nUnknown action\n");
            }
        }
    }

    /**
     * Finds all transactions made by a member
     * @param m the member's history to look for
     */
    public void viewHistory(Member m) {
        List<Transaction> trans = transServ.getHistoryByCustomer(m);
        int count = 0;
        double total = 0;
        System.out.println();
        for (int i = 0 ; i < trans.size(); i++) {
            Transaction t = trans.get(i);
            System.out.printf("Transaction %4d - Total: $%3.2f - Date: %s\n",
                    t.getTransactionID(), t.getTotalPrice(), t.getPurchaseDate().toString());
            count++;
            total += t.getTotalPrice();
        }
        System.out.printf("You've made %3d purchases with a total of $%4.2f\n\n",
                count, total);
    }

    /**
     * Logic to make a purchase at a store
     * User will be prompted for the date, the store, and to select merchandise
     * @param scan scanner to allow user input from the keyboard
     * @param active the member making the purchase
     * @throws SQLException Exception may be thrown if connection to the database is disrupted
     */
    @Transactional
    public void makePurchase(Scanner scan, Member active) throws SQLException {
        Connection conn = null;
        try {
            conn = source.getConnection();
        } catch (Exception e) {
            System.out.println("Error connecting to the database.\n");
            return;
        }
        try  {
            conn.setAutoCommit(false);
            System.out.println("Please enter a date in the format mm-dd-yyyy:");
            System.out.print("> ");

            String input = scan.nextLine().trim();
            LocalDate date = null;
            try {
                date = LocalDate.parse(input, WolfWRApp.timeFormat);
            } catch (Exception e) {
                System.out.println("Invalid Date");
                conn.rollback();
                return;
            }
            // Store Selection
            Store current = null;
            List<Store> locations = storeServ.findAll();
            System.out.println("\nPlease select a store:");
            listLocations(locations);

            try {
                int idx = Integer.parseInt(scan.nextLine().trim());
                current = locations.get(idx - 1);
            } catch (Exception e) {
                System.out.println("Invalid Store\n");
                conn.rollback();
                return;
            }

            Transaction t = getNewTransaction(current, active, date);

            if (t == null) {
                System.out.println("There are no cashiers at this store\n");
                conn.rollback();
                return;
            }

            // Get list of transaction items
            List<Merchandise> product = merchServ.storeInventory(current);
            while (true) {
                System.out.println("Please make a selection:");
                listInventory(product);

                // Item Selection
                try {
                    input = scan.nextLine().trim();

                    if (input.equals("-1")) {
                        System.out.println("Cancelling Transaction. . .\n");
                        conn.rollback();
                        return;
                    } else if (input.equals("0")) {
                        System.out.println("Proceeding to Checkout. . .");
                        break;
                    } else {
                        int idx = Integer.parseInt(input) - 1;
                        Merchandise m = product.get(idx);
                        // Add Merch to cart
                        t.addMerchandise(m);
                        // Buy each Item once
                        product.remove(idx);
                        System.out.printf("%s added to cart\n\n", m.getProductName());
                    }
                } catch (NumberFormatException n) {
                    System.out.println("Number Format Exception\n");
                } catch (Exception e) {
                    System.out.println("\nError reading selection\n");
                    e.getStackTrace();
                    continue;
                }
            }


            // Checkout
            double total = calculateTotal(date, t.getProductList());


            t.setTotalPrice(total);
            transServ.completePurchase(t);
            System.out.printf("\nThe total for you transaction is $%3.2f.\n", total);
            System.out.println("Purchase Complete!\n");
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        }
        conn.setAutoCommit(true);
    }

    /**
     * Helper method to calculate the total price of a transaction,
     * factoring discounts
     * @param date the date of the transaction
     * @param cart the list of products purchased
     * @return the total price
     */
    private double calculateTotal(LocalDate date, List<TransactionItem> cart) {
        double total = 0;
        double price = 0;

        List<Discount> discounts = discountServ.findByDate(date);
        for (TransactionItem m: cart) {
            discounts = discountServ.findByProductIDAndDate(m.getMerch(), date);
            price = m.getMerch().getMarketPrice();
            if (!discounts.isEmpty()) {
                Discount d = discounts.get(0);
                price *= (100 - d.getDiscountPercentage());
                price /= 100;
            }
            total += price;
        }
        return total;
    }

    /**
     * Helper method to list all stores in a list
     * @param locs list of stores to display
     */
    private void listLocations(List<Store> locs) {
        for(int i = 0; i < locs.size(); i++) {
            Store s = locs.get(i);
            System.out.printf("[%d] %s\n", i + 1, s.getAddress());
        }
        System.out.print("> ");
    }

    /**
     * Helper method to list all merchandise in a list
     * @param merch list of stores to display
     */
    private void listInventory(List<Merchandise> merch) {

        System.out.println("[-1] Cancel Transaction");
        System.out.println("[0] Checkout\n");
        for (int i = 0; i < merch.size(); i++) {
            Merchandise m = merch.get(i);
            System.out.printf("[%2d] $%3.2f %s\n", i + 1, m.getMarketPrice(),m.getProductName());
        }
        System.out.print("> ");
    }

    /**
     * Helper method to list all members in a list
     * @param members list of stores to display
     */
    private void displayMembers(List<Member> members) {
        for (int i = 0; i < members.size(); i++) {
            Member m = members.get(i);
            System.out.printf("[%d] %10s %10s. %s Member\n", i + 1, m.getFirstName(), m.getLastName(), m.getMembershipLevel());
        }
        System.out.print("> ");
    }

    /**
     * Creates a new transaction for the member, at a store, on the given date
     * @param s the store the transaction is happening
     * @param m the member making the purchase
     * @param date the date the transaction is happening on
     * @return new transaction with the given info
     */
    @Transactional
    public Transaction getNewTransaction(Store s, Member m, LocalDate date) {
        Transaction t = new Transaction();
        t.setTransactionID( transServ.generateID() );
        t.setStore(s);
        t.setPurchaseDate(date);
        t.setProductList( new ArrayList<TransactionItem>() );
        t.setMember(m);
        List<Staff> cashiers = transServ.findCashier(s);
        if (cashiers.isEmpty()) {
            t.setCashierID(null);
            return t;
        }
        Staff cash = cashiers.get( 0 );

        return t;
    }
}