package CSC540.WolfWR.services;

import CSC540.WolfWR.models.Supplier;
import CSC540.WolfWR.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Supplier Service contains a method to get the database connection for the Supplier table
 * Any other methods that would interact with the Supplier table should go here.
 *
 * @author Brandon Jiang
 */
@Transactional
@Component
public class SupplierService  extends  Services<Supplier, Long> {

    /** Repository connection between the Supplier table and program code */
    @Autowired
    private SupplierRepository repo;

    /** Returns the repository connection between the Supplier table and program code */
    @Override
    protected JpaRepository<Supplier, Long> getRepo() {
        return this.repo;
    }
}
