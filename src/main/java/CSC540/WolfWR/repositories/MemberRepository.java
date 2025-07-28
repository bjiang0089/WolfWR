package CSC540.WolfWR.repositories;

import CSC540.WolfWR.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Connection between the database and the program code for Members
 * @author Brandon Jiang
 * @author Janelle Correia
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * Custom query to find the IDs of all members in the system
     * @return list of member IDs
     */
    @Query(value = "SELECT memberid FROM members", nativeQuery = true)
    public List<Long> getIDs();

    /**
     * Custom query to find members with active memberships
     * @return List of members with active memberships
     */
    @Query(value = "SELECT * from members WHERE is_active = 1", nativeQuery = true)
    public List<Member> getMembers();
}
