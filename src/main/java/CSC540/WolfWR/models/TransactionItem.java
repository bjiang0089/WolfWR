package CSC540.WolfWR.models;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * TransactionItem is a stand in for the join table between Transactions and Merchandise.
 * Each entry represents only one unit of the merchandise
 *
 * @author Brandon Jiang
 */
@Entity
@IdClass(TransactionItemID.class)
public class TransactionItem extends DomainObject{

    /**
     * Unique identifier for the transaction this item is a part of
     */
    @Id
    @Column(name = "transactionID")
    private long transactionID;
    /**
     * unique identifier for the product in the transaction
     */
    @Id
    @Column(name = "productID")
    private long productID;

    /**
     * The transaction that corresponds to this entry
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "transactionID")
    private Transaction transaction;

    /**
     * The merchandise being purchased
     */
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "productID")
    private Merchandise merch;

    /**
     * Empty constructor for JPA
     */
    public TransactionItem() {}

    /**
     * Fully enumerated constructor
     * @param transaction The transaction that corresponds to this entry
     * @param merch The merchandise being purchased
     */
    public TransactionItem(Transaction transaction, Merchandise merch) {
        setTransaction(transaction);
        setMerch(merch);
    }

    /**
     * Get the ID of the transaction
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
     * Get the product ID
     * @return the product ID
     */
    public long getProductID() {
        return productID;
    }

    /**
     * Set the product ID
     * @param productID the new product ID
     */
    public void setProductID(long productID) {
        this.productID = productID;
    }

    /**
     * Get the transaction
     * @return the transaction (object)
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Set the transaction
     * @param transaction the new transaction
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
        setTransactionID(transaction.getTransactionID());
    }

    /**
     * Get the merchandise
     * @return the merchandise
     */
    public Merchandise getMerch() {
        return merch;
    }

    /**
     * Set the merchandise
     * @param merch the new merchandise
     */
    public void setMerch(Merchandise merch) {
        this.merch = merch;
        setProductID(merch.getProductID());
    }

    /**
     * Compares two TransactionItems. TransactionItems are considered the same if they are part of the same
     * transaction and have the same merchandise
     * @param o the object to compare to this one
     * @return are the two objects considered the same
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransactionItem that = (TransactionItem) o;
        return transactionID == that.getTransactionID() && productID == that.getProductID() &&
                Objects.equals(transaction, that.transaction) && Objects.equals(merch, that.merch);
    }

    /**
     * Hashes this object
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(transactionID, productID, transaction, merch);
    }
}
