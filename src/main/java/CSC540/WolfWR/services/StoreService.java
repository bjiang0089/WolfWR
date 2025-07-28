package CSC540.WolfWR.services;

import CSC540.WolfWR.models.Store;
import CSC540.WolfWR.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Store Service has methods to share the connection to the Store database table and to get the next ID number.
 * Any other methods that would interact with the Store table should go here.
 * @author Brandon Jiang
 */
@Transactional
@Component
public class StoreService  extends  Services<Store, Long> {

    /** Repository connection between the Store table and program code */
    @Autowired
    private StoreRepository repo;

    /** Returns the repository connection between the Store table and program code */
    @Override
    protected JpaRepository<Store, Long> getRepo() {
        return this.repo;
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