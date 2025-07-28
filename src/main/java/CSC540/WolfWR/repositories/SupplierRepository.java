package CSC540.WolfWR.repositories;

import CSC540.WolfWR.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

/**
 * Connection between the database and the program code for Suppliers
 * @author Brandon Jiang
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
