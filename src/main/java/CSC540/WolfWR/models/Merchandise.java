package CSC540.WolfWR.models;

import CSC540.WolfWR.WolfWRApp;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * 'Merchandise' and 'productID' are the terms we were required to use.
 *  throughout the code base, Merchandise and Product are used
 *  interchangeably to refer to the same thing.
 *
 * Merchandise are the products sold in-stores.
 * Many stores may sell the same product, so
 * uniquely identifying merchandise refers to
 * a specific product in a specific store.
 *
 * Merchandise also maintains which shipment it came from and
 * expiration date for freshness. This means when tabulating a
 * store's inventory of a product will require aggregating merchandise
 * across multiple shipments.
 *
 * @author Brandon Jiang
 */
@Entity
public class Merchandise extends DomainObject {

    /** Unique identifier for a merchandise entry in the database */
    @Id
    private long productID;

    /** The name of the product */
    @Column(nullable = false)
    private String productName;

    /** How much of this shipment of merchandise remains in inventory */
    @Min(0)
    @NotNull
    private int quantity;

    /** The price the store pays for each unit from the supplier */
    @Min(0)
    @NotNull
    private double buyPrice;

    /** The price customers pay for each unit */
    @Min(0)
    @NotNull
    private double marketPrice;

    /** The date that the merchandise was made */
    @NotNull
    private LocalDate productionDate;

    /** The day the merchandise is deemed not fit for sale/consumption */
    @NotNull
    private LocalDate expirationDate;

    /** The company that sells this product */
    @ManyToOne
    @NotNull
    @JoinColumn(name = "supplierID")
    private Supplier supplier;

    /** The store that has this batch of merchandise */
    @ManyToOne
    @JoinColumn(name = "storeID")
    @NotNull
    private Store store;

    /**
     * Empty Constructor for JPA
     */
    public Merchandise() {}

    /**
     * Fully enumerated constructor, accepting dates as
     * strings in the format MM-dd-yyyy
     * @param productID Identifier for this shipment of product
     * @param productName the name of the merchandise
     * @param quantity the amount of product in this batch
     * @param buyPrice the price the store paid for new inventory
     * @param marketPrice the price the customer pays for sales
     * @param productionDate the date the product was made
     * @param expirationDate the date the product is not fit for sales / consumption
     * @param supplier the company that sells the merchandise to the store
     * @param store the store receiving the merchandise
     */
    public Merchandise(long productID, String productName, int quantity, double buyPrice, double marketPrice,
                       String productionDate, String expirationDate, Supplier supplier, Store store) {
        this.productID = productID;
        this.productName = productName;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.marketPrice = marketPrice;
        this.productionDate = LocalDate.parse(productionDate, WolfWRApp.timeFormat);
        this.expirationDate = LocalDate.parse(expirationDate, WolfWRApp.timeFormat);
        this.supplier = supplier;
        this.store = store;
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
     * Get the product name
     * @return the product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Set the product name
     * @param productName new product name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Get the quantity remaining of this batch of merchandise
     * @return the quantity of this merchandise
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the quantity of the merchande
     * @param quantity new quantities
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Get the wholesale price (price paid by the store for each unit of inventory)
     * @return the wholesale price
     */
    public double getBuyPrice() {
        return buyPrice;
    }

    /**
     * Set the wholesale price (price paid by the store for each unit of inventory)
     * @param buyPrice the new wholesale price
     */
    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    /**
     * Get the market price (the price paid by customers)
     * @return the market price
     */
    public double getMarketPrice() {
        return marketPrice;
    }

    /**
     * Set the market price (price paid by customer)
     * @param marketPrice the new market price
     */
    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * Get the production date
     * @return the production date
     */
    public LocalDate getProductionDate() {
        return productionDate;
    }

    /**
     * Set the production date
     * @param productionDate the new production date
     */
    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    /**
     * Get the expiration date
     * @return the expiration date
     */
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    /**
     * Set the expiration date
     * @param expirationDate the new expiration date
     */
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Get the supplier of the merchandise
     * @return the supplier
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * Set the supplier of the merchandise
     * @param supplier the new supplier
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    /**
     * Get the store the merchandise is sold
     * @return the store
     */
    public Store getStore() {
        return store;
    }

    /**
     * Set the store the merchandise is sold
     * @param store the store
     */
    public void setStore(Store store) {
        this.store = store;
    }

    /**
     * Get a string representation of this merchandise with all of it's attributes
     * @return a string representation of the merchandise
     */
    public String toString() {
        return String.format("Product ID: %d, Name: %s, Quantity: %d, Buy Price: %f, MarketPrice: %f, Production Date: %s, Expiration Date: %s, Supplier Name: %s, Store ID: %d\n",
                this.getProductID(), this.getProductName(), this.getQuantity(), this.getBuyPrice(), this.getMarketPrice(), this.getProductionDate().toString(),
                this.getExpirationDate().toString(), this.getSupplier().getSupplierName(), this.getStore().getStoreID());
    }
}
