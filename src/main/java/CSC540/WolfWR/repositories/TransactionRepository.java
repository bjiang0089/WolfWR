package CSC540.WolfWR.repositories;

import CSC540.WolfWR.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Connection between the database and the program code for Transactions
 * @author Brandon Jiang
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Gather all transactions made by the member during the rewards period
     * @param member the member receiving rewards
     * @param end the last day of the billing period
     * @return list of all transactions made by the member during the rewards period
     */
    @Query( value = "SELECT * FROM transaction WHERE member_id = :member AND purchase_date BETWEEN DATE_SUB(:end, INTERVAL 1 year) AND :end", nativeQuery = true)
    public List<Transaction> processRewards(@Param("member") Long member, @Param("end")LocalDate end);

    /**
     * Get all purchases made the given customer
     * @param memberID the customer that made the purchases
     * @return a list of all transaction made by that customer
     */
    @Query(value = "SELECT * FROM transaction WHERE member_id = :memberID", nativeQuery = true)
    public List<Transaction> getHistoryByCustomer(@Param("memberID") Long memberID);

    /**
     * Get the IDs of all transactions
     * @return a list with all transaction IDs
     */
    @Query(value =  "SELECT transactionid FROM transaction", nativeQuery = true)
    public List<Long> getIDs();

    /**
     * Get all transaction made on a given day at a specific store
     * @param start the day the report should be made for
     * @param storeID the store the purchases were made
     * @return a list of all transactions that took place at the store on the given day
     */
    @Query(value = "SELECT * FROM transaction WHERE purchase_date BETWEEN :start AND DATE_ADD(:start, INTERVAL 1 day) AND storeID = :storeid", nativeQuery = true)
    public List<Transaction> generateDayStoreReport(@Param("start") LocalDate start, @Param("storeid") Long storeID);

    /**
     * Get all transaction made in a given month at a specific store
     * @param start the first day of the month the report should be made for
     * @param storeID the store the purchases were made
     * @return a list of all transactions that took place at the store during the month
     */
    @Query(value = "SELECT * FROM transaction WHERE purchase_date BETWEEN :start AND DATE_ADD(:start, INTERVAL 1 month) AND storeID = :storeid", nativeQuery = true)
    public List<Transaction> generateMonthStoreReport(@Param("start") LocalDate start, @Param("storeid") Long storeID);

    /**
     * Get all transaction made during a given year at a specific store
     * @param start the first day of the year the report should be made for
     * @param storeID the store the purchases were made
     * @return a list of all transactions that took place at the store during the year
     */
    @Query(value = "SELECT * FROM transaction WHERE purchase_date BETWEEN :start AND DATE_ADD(:start, INTERVAL 1 year) AND storeID = :storeid", nativeQuery = true)
    public List<Transaction> generateYearStoreReport(@Param("start") LocalDate start, @Param("storeid") Long storeID);


    /**
     * Get all transaction made on a given day company-wide
     * @param start the day the report should be made for
     * @return a list of all transactions that took place on the given day
     */
    @Query(value = "SELECT * FROM transaction WHERE purchase_date BETWEEN :start AND DATE_ADD(:start, INTERVAL 1 day)", nativeQuery = true)
    public List<Transaction> generateDayReport(@Param("start") LocalDate start);

    /**
     * Get all transaction made during a given month company-wide
     * @param start the day the report should be made for
     * @return a list of all transactions that took place during the month
     */
    @Query(value = "SELECT * FROM transaction WHERE purchase_date BETWEEN :start AND DATE_ADD(:start, INTERVAL 1 month)", nativeQuery = true)
    public List<Transaction> generateMonthReport(@Param("start") LocalDate start);

    /**
     * Get all transaction made during a given year company-wide
     * @param start the day the report should be made for
     * @return a list of all transactions that took place during the year
     */
    @Query(value = "SELECT * FROM transaction WHERE purchase_date BETWEEN :start AND DATE_ADD(:start, INTERVAL 1 year)", nativeQuery = true)
    public List<Transaction> generateYearReport(@Param("start") LocalDate start);

    /**
     * Get all transactions made at a store in a specified time frame
     * @param start the first day of the time frame (inclusive)
     * @param end the last day of the time frame (inclusive)
     * @param storeID the store to search for
     * @return a list of all transactions that occurred at the store in the time specified
     */
    @Query(value = "SELECT * FROM transaction WHERE purchase_date BETWEEN :start AND :end AND storeid = :storeID", nativeQuery = true)
    public List<Transaction> generateBoundStoreSalesReport(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("storeID") Long storeID);

    /**
     * Get all transactions made in a specified time frame company-wide
     * @param start the first day of the time frame (inclusive)
     * @param end the last day of the time frame (inclusive)
     * @return a list of all transactions that occurred in the time specified
     */
    @Query(value = "SELECT * FROM transaction WHERE purchase_date BETWEEN :start AND :end", nativeQuery = true)
    public List<Transaction> generateBoundSalesReport(@Param("start") LocalDate start, @Param("end") LocalDate end);

}
