package CSC540.WolfWR.services;

import CSC540.WolfWR.models.Staff;
import CSC540.WolfWR.models.Store;
import CSC540.WolfWR.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Staff Service has methods to share the connection to the Staff table in the database
 * as well as getting ID numbers and finding staff by store.
 * Any other methods that would interact with the Staff table should go here.
 *
 * @author Brandon Jiang
 */
@Transactional
@Component
public class StaffService  extends Services<Staff, Long> {

    /** Repository connection between the Staff table and program code */
    @Autowired
    private StaffRepository repo;

    /** Returns the repository connection between the Staff table and program code */
    @Override
    protected JpaRepository<Staff, Long> getRepo() {
        return this.repo;
    }

    /**
     * Get the next available value for a unique ID number
     * @return the next ID number
     */
    public long generateID() {
        return Collections.max( repo.getID() ) + 1;
    }

    /**
     * Get all the staff that work at a given store
     * @param s the store to look for
     * @return list of all staff that work at store s
     */
    public List<Staff> findAllByStore(Store s) {
        return repo.findByStore(s.getStoreID());
    }
}
