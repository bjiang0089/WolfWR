package CSC540.WolfWR.repositories;

import CSC540.WolfWR.models.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Connection between the database and the program code for Members
 * @author Brandon Jiang
 */
@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    /**
     * Get the IDs of all stores
     * @return a list of all store IDs
     */
    @Query(value =  "SELECT storeID FROM store", nativeQuery = true)
    public List<Long> getIDs();
}
