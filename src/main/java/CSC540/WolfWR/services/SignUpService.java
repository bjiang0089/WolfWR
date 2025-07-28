package CSC540.WolfWR.services;

import CSC540.WolfWR.models.SignUp;
import CSC540.WolfWR.repositories.SignUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * SignUp Service has methods to share the connection to the SignUp database table.
 * Any other methods that would interact with the SignUp table should go here.
 * @author Brandon Jiang
 */
@Transactional
@Component
public class SignUpService extends  Services<SignUp, Long> {

    /** Repository connection between the SignUp table and program code */
    @Autowired
    private SignUpRepository repo;

    /** Returns the repository connection between the SignUp table and program code */
    @Override
    protected JpaRepository<SignUp, Long> getRepo() {
        return this.repo;
    }
}
