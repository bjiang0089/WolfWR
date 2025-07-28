package CSC540.WolfWR.models;

import jakarta.persistence.*;

/**
 * Staff at the store include all employees from all departments.
 * Staff considered for this system include: Management,
 * Customer Service / Registration, Billing, and Warehouse.
 *
 * For this program, staff only differ by name and privileges. As all attributes remain the same,
 * all staff are represented by a singular class and the different views differentiates their privileges.
 *
 * @author Brandon Jiang
 * @author Janelle Correia
 */
@Entity
// Inheritance tag will save all applicable attributes to this table and
// the full entry to the child table
// @Inheritance(strategy = InheritanceType.JOINED) Artifact from previous approach, left in for learning purposes
public class Staff extends DomainObject{

    /**
     * Unique Identifier for each staff
     */
    @Id
    private long staffID;

    /**
     * The store that the staff member works at
     */
    @ManyToOne
    @JoinColumn(name = "storeID", nullable = false)
    private Store store;

    /**
     * The staff member's name (Full Name)
     */
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    /**
     * Staff member's age
     */
    @Column(name = "age", nullable = false)
    private int age;

    /**
     * Staff member's physical address
     */
    @Column(name = "address", nullable = false, length = 128)
    private String address;

    /**
     * Staff member's phone number
     */
    @Column(name = "phone", nullable = false, length = 16)
    private String phone;

    /**
     * Staff member's email address
     */
    @Column(name = "email", nullable = false, length = 64)
    private String email;

    /**
     * Time the staff member has been employed by the company (in years)
     */
    @Column(name = "employmentTime", nullable = false)
    private int employmentTime;

    /**
     * Restricted set of values listing the different departments that staff may work in
     * @author Janelle Correia
     */
    public enum Title {
        MANAGER, 
        REGISTRATION, 
        BILLING, 
        WAREHOUSE,
        UNKNOWN;

        /**
         * Converts human legible strings into enum
         * @param title role to convert
         * @return enum value for the role. Returns UNKNOWN if the input does not match.
         */
        public static Title fromString(String title) {
            return switch (title.toLowerCase()) {
                case "manager", "assistant manager" -> MANAGER;
                case "billing staff", "cashier" -> BILLING;
                case "warehouse checker" -> WAREHOUSE;
                case "registration" -> REGISTRATION;
                default -> UNKNOWN;
            };
        }
    }

    /**
     * Member's role at the store
     */
    @Column(name = "title", nullable = false)
    private Title title;

    /**
     * Empty constructor for JPA
     */
    public Staff() {}

    /**
     * Fully enumerated constructor
     * @param name Staff full name
     * @param address Staff physical address
     * @param age Staff age
     * @param email Staff email address
     * @param title Staff role at the store
     * @param phone staff phone number
     * @param employmentTime Duration of Employment (in years)
     * @param staffID Staff ID number
     * @param store The store the staff member works at
     */
    public Staff(String name,
                 String address,
                 int age,
                 String email,
                 String title,
                 String phone,
                 int employmentTime,
                 long staffID,
                 Store store) {
        setStaffId(staffID);
        setStore(store);
        setName(name);
        setAge(age);
        setAddress(address);
        setTitle(title);
        setEmail(email);
        setPhone(phone);
        setEmploymentTime(employmentTime);
    }

    /**
     * Get the staff ID number
     * @return staff ID number
     */
    public long getStaffId() {
        return this.staffID;
    }

    /**
     * Set the staff ID number
     * @param staffID the new staff ID number
     */
    public void setStaffId(long staffID) {
        this.staffID = staffID;
    }

    /**
     * Get the store the staff member works at
     * @return the store the staff member works at
     */
    public Store getStore() {
        return this.store;
    }

    /**
     * Set the store the staff member works at
     * @param store the new store the staff memebr works at
     */
    public void setStore(Store store) {
        this.store = store;
    }

    /**
     * Get the staff member's name
     * @return the staff member's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the staff member's name
     * @param name the staff member's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the staff member's physical address
     * @return the staff member's physical address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Set the staff member's physical address
     * @param address the staff member's new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get the staff member's age
     * @return the staff member's age
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Set the staff memebr's age
     * @param age the staff member's age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Get the staff member's title
     * @return the staff member's title
     */
    public Title getTitle() {
        return this.title;
    }

    /**
     * Set the staff member's title
     * @param title the staff member's new title, as a string
     */
    public void setTitle(String title) {
        this.title = Title.fromString(title);
    }

    /**
     * Get the staff member's phone number
     * @return the staff member's phone number
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Set the staff member's phone number
     * @param phone the staff member's new title
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Get the staff member's email address
     * @return the staff member's email address
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Set the staff member's email address
     * @param email the staff member's new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the duration of the staff member's employment
     * @return how long the staff member has been employed (in years)
     */
    public int getEmploymentTime() {
        return this.employmentTime;
    }

    /**
     * Set the staff member's duration of employment
     * @param employmentTime the new duration (in years)
     */
    public void setEmploymentTime(int employmentTime) {
        this.employmentTime = employmentTime;
    }
}
