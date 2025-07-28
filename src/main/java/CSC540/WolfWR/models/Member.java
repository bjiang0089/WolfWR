package CSC540.WolfWR.models;

import jakarta.persistence.*;

/**
 * The Member class represents customers and their membership status with the company.
 * Basic information like names and contact information is also stored for each member.
 *
 * @author Brandon Jiang
 */
@Entity
@Table(name="members")
public class Member extends DomainObject{

    /** Unique Identifier for each member */
    @Id
    @Column(name = "memberID")
    private long id;

    /** Member's first name */
    private String firstName;
    /** Member's last name */
    private String lastName;

    /** The specific membership that the member has (Ex. Gold or Platinum) */
    @Enumerated(EnumType.STRING)
    private MembershipLevel membershipLevel;

    /** Member's email address */
    private String email;
    /** Member's phone number */
    private String phoneNo;
    /** Member's physical address */
    private String address;
    /** Is the membership still active / Have they paid their dues for this billing period */
    private boolean isActive;

    /**
     * Constructor with all fields
     * @param id unique identifier for members
     * @param firstName member's first name
     * @param lastName member's last name
     * @param ml membership level
     * @param email member's email address
     * @param phoneNo member's phone number
     * @param address member's physical address
     * @param isActive is the membership still active for this billing period
     */
    public Member(long id, String firstName, String lastName, String ml, String email, String phoneNo, String address, boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.membershipLevel = MembershipLevel.getLevel(ml);
        this.email = email;
        this.address = address;
        this.phoneNo = phoneNo;
        this.isActive = isActive;
    }

    /**
     * Empty constructor for JPA
     */
    public Member(){}

    /**
     * Get the id number for the member
     * @return the id number
     */
    public long getId() {
        return id;
    }

    /**
     * Set / Change the id number for the member
     * @param id the new id number
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the member's first name
     * @return the member's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set / Chang the member's first name
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the member's last name
     * @return the member's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set / Change the member's last name
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the member's current membership level
     * @return the member's current level
     */
    public String getMembershipLevel() {
        return membershipLevel.getName();
    }

    /**
     * Seet / Change the member's membership level
     * @param membershipLevel the new membership level
     */
    public void setMembershipLevel(MembershipLevel membershipLevel) {
        this.membershipLevel = membershipLevel;
    }

    /**
     * Get the member's email address
     * @return the member's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set / Change the member's email address
     * @param email the new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the member's phone number
     * @return the member's phone number
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * Set / Change the member's phone number
     * @param phoneNo the new phone number
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * Get the member's physical address
     * @return the member's physical address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set / Change the member's physical address
     * @param address the member's physical address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Determines if the member's membership is currently active
     * @return is the member's membership active (T/F)
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Set / Change the member's membership status
     * @param active is the member's membership active (T/F)
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Get a string containing the member's ID number, full name, and membership level
     * @return a string representing the member
     */
    public String toString() {
        return String.format("Id: %2d. Full Name: %s %s. Membership Level: %s\n",
                this.id, this.firstName, this.lastName, this.membershipLevel.getName());
    }
}
