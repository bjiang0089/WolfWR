package CSC540.WolfWR.repositories;

import CSC540.WolfWR.models.Merchandise;
import CSC540.WolfWR.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Connection between the database and the program code for Merchandise
 * @author Brandon Jiang
 * @author Janelle Correia
 */
@Repository
public interface MerchandiseRepository extends JpaRepository<Merchandise, Long> {

    /**
     * Get all merchandise made in a given timeframe from a specific supplier
     * @param start the start of the time frame (inclusive)
     * @param end the end of the time frame (inclusive)
     * @param supplierID the supplier to look for
     * @return list of all merchandise entries
     */
    @Query(value = "SELECT * FROM Merchandise WHERE supplierID = :supplierID AND production_date BETWEEN :start AND :end", nativeQuery = true)
    public List<Merchandise> deliveriesByTime(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("supplierID")Long supplierID);


    /**
     * Get all the merchandise entries for a given store
     * @param storeID the store to collect inventory
     * @return list of all merchandise entries at the store
     */
    @Query(value = "SELECT * FROM merchandise WHERE storeID = :store AND quantity > 0", nativeQuery = true)
    public List<Merchandise> storeInventory(@Param("store") Long storeID);

    /**
     * Get IDs for all merchandise
     * @return list of all merchandise IDs
     */
    @Query(value = "SELECT productid FROM merchandise", nativeQuery = true)
    public List<Long> getIDs();
}
