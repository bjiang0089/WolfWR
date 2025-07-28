package CSC540.WolfWR.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

/**
 * Supplier holds the information on different vendors that supply the store with merchandise.
 *
 * @author Brnadon Jiang
 */
@Entity
public class Supplier extends DomainObject {

    /**
     * Unique identifier for supplier
     */
    @Id
    private long supplierID;

    /**
     * Supplier's name
     */
    @NotNull
    private String supplierName;

    /**
     * Supplier's phone number
     */
    @NotNull
    private String phone;

    /**
     * Supplier's email address
     */
    @NotNull
    private String email;

    /**
     * Supplier's physical location, like an address or city
     */
    @NotNull
    private String location;

    /**
     * Empty constructor for JPA
     */
    public Supplier() {}

    /**
     * Fully enumerated constructor
     * @param supplierID supplier's ID number
     * @param supplierName supplier's name
     * @param phone supplier's phone number
     * @param email supplier's email address
     * @param location supplier's location
     */
    public Supplier(long supplierID, String supplierName,
                    String phone, String email, String location) {
        setSupplierID(supplierID);
        setSupplierName(supplierName);
        setPhone(phone);
        setEmail(email);
        setLocation(location);
    }

    /**
     * Get the supplier ID number
     * @return supplier ID number
     */
    public long getSupplierID() {
        return supplierID;
    }

    /**
     * Set the supplier ID Number
     * @param supplierID the new supplier ID number
     */
    public void setSupplierID(long supplierID) {
        this.supplierID = supplierID;
    }

    /**
     * Get the supplier's name
     * @return the supplier's name
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * Set the supplier's name
     * @param supplierName the supplier's new name
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * Get the supplier's phone number
     * @return the supplier's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set the supplier's phone number
     * @param phone the supplier's new phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Get the supplier's email address
     * @return the supplier's email address
     */
    public String getEmail() { return email; }

    /**
     * Set the supplier's email address
     * @param email the supplier's new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the supplier's physical location
     * @return the supplier's location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set the supplier's physical location
     * @param location the supplier's new location
     */
    public void setLocation(String location) {
        this.location = location;
    }
}
