package CSC540.WolfWR.views;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;

import CSC540.WolfWR.WolfWRApp;
import CSC540.WolfWR.services.MemberService;
import CSC540.WolfWR.services.SignUpService;
import CSC540.WolfWR.services.StoreService;
import CSC540.WolfWR.models.SignUp;
import CSC540.WolfWR.models.MembershipLevel;
import CSC540.WolfWR.models.Store;
import CSC540.WolfWR.models.Member;


/**
 * Registration View controls the main loop allowing registration staff to perform their duties
 * such as signing up new members, cancelling memberships, and viewing all members.
 * @author Janelle Correia
 */
@Component
public class RegistrationView {

    /** Service with database connection to the Members table  */
    @Autowired
    private MemberService memberServ;

    /** Service to update and add new entries to the SignUp table */
    @Autowired
    private SignUpService signUpServ;

    /** Service to find store locations */
    @Autowired
    private StoreService storeServ;

    /**
     * Holds th main loop that runs displaying actions that can be taken by registration staff
     * Helper methods contain the logic for each action
     * @param scan scanner to allow user input from the keyboard
     */
    public void view(Scanner scan) {
        System.out.println("Select an action with the number provided:\n");

        System.out.println("[0] Return to Home Page");
        System.out.println("[1] Register Member");
        System.out.println("[2] Deactivate a Membership");
        System.out.println("[3] View all Members");
        System.out.print("> ");

        String input = scan.nextLine().trim();

        switch (input.trim()) {
            case "0":
                System.out.println();
                return;
            case "1":
                registerMember(scan);
                break;
            case "2":
                cancelMembership(scan);
                break;
            case "3":
                listMembers(scan);
                System.out.println();
                break;
            default:
                System.out.println("\nUnknown action\n");
                System.out.println();
        }
    }

    /**
     * Logic to allow staff to signup new members
     * User will be prompted select a store location and for the customer's information
     * @param scan scanner to allow user input from the keyboard
     */
    public void registerMember(Scanner scan) {
        System.out.println();
        long id = memberServ.generateID();
        String first = "";
        String last = "";
        MembershipLevel level = null;
        String email = "";
        String address = "";
        String phone = "";

        List<Store> stores = storeServ.findAll();
        viewStores(stores);
        Store store = null;
        try {
            store = stores.get(Integer.parseInt(scan.nextLine().trim()));
        } catch (Exception e) {
            System.out.println("Invalid Store.\n");
            return;
        }

        try {
            System.out.println();
            System.out.println("Enter the member's first name:");
            System.out.print("> ");
            first = scan.nextLine().trim();
            System.out.println("Enter the member's last name:");
            System.out.print("> ");
            last = scan.nextLine().trim();
            System.out.println("Enter the member's membership level:");
            System.out.print("> ");
            level = MembershipLevel.getLevel(scan.nextLine().trim());
            System.out.println("Enter the member's email address:");
            System.out.print("> ");
            email = scan.nextLine().trim();
            System.out.println("Enter the member's home address:");
            System.out.print("> ");
            address = scan.nextLine().trim();
            System.out.println("Enter the member's phone number:");
            System.out.print("> ");
            phone = scan.nextLine().trim();
        } catch (Exception e) {
            System.out.println("Invalid Entry\n");
            return;
        }

        Member member = new Member();
        member.setId(id);
        member.setFirstName(first);
        member.setLastName(last);
        member.setMembershipLevel(level);
        member.setEmail(email);
        member.setAddress(address);
        member.setPhoneNo(phone);
        member.setActive(true);
        memberServ.save(member);

        SignUp signUp = new SignUp();
        signUp.setMemberID(member.getId());
        signUp.setSignUpDate(LocalDate.now());
        signUp.setStore(store);
        System.out.println();
        System.out.println("Success!\n");
    }

    /**
     * Logic to allow staff to cancel a member's subscription
     * User will be prompted to select a member to cancel
     * @param scan scanner to allow user input from the keyboard
     */
    public void cancelMembership(Scanner scan) {
        System.out.println();
        List<Member> members = memberServ.viewMembers();
        System.out.println();
        System.out.println("Select the member whose membership shall be cancelled:");
        System.out.println();
        int idx = 0;
        for (Member member : members) {
            System.out.printf("[%d] Member ID: %d, First Name: %s, Last Name: %s\n", idx, member.getId(), member.getFirstName(), member.getLastName());
            idx++;
        }
        System.out.print("> ");
        Member member = null;
        try {
            member = members.get(Integer.parseInt(scan.nextLine().trim()));
        } catch (Exception e) {
            System.out.println("Invalid Member\n");
            return;
        }
        member.setActive(false);
        memberServ.save(member);
        System.out.println();
        System.out.println("Success!\n");
    }

    /**
     * Helper function that lists all members' IDs and First and Last names
     * @param scan scanner to allow user input from the keyboard (kept for consistency)
     */
    public void listMembers(Scanner scan) {
        System.out.println();
        int idx = 0;
        List<Member> members = memberServ.viewMembers();
        System.out.println();
        for (Member member : members) {
            System.out.printf("[%d] Member ID: %d, First Name: %s, Last Name: %s\n", idx, member.getId(), member.getFirstName(), member.getLastName());
            idx++;
        }
    }

    /**
     * Helper function to list all store locations
     * @param stores list of stores to display
     */
    private void viewStores(List<Store> stores) {
        System.out.println();
        System.out.println("Please select your store from the following options:\n");
        int idx = 0;
        for (Store s : stores) {
            System.out.printf("[%d] Store ID: %d\n", idx, s.getStoreID());
            idx++;
        }
        System.out.print("> ");
    }
}
