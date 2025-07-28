package CSC540.WolfWR.services;


import CSC540.WolfWR.models.Member;
import CSC540.WolfWR.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Member Service has methods to share the database connection to the Member table and
 * to get all members. Any other methods that require interaction w. the Members table
 * should go here.
 * @author Brandon Jiang
 */
@Transactional
@Component
public class MemberService extends Services<Member, Long> {

    /** Repository connection between the Member table and program code */
    @Autowired
    private MemberRepository repo;

    /** Returns the repository connection between the Member table and program code */
    @Override
    protected JpaRepository<Member, Long> getRepo() {
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

    /**
     * Get all members
     * @return a list with all members
     */
    public List<Member> viewMembers() {
        return repo.getMembers();
    }
}
