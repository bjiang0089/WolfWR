package CSC540.WolfWR.repositories;

import CSC540.WolfWR.models.Discount;
import CSC540.WolfWR.models.Merchandise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Connection between the database and the program code for Discounts
 * @author Brandon Jiang
 */
@Repository
public interface DiscountRepository extends JpaRepository<Discount, Discount.DiscountID> {

    /**
     * Custom query to find all discounts that are active on a certain date
     * @param date the reference date
     * @return all discounts that are active on this day
     */
    @Query(value = "SELECT * FROM discount WHERE :date BETWEEN start AND end ", nativeQuery = true)
    public List<Discount> findAllByDate(@Param("date")LocalDate date);

    /**
     * Custom query to find discounts for a product that are active on a certain date
     * @param productID the product on sale
     * @param date the date to search
     * @return Discounts applying to the product on the date
     */
    @Query(value = "SELECT * FROM discount WHERE productID = :productID AND :date BETWEEN start AND end", nativeQuery = true)
    public List<Discount> findByProductIDAndDate(@Param("productID") Long productID, @Param("date") LocalDate date);

}
