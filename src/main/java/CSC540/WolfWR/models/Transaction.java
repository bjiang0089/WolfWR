package CSC540.WolfWR.models;

import CSC540.WolfWR.WolfWRApp;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.*;

/**
 * Transaction keeps track of purchases made by members.
 * Transactions maintain a list of merchandise purchased, at which store, and by which member.
 * It also keeps track of the total price, although that is tabulated elsewhere to account from discounts.
 * In its current implementation, to purchase more than one of the same item, it will need to be added
 * to the transactions that many times. Each entry only adds one unit of the merchandise.
 *
 * @author Brandon Jiang
 */
@Entity
public class Transaction extends DomainObject {
    /**
     * Unique identifier for transactions
     */
    @Id
    private long transactionID;

    /**
     * The store the purchase was made
     */
    @ManyToOne
    @JoinColumn(name = "storeID")
    @NotNull
    private Store store;

    /**
     * The member making the purchase / the membership used
     */
    @ManyToOne
    @JoinColumn(name = "member_id")
    @NotNull
    private Member member;


    /**
     * Staff member that completed the transaction
     */
    @ManyToOne
    @JoinColumn(name = "cashierID")
    @NotNull
    private Staff cashierID;

    /**
     * Date the purchase was made
     */
    @NotNull
    private LocalDate purchaseDate;

    /**
     * The final price of the transactions after discounts have been accounted for.
     */
    private double totalPrice;

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<TransactionItem> productList;

    /**
     * List of merchandise purchased
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<TransactionItem> productList;

    /**
     * Empty constructor for JPA
     */
    public Transaction() {}

    /**
     * Fully enumerated constructor
     * @param transactionID ID number for transaction
     * @param store store the transaction was made
     * @param member the member making the purchase
     * @param cashierID the staff member that completed the transaction
     * @param purchaseDate the date the purchase was made
     * @param totalPrice the total price after discounts are accounted for
     * @param productList list of merchandise purchased
     */
    public Transaction(long transactionID, Store store, Member member, Staff cashierID,
                       String purchaseDate, float totalPrice, List<TransactionItem> productList) {
        this.transactionID = transactionID;
        this.store = store;
        this.member = member;
        this.cashierID = cashierID;
        this.purchaseDate = LocalDate.parse(purchaseDate);
        this.totalPrice = totalPrice;
        this.productList = productList;
    }
    /**
     * Constructor initializes a new list of transaction items rather than accepting an existing list
     * @param transactionID ID number for transaction
     * @param store store the transaction was made
     * @param member the member making the purchase
     * @param cashierID the staff member that completed the transaction
     * @param purchaseDate the date the purchase was made
     * @param totalPrice the total price after discounts are accounted for
     */
    public Transaction(long transactionID, Store store, Member member, Staff cashierID,
                       String purchaseDate, double totalPrice) {

        setTransactionID(transactionID);
        setStore(store);
        setMember(member);
        setCashierID(cashierID);
        setPurchaseDate(LocalDate.parse(purchaseDate, WolfWRApp.timeFormat));
        setTotalPrice(totalPrice);
        setProductList(new ArrayList<TransactionItem>());
    }

    /**
     * Get the cashier that completed the purchase
     * @return the cashier
     */
    public Staff getCashierID() {
        return cashierID;
    }

    /**
     * Set the cashier making the purchase
     * @param cashierID the new cashier
     */
    public void setCashierID(Staff cashierID) {
        this.cashierID = cashierID;
    }

    /**
     * Get the member making the purchase
     * @return the member
     */
    public Member getMember() {
        return member;
    }

    /**
     * Set the member making the purchase
     * @param member the new member
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * Get the store the transaction was made at
     * @return the store
     */
    public Store getStore() {
        return store;
    }

    /**
     * Set the store that the transaction was made
     * @param store the new store
     */
    public void setStore(Store store) {
        this.store = store;
    }

    /**
     * Get the transaction ID
     * @return the transaction ID
     */
    public long getTransactionID() {
        return transactionID;
    }

    /**
     * Set the transaction ID
     * @param transactionID the new transaction ID
     */
    public void setTransactionID(long transactionID) {
        this.transactionID = transactionID;
    }

    /**
     * Get the date the purchase was made
     * @return the purchase date
     */
    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Set the purchase date
     * @param purchaseDate the new purchase date
     */
    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * Get the total price of the transaction
     * @return the total price
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Set the total price
     * @param totalPrice the new total price
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Get the list of merchandise
     * @return the list of purchased items
     */
    public List<TransactionItem> getProductList() {
        return productList;
    }

    /**
     * Set the list of purchased merchandise
     * @param productList new list of merchandise for the transaction
     */
    public void setProductList(List<TransactionItem> productList) {
        this.productList = productList;
    }

    /**
     * Add a new piece of merchandise to the transaction
     * @param m the merchandise to add
     */
    public void addMerchandise(Merchandise m){
        TransactionItem it = new TransactionItem(this, m);
        this.productList.add(it);
    }


//    public List<Merchandise> getProductList() {
//        return productList;
//    }
//
//    public void setProductList(List<Merchandise> productList) {
//        this.productList = productList;
//    }
//    public void addMerchandise(Merchandise m){
//        this.productList.add(m);
//    }
}
