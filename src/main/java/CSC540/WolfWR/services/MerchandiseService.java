package CSC540.WolfWR.services;

import CSC540.WolfWR.models.Merchandise;
import CSC540.WolfWR.models.Store;
import CSC540.WolfWR.models.Supplier;
import CSC540.WolfWR.repositories.MerchandiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Collections;

/**
 * Merchandise Service has methods to share the connection to the Merchandise database table and to get the next ID number.
 * Merchandise Service can also search for store inventory and deliveries of merchandise
 * Any other methods that would interact with the Merchandise table should go here.
 * @author Brandon Jiang
 */
@Transactional
@Component
public class MerchandiseService extends Services<Merchandise, Long> {

    /** Repository connection between the Merchandise table and program code */
    @Autowired
    private MerchandiseRepository repo;

    /** Returns the repository connection between the Merchandise table and program code */
    @Override
    protected JpaRepository<Merchandise, Long> getRepo() {
        return this.repo;
    }

    /**
     * Get all merchandise delivered to all stores from a given supplier during a given timeframe
     * @param start the first day of the timeframe (inclusive)
     * @param end the last day of the timeframe (inclusive)
     * @param s the supplier to look for
     * @return List of all merchandise from supplier s delivered between start and end (inclusive)
     */
    public List<Merchandise> deliveriesByTimeAndSupplier(LocalDate start, LocalDate end, Supplier s) {
        return repo.deliveriesByTime(start, end, s.getSupplierID());
    }

    /**
     * Get all merchandise at a given store
     * @param s the store to search for
     * @return a list of all merchandise deliveries to store s
     */
    public List<Merchandise> storeInventory(Store s) {
        return this.repo.storeInventory(s.getStoreID());
    }

    /**
     * Get the next available value for a unique ID number
     * @return the next ID number
     */
    public long generateID() {
        List<Long> ids = repo.getIDs();
        return Collections.max(ids) + 1;
    }
}
