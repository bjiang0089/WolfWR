package CSC540.WolfWR.models;

import java.util.Objects;

/**
 * TransactionItemID a wrapper class to create a composite key for the database
 *
 * @author Brandon Jiang
 */
public class TransactionItemID extends DomainObject {
    /**
     * The ID of the transaction
     */
    private long transactionID;

    /**
     * The ID of the product in the transaction
     */
    private long productID;

    /**
     * Empty constructor for JPA
     */
    public TransactionItemID () {}

    /**
     * Fully enumerated constructor
     * @param transactionID the ID of the transaction this item is a part of
     * @param productID the product being purchased
     */
    public TransactionItemID(long transactionID, long productID) {
        this.transactionID = transactionID;
        this.productID = productID;
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
     * Compares two IDs to see if they are considered the same.
     * IDs are considered the same if the transactionID and the productID are the same.
     * @param o the object to compare to this one
     * @return are the two objects the same (T/F)
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransactionItemID that = (TransactionItemID) o;
        return this.transactionID == that.getTransactionID() && this.productID == that.getProductID();
    }

    /**
     * Returns a hashcode for this TransactionItemID
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.transactionID, this.productID);
    }
}