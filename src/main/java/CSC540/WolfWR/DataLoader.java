package CSC540.WolfWR;

import CSC540.WolfWR.models.*;
import CSC540.WolfWR.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Load the database with the dummy data provided.
 * Please manually clear the database prior to loading in
 */
@Component
public class DataLoader {

    @Autowired
    private DiscountService discountServ;

    @Autowired
    private  MemberService memberServ;

    @Autowired
    private  MerchandiseService merchServ;

    @Autowired
    private  SignUpService signUpServ;

    @Autowired
    private  StaffService staffServ;

    @Autowired
    private  StoreService storeServ;

    @Autowired
    private  SupplierService supplierServ;

    @Autowired
    private  TransactionService transServ;

    public void loadData() {
        loadStore();
        // set Manager for store 1001 after Staff are created handled in loadStaff()
        loadStaff();
        loadMembers();


        // Load Suppliers before Merchandise
        loadSupplier();
        loadMerchandise();
        loadDiscount();
        loadSignUp();
        loadTransaction();


    }

    private  void loadMembers() {
        Member m = new Member(501L, "John", "Doe", "Gold", "john.doe@gmail.com",
                "9194285314", "12 Elm St, Raleigh, NC 27607", true);
        memberServ.save(m);

        m = new Member(502L, "Emily", "Smith", "Silver", "emily.smith@gmail.com",
                "9844235314", "34 Oak Ave, Raleigh, NC 27606", false);
        memberServ.save(m);

        m = new Member(503, "Michael", "Brown", "Platinum", "michael.brown@gmail.com", "9194820931",
                "56 Pine Rd, Raleigh, NC 27607", true);
        memberServ.save(m);

        m = new Member(504L, "Sarah", "Johnson", "Gold", "sarah.johnson@gmail.com",
                "9841298435", "78 Maple Dr, Raleigh, NC 27607", true);
        memberServ.save(m);

        m = new Member(505L, "David", "Williams", "Silver", "david.williams@gmail.com",
                "9194829424", "90 Birch Ln, Raleigh, NC 27607", false);
        memberServ.save(m);

        m = new Member(506L, "Anna", "Miller", "Platinum", "anna.miller@gmail.com", "9848519427",
                "101 Oak Ct, Raleigh, NC 27607", true);
        memberServ.save(m);



    }

    private  void loadStore () {
        Store store = new Store(1001L, "9194789125",
                "1021 Main Campus Dr, Raleigh, NC, 27606", null);
        storeServ.save( store );

        store = new Store(1002L, "9195929621",
                "851 Partners Way, Raleigh, NC, 27606", null);
        storeServ.save(store);
    }

    private  void loadStaff() {
        Store store1 = storeServ.findByID( 1001L );
        Store store2 = storeServ.findByID( 1002L );

        Staff alice = new Staff();
        alice.setStaffId(201);
        alice.setStore(store1);
        alice.setName("Alice Johnson");
        alice.setAge(34);
        alice.setAddress("111 Wolf Street, Raleigh, NC 27606");
        alice.setTitle("Manager");
        alice.setPhone("9194285357");
        alice.setEmail("alice.johnson@gmail.com");
        alice.setEmploymentTime(5);
        staffServ.save(alice);

        alice = staffServ.findByID(201L);
        store1.setManager(alice);
        storeServ.save(store1);

        Staff charles = new Staff();
        charles.setStaffId(203);
        charles.setStore(store1);
        charles.setName("Charles Davis");
        charles.setAge(40);
        charles.setAddress("333 Bear Rd, Greensboro, NC, 27282");
        charles.setTitle("Cashier");
        charles.setPhone("9194856193");
        charles.setEmail("charlie.davis@gmail.com");
        charles.setEmploymentTime(7);
        staffServ.save(charles);

        Staff emma = new Staff();
        emma.setStaffId(205);
        emma.setStore(store1);
        emma.setName("Emma White");
        emma.setAge(30);
        emma.setAddress("555 Deer Ln, Durham, NC 27560");
        emma.setTitle("Billing Staff");
        emma.setPhone("9198247184");
        emma.setEmail("emma.white@gmail.com");
        emma.setEmploymentTime(4);
        staffServ.save(emma);

        Staff isla = new Staff();
        isla.setStaffId(207);
        isla.setStore(store1);
        isla.setName("Isla Scott");
        isla.setAge(33);
        isla.setAddress("777 Lunx Rd, Raleigh, NC 27612");
        isla.setTitle("Warehouse Checker");
        isla.setPhone("9841298427");
        isla.setEmail("isla.scott@gmail.com");
        isla.setEmploymentTime(2);
        staffServ.save(isla);

        // Create and insert Staff records for store2 (StoreID 1002)
        Staff bob = new Staff();
        bob.setStaffId(202);
        bob.setStore(store2);
        bob.setName("Bob Smith");
        bob.setAge(29);
        bob.setAddress("222 Fax Ave, Durham, NC 27701");
        bob.setTitle("Assistant Manager");
        bob.setPhone("9841482375");
        bob.setEmail("bob.smith@hotmail.com");
        bob.setEmploymentTime(3);
        staffServ.save(bob);

        Staff david = new Staff();
        david.setStaffId(204);
        david.setStore(store2);
        david.setName("David Lee");
        david.setAge(45);
        david.setAddress("444 Eagle Dr, Raleigh, NC 27606");
        david.setTitle("Warehouse Checker");
        david.setPhone("9847028471");
        david.setEmail("david.lee@yahoo.com");
        david.setEmploymentTime(10);
        staffServ.save(david);

        Staff frank = new Staff();
        frank.setStaffId(206);
        frank.setStore(store2);
        frank.setName("Frank Harris");
        frank.setAge(38);
        frank.setAddress("666 Owl Ct, Raleigh, NC 27610");
        frank.setTitle("Billing Staff");
        frank.setPhone("9194288535");
        frank.setEmail("frank.harris@gmail.com");
        frank.setEmploymentTime(6);
        staffServ.save(frank);

        Staff jack = new Staff("Jack Lewis", "888 Falcon St, Greensboro, NC 27377",
                41, "jack.lewis@gmail.com", "Cashier", "9194183951",
                3, 208, store2);
        staffServ.save(jack);


    }

    private  void loadMerchandise() {
        Store store1 = storeServ.findByID( 1001L );
        Store store2 = storeServ.findByID( 1002L );

        Supplier supplier1 = supplierServ.findByID(401L);
        Supplier supplier2 = supplierServ.findByID(402L);

        Merchandise m = new Merchandise(301, "Organic Apples", 120, 1.5, 2,
                "04-12-2025", "04-20-2025", supplier1, store2);
        merchServ.save(m);

        m = new Merchandise(302L, "Whole Grain Bread", 80, 2,
                3.5, "04-10-2025", "04-15-2025", supplier1, store2);
        merchServ.save(m);

        m = new Merchandise(303L, "Almond Milk", 150, 3.5,
                4, "04-15-2025", "04-30-2025", supplier1, store2);
        merchServ.save(m);

        m = new Merchandise(304L, "Brown Rice", 200, 2.8,
                3.5, "04-12-2025", "04-20-2025", supplier2, store2);
        merchServ.save(m);

        m = new Merchandise(305L, "Olive Oil", 90, 5,
                7, "04-04-2025", "04-20-2027", supplier2, store2);
        merchServ.save(m);

        m = new Merchandise(306L, "Whole Chicken", 75, 10,
                13, "04-12-2025", "05-12-2025", supplier2, store2);
        merchServ.save(m);

        m = new Merchandise(307L, "Cheddar Cheese", 60, 3,
                4.2, "04-12-2025", "10-12-2025", supplier2, store2);
        merchServ.save(m);

        m = new Merchandise(308L, "Dark Chocolate", 50, 2.5,
                3.5, "04-12-2025", "06-20-2026", supplier2, store2);
        merchServ.save(m);
    }

    private  void loadSignUp(){
        Store store1 = storeServ.findByID(1001L);
        Store store2 = storeServ.findByID(1002L);

        Member m = memberServ.findByID(501L);
        SignUp s = new SignUp(store1, "01-31-2024", m);
        if (store1 == null) {
            System.out.println("\n\nNull Store\n\n");
        }
        signUpServ.save(s);

        m = memberServ.findByID(502L);
        s = new SignUp(store1, "02-28-2022", m);
        signUpServ.save(s);

        m = memberServ.findByID(503L);
        s = new SignUp(store2, "03-22-2020", m);
        signUpServ.save(s);

        m = memberServ.findByID(504L);
        s = new SignUp(store2, "03-15-2023", m);
        signUpServ.save(s);

        m = memberServ.findByID(505L);
        s = new SignUp(store2, "08-23-2024", m);
        signUpServ.save(s);

        m = memberServ.findByID(506L);
        s = new SignUp(store2, "02-10-2025", m);
        signUpServ.save(s);
    }

    private  void loadTransaction(){
        Merchandise apples = merchServ.findByID(301L);
        Merchandise bread = merchServ.findByID(302L);
        Merchandise milk = merchServ.findByID(303L);
        Merchandise rice = merchServ.findByID(304L);
        Merchandise oil = merchServ.findByID(305L);
        Merchandise chicken = merchServ.findByID(306L);
        Merchandise cheese = merchServ.findByID(307L);
        Merchandise chocolate = merchServ.findByID(308L);

        Store store1 = storeServ.findByID(1001L);
        Store store2 = storeServ.findByID(1002L);

        Member m2 = memberServ.findByID(502L);
        Member m4 = memberServ.findByID(504L);

        Staff s3 = staffServ.findByID(203L);
        Staff s8 = staffServ.findByID(208L);

        Transaction t = new Transaction(701L, store2, m2, s3, "02-10-2024", 45);
        t.addMerchandise(apples);
        t.addMerchandise(bread);
        transServ.save(t);

        t = new Transaction(702L, store2, m2, s8, "09-12-2024", 60.75);
        t.addMerchandise(milk);
        t.addMerchandise(rice);
        t.addMerchandise(oil);
        transServ.save(t);

        t = new Transaction(703L, store2, m2, s8, "09-23-2024", 78.9);
        t.addMerchandise(chocolate);
        t.addMerchandise(oil);
        t.addMerchandise(milk);
        transServ.save(t);

        t = new Transaction(704L, store2, m4, s8, "07-23-2024", 32.5);
        t.addMerchandise(chicken);
        transServ.save(t);
    }

    private  void loadSupplier(){
        Supplier s = new Supplier( 401, "Fresh Farms Ltd.", "9194248251",
                "contact@freshfarm.com", "123 Greenway Blvd, Raleigh, NC 27615");
        supplierServ.save(s);

        s = new Supplier(402, "Organic Goods Inc.", "9841384298",
                "info@organicgoods.com", "456 Healthy Rd, Raleigh, NC 27606");
        supplierServ.save(s);
    }

    private  void loadDiscount(){
        Merchandise m = merchServ.findByID(306L);
        Discount d = new Discount(m, 10, "04-10-2024", "05-10-2024");
        discountServ.save(d);

        m = merchServ.findByID(303L);
        d = new Discount(m, 20, "02-12-2023", "02-19-2023");
        discountServ.save(d);
    }
}
