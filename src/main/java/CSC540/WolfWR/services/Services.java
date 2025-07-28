package CSC540.WolfWR.services;

import CSC540.WolfWR.models.DomainObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Base Service class with common methods that all services classes may use
 * @param <T> Generic Object type for the entity to store in the database (Model Objects)
 * @param <K> Generic Object type for the IDs / keys of the entity
 */
public abstract class Services<T extends DomainObject, K> {

    /** Returns the repository connection between a database table and the program */
    protected abstract JpaRepository<T, K> getRepo();

    /** Save the entity to the repository */
    public void save(T entity) {
        getRepo().saveAndFlush(entity);
    }

    /** Save each entity in the collection to the database */
    public void saveAll(Iterable<T> entities) {
        getRepo().saveAllAndFlush(entities);
    }

    /** Get an entity from the database by its ID */
    public T findByID(K id) {
        Optional<T> obj = getRepo().findById( id );
        return obj.orElse(null);
    }

    /** Get all instances of an entity type from the database */
    public List<T> findAll() {
        return getRepo().findAll();
    }
    /** Delete the given entity from the database */
    public void delete(T obj){getRepo().delete(obj);}

    /** Delete each instance of an entity in the collection from the database */
    public void deleteList(List<T> objs){ getRepo().deleteAll(objs); }
}
