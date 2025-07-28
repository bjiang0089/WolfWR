package CSC540.WolfWR.services;

import CSC540.WolfWR.models.*;
import CSC540.WolfWR.repositories.MerchandiseRepository;
import CSC540.WolfWR.repositories.StaffRepository;
import CSC540.WolfWR.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Transaction Service computes and aggregates data regarding transactions, such as updating inventory,
 * generating reports, and reviewing transaction history.
 *
 * @author Brandon Jiang
 */
@Transactional
@Component
public class TransactionService  extends  Services<Transaction, Long> {

    /** Repository connection between the Transaction table and program code */
    @Autowired
    private TransactionRepository repo;

    /** Repository connection between the Merchandise table and program code */
    @Autowired
    private MerchandiseRepository merchRepo;

    /** Repository connection between the Staff table and program code */
    @Autowired
    private StaffRepository staffRepo;

    /** Returns the repository connection between the Transaction table and program code */
    @Override
    protected JpaRepository<Transaction, Long> getRepo() {
        return this.repo;
    }

    /**
     * Get all transactions made by a member during a one-year rewards period
     * @param m the member who made the transactions
     * @param end the last day of the billing period (inclusive)
     * @return list of all transactions made by the member during the billing period
     */
    public List<Transaction> processRewards(Member m, LocalDate end) {
        return this.repo.processRewards(m.getId(), end);
    }

    /**
     * Given a transaction, update the inventory of all merchandise purchased
     * @param t the transaction with merchandise to update
     */
    @Transactional
    public void completePurchase(Transaction t) {
        List<TransactionItem> cart = t.getProductList();
        for (TransactionItem ti: cart) {
            Merchandise m = ti.getMerch();
            // Decrement the quantity
            m.setQuantity( m.getQuantity() - 1 );
            merchRepo.save( m );
        }

        repo.save(t);
    }

    /**
     * Print out all transaction IDs and their totals made at a store in a given time frame.
     * A summary stating the total number of transactions and the total price of all transactions
     * during that time is printed at the end
     * @param start the first day of the time frame (inclusive)
     * @param end the last day of the time frame (inclusive)
     * @param store the store the report is generated for
     */
    public void generateBoundStoreSalesReport(LocalDate start, LocalDate end, Store store) {
        List<Transaction> trans = repo.generateBoundStoreSalesReport(start, end, store.getStoreID());
        double total = 0;
        int count = 0;
        System.out.println();
        for (Transaction t: trans) {
            System.out.printf("Trans ID: %4d. Total: $%4.2f\n", t.getTransactionID(), t.getTotalPrice());
            total += t.getTotalPrice();
            count++;
        }
        System.out.println();
        System.out.printf("Between %s and %s, store %4d has completed %d transactions for a total of $%4.2f in sales.\n\n",
                start.toString(), end.toString(), store.getStoreID(), count, total);
    }

    /**
     * Print out all transaction IDs and their totals made company-wide in a given time frame.
     * A summary stating the total number of transactions and the total price of all transactions
     * during that time is printed at the end
     * @param start the first day of the time frame (inclusive)
     * @param end the last day of the time frame (inclusive)
     */
    public void generateBoundSalesReport(LocalDate start, LocalDate end) {
        List<Transaction> trans = repo.generateBoundSalesReport(start, end);
        double total = 0;
        int count = 0;
        System.out.println();
        for (Transaction t: trans) {
            System.out.printf("Trans ID: %4d. Total: $%4.2f\n", t.getTransactionID(), t.getTotalPrice());
            total += t.getTotalPrice();
            count++;
        }
        System.out.println();
        System.out.printf("Between %s and %s, %d transactions were completed company-wide for a total of $%4.2f in sales.\n\n",
                start.toString(), end.toString(), count, total);
    }

    /**
     * Create a sales report for a store in one of three fixed timeframes (a day, a month, or a year).
     * Prints all transaction IDs with their totals
     * A summary with total number of sales and total sales is printed at the end
     * @param timeframe a String spelling out the timeframe to look at
     * @param start the first day of the timeframe (inclusive)
     * @param store the store to generate the report for
     */
    public void generateStoreSalesReport(String timeframe, LocalDate start, Store store) {
        List<Transaction> trans = null;
        switch (timeframe) {
            case "day":
                trans = repo.generateDayStoreReport(start, store.getStoreID());
                break;
            case "month":
                trans = repo.generateMonthStoreReport(start, store.getStoreID());
                break;
            case "year":
                trans = repo.generateYearStoreReport(start, store.getStoreID());
                break;
            default:
                System.out.println("Invalid Input\n");
                return;
        }

        double total = 0;
        int count = 0;
        System.out.println();
        for (Transaction t: trans) {
            System.out.printf("Trans ID: %4d. Total: $%4.2f\n", t.getTransactionID(), t.getTotalPrice());
            total += t.getTotalPrice();
            count++;
        }
        System.out.println();
        System.out.printf("In a 1 %s timespan starting from %s, store %4d has completed %d transactions for a total of $%4.2f in sales.\n\n",
                timeframe, start.toString(), store.getStoreID(), count, total);
    }

    /**
     * Create a sales report company-wide in one of three fixed timeframes (a day, a month, or a year).
     * Prints all transaction IDs with their totals
     * A summary with total number of sales and total sales is printed at the end
     * @param timeframe a String spelling out the timeframe to look at
     * @param start the first day of the timeframe (inclusive)
     */
    public void generateGlobalSalesReport(String timeframe, LocalDate start) {
        List<Transaction> trans = null;
        switch (timeframe) {
            case "day":
                trans = repo.generateDayReport(start);
                break;
            case "month":
                trans = repo.generateMonthReport(start);
                break;
            case "year":
                trans = repo.generateYearReport(start);
                break;
            default:
                System.out.println("Invalid Input\n");
                return;
        }

        double total = 0;
        int count = 0;
        System.out.println();
        for (Transaction t: trans) {
            System.out.printf("Trans ID: %4d. Store: %4d Total: $%4.2f\n",
                    t.getTransactionID(), t.getStore().getStoreID(), t.getTotalPrice());

            total += t.getTotalPrice();
            count++;
        }
        System.out.println();
        System.out.printf("In a 1 %s timespan starting from %s, %4d transactions have been completed company-wide for a total of $%4.2f in sales.\n\n",
                timeframe, start.toString(), count, total);
    }

    /**
     * Get all staff members with the role of 'cashier' at a store
     * @param s the store to look at
     * @return a list of all cashiers at store s
     */
    public List<Staff> findCashier(Store s) {
        return staffRepo.findCashier(s.getStoreID(), Staff.Title.fromString("cashier").ordinal());
    }

    /**
     * Get the full transaction history of a customer
     * @param m the member to look for
     * @return a list containing all transactions made by that customer (regardless of store)
     */
    public List<Transaction> getHistoryByCustomer(Member m) {
        return repo.getHistoryByCustomer(m.getId());
    }

    /**
     * Create unique IDs for transactions
     * @return one more than the current highest ID number
     */
    public long generateID() {
        List<Long> ids = repo.getIDs();
        return Collections.max(ids) + 1;
    }
}
