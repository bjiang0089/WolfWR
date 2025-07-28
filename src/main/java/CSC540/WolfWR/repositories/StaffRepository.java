package CSC540.WolfWR.repositories;

import CSC540.WolfWR.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Connection between the database and the program code for Staff
 * @author Brandon Jiang
 */
@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    /**
     * Get all staff members that have a specified role at a given store
     * @param storeID the store to search in
     * @param title the title to look for
     * @return List of all staff members at the store with the title
     */
    @Query(value = "SELECT * FROM staff WHERE storeID = :storeID AND title = :cashier", nativeQuery = true)
    public List<Staff> findCashier(@Param("storeID") Long storeID, @Param("cashier") int title);

    /**
     * Get all IDs of all staff
     * @return List of staff IDs
     */
    @Query(value = "SELECT staffID FROM staff", nativeQuery = true)
    public List<Long> getID();

    /**
     * Get all staff at a given store
     * @param storeID the store to search for
     * @return list of all staff from the store
     */
    @Query(value = "SELECT * FROM staff WHERE storeid = :storeID", nativeQuery = true)
    public List<Staff> findByStore(@Param("storeID") long storeID);
}
