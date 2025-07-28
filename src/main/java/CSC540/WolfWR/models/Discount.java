package CSC540.WolfWR.models;

import CSC540.WolfWR.WolfWRApp;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Discounts represents promotions or sales happening in a given timeframe.
 * The requirements given did not specify that stores may be store specific so the current implementation
 * treats discounts as franchise-wide.
 *
 * Discounts are currently implemented to remove a certain percentage of the price (like 25% off).
 * To discount a specific dollar value, management would need to compute the percentage first.
 * Ex. A manager wants to do a $2 discount on a $5 item. They would create a 40% discount on the item.
 *
 * @author Brandon Jiang
 */
@Entity
@IdClass(Discount.DiscountID.class)
public class Discount extends DomainObject {

    /** The product that is being discounted */
    @ManyToOne
    @JoinColumn(name = "productID")
    @MapsId("productID")
    @Id
    private Merchandise productID;

    /** The percentage taken off the product  */
    @Min(0)
    @Max(100)
    @NotNull
    private int discountPercentage;

    /** The first day that the discount is active (inclusive) */
    @MapsId("start")
    @Id // Includes a NotNull tag
    private LocalDate start;

    /** The last day that the discount is active (inclusive) */
    @NotNull
    private LocalDate end;

    /** Empty constructor to fit JPA guidelines */
    public Discount() {}

    /** Constructor that accepts dates as strings in the form of MM-dd-yyyy */
    public Discount( Merchandise merch, int discountPercentage, String start, String end) {
        setProductID(merch);
        setStart(LocalDate.parse(start, WolfWRApp.timeFormat));
        setDiscountPercentage(discountPercentage);
        setEnd(LocalDate.parse(end, WolfWRApp.timeFormat));
    }

    /** Constructor that accepts dates as LocalDate objects */
    public Discount( Merchandise merch, int discountPercentage, LocalDate start, LocalDate end) {
        setProductID(merch);
        setStart(start);
        setDiscountPercentage(discountPercentage);
        setEnd(end);
    }


    /**
     * Get the product being discounted
     * @return the discounted product
     */
    public Merchandise getProductID() {
        return productID;
    }

    /**
     * Set / Change the product being discounted
     * @param merch the new product to be discounted
     */
    public void setProductID(Merchandise merch) {
        this.productID = merch;
    }

    /**
     * Get the percentage take off of the original price
     * @return the discount percentage
     */
    public int getDiscountPercentage() {
        return discountPercentage;
    }

    /**
     * Set / Change the discount (as an integer percentage)
     * @param discountPercentage the new discounted percentage
     */
    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    /**
     * Get the first day that the discount is active
     * @return the first day the discount applies
     */
    public LocalDate getStart() {
        return start;
    }

    /**
     * Set / Change the first day that the discount is active
     * @param start the new first day of the discount
     */
    public void setStart(LocalDate start) {
        this.start = start;
    }

    /**
     * Get the last day that the discount is active
     * @return the last day the discount applies
     */
    public LocalDate getEnd() {
        return end;
    }

    /**
     * Set / Change the last day that the discount is active
     * @param end the new last day of the discount
     */
    public void setEnd(LocalDate end) {
        this.end = end;
    }

    /**
     * DiscountID is a wrapper class that groups several fields from the Discount class together
     * to form a composite key in the database
     *
     * The associations between corresponding fields is handled by JPA tags
     */
    @Embeddable
    public static class DiscountID extends DomainObject {

        private long productID;

        private LocalDate start;

        public DiscountID() {}

        public DiscountID(long productId, LocalDate start) {
            this.productID = productId;
            this.start = start;
        }

        public long getProductID() {
            return productID;
        }

        public void setProductID(long merchID) {
            this.productID = merchID;
        }

        public LocalDate getStart() {
            return start;
        }

        public void setStart(LocalDate start) {
            this.start = start;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            DiscountID that = (DiscountID) o;
            return productID == that.productID && Objects.equals(start, that.start);
        }

        @Override
        public int hashCode() {
            return Objects.hash(productID, start);
        }
    }
}
