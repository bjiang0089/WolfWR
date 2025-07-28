package CSC540.WolfWR.models;

import jakarta.persistence.*;

/**
 * Store represents franchise locations.
 * Stores know their address, phone number, and managers.
 * Stores are not required to have a manager at all times.
 *
 * @author Brando Jiang
 */
@Entity
public class Store extends DomainObject {
    /**
     * Unique identifier for stores
     */
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long storeID;

    /**
     * The manager of the store
     */
    @OneToOne
    @JoinColumn(name = "managerID")
    private Staff manager;


    /**
     * Store's physical address
     */
    @Column(name = "address", nullable = false, length = 128)
    private String address;

    /**
     * Store phone number
     */
    @Column(name = "phone", nullable = false, length = 16)
    private String phone;


    /**
     * Empty constructor for JPA
     */
    public Store() {};

    /**
     * Fully enumerated constructor
     * @param storeID Store ID number
     * @param phone Store phone number
     * @param address Store's physical address
     * @param manager Store's manager (allowed to be null)
     */
    public Store(long storeID, String phone, String address, Staff manager) {
        setStoreID(storeID);
        setManager(manager);
        setAddress(address);
        setPhone(phone);
    }

    /**
     * Get the store's ID number
     * @return the store ID number
     */
    public long getStoreID() {
        return storeID;
    }

    /**
     * Set the store's ID number
     * @param storeID the store's new ID number
     */
    public void setStoreID(long storeID) {
        this.storeID = storeID;
    }

    /**
     * Get the store's manager
     * @return the store's manager
     */
    public Staff getManager() {
        return manager;
    }

    /**
     * Set the store manager
     * @param manager the store's new manager
     */
    public void setManager(Staff manager) {
        this.manager = manager;
    }

    /**
     * Get the store's physical address
     * @return the store's physical address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the store's physical address
     * @param address the store's new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get the store's phone number
     * @return the store's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set the store's phone number
     * @param phone the store's new phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns a string representation of the store's information
     * @return string representation of the store
     */
    public String toString() {
        return String.format("\n\nStore ID: %d. Address\n\n", this.storeID, this.address);
    }
}
