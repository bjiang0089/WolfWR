package CSC540.WolfWR.services;

import CSC540.WolfWR.models.Discount;
import CSC540.WolfWR.models.Merchandise;
import CSC540.WolfWR.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * DiscountService has methods for sharing the database connection with the Discount table
 * and for searching for discounts. Any other methods that interacts with the Discount table
 * should go here.
 *
 * @author Brandon Jiang
 */
@Component
@Transactional
public class DiscountService extends Services<Discount, Discount.DiscountID>{

    /** Repository connection between the Discount table and program code */
    @Autowired
    private DiscountRepository repo;

    /** Returns the repository connection between the Discount table and program code */
    @Override
    protected JpaRepository<Discount, Discount.DiscountID> getRepo() {
        return this.repo;
    }

    /**
     * Find all discounts on all merchandise that are active on the date given
     * @param date the date to search for
     * @return list of all discounts that are active on that date
     */
    public List<Discount> findByDate(LocalDate date) {
        return repo.findAllByDate(date);
    }

    /**
     * Find all discounts on a specific product that are active on the given date
     * @param m the product to look for
     * @param date the date to look for
     * @return list of all discounts for that product on the date
     */
    public List<Discount> findByProductIDAndDate(Merchandise m, LocalDate date) {
        return repo.findByProductIDAndDate(m.getProductID(), date);
    }


}
