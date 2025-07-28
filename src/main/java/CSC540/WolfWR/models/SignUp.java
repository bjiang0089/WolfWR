package CSC540.WolfWR.models;

import CSC540.WolfWR.WolfWRApp;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * The SignUp class acts a joint table between Members and Stores.
 * It saves the date and the store that the member first applied for their membership
 * or the date of their last renewal for billing and promotional purposes.
 *
 * @author Brandon Jiang
 */
@Entity
public class SignUp extends DomainObject{

    /**
     * The ID of the member that signed up
     */
    @Id
    @Column(name = "memberID")
    private long memberID;  // Use memberID as the primary key for SignUp

    /**
     * The ID of the store that registered the customer
     */
    @ManyToOne
    @NotNull
    @JoinColumn(name = "storeID")
    private Store store;

//    @OneToOne(fetch = FetchType.LAZY)
//    @MapsId  // Maps memberID as the primary key of SignUp
//    @NotNull
//    @JoinColumn(name = "memberID", insertable = false, updatable = false)  // Don't insert or update the memberID here
//    private Member member;

    /** The first day the membership is active for this billing period */
    @NotNull
    private LocalDate signUpDate;

    /**
     * Fully enumerated constructor
     * @param store the store where signup happened
     * @param signUpDate the date the member registered
     * @param member the member that registered
     */
    // Constructor with parameters
    public SignUp(Store store,  String signUpDate, Member member) {
        this.memberID = member.getId(); // Set memberID from the existing Member's ID
        this.store = store;
//        this.member = member;  // Reference the existing Member
        this.signUpDate = LocalDate.parse(signUpDate, WolfWRApp.timeFormat);
    }

    /**
     * Empty constructor for JPA
     */
    public SignUp() {}


    // Getters and Setters

    /**
     * Get the member's ID
     * @return the member's ID
     */
    public long getMemberID() {
        return memberID;
    }

    /**
     * Set the member ID
     * @param memberID the new member's ID
     */
    public void setMemberID(long memberID) {
        this.memberID = memberID;
    }

    /**
     * Get the signup date
     * @return the signup date
     */
    public LocalDate getSignUpDate() {
        return signUpDate;
    }

    /**
     * Set the signup date
     * @param signUpDate the new signup date
     */
    public void setSignUpDate(LocalDate signUpDate) {
        this.signUpDate = signUpDate;
    }

//    public Member getMember() {
//        return member;
//    }
//
//    public void setMember(Member member) {
//        this.member = member;
//    }

    /**
     * Get the store the member signed up at
     * @return the store
     */
    public Store getStore() {
        return store;
    }

    /**
     * Set the store the member signed up at
     * @param store the new store
     */
    public void setStore(Store store) {
        this.store = store;
    }
}
