package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class EntityBean<Entity, EntityId> {

    @PersistenceContext
    protected EntityManager em;

    private final Class<Entity> entityClass;
    
    /**
     * Dieser Konstruktor muss von der erbenden Klasse aufgerufen werden, um
     * das Klassenobjekt der Entity zu setzen. Sonst lässt sich die Methode
     * findById() aufgrund einer Einschränkung der Java Generics hier nicht
     * typsicher definieren.
     * 
     * @param entityClass Klasse der zugrunde liegenden Entity
     */
    public EntityBean(Class<Entity> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Auslesen eines eindeutigen Datensatzes anhand seiner ID bzw. seines
     * Primary Key.
     * 
     * @param id Schlüsselwert
     * @return Gefundener Datensatz oder null
     */
    public Entity findById(EntityId id) {
        if (id == null) {
            return null;
        }
        
        return em.find(entityClass, id);
    }

    /**
     * Auslesen aller Datensätze (Reihenfolge undefiniert)
     * @return Liste mit allen Datensätzen
     */
    public List<Entity> findAll() {
        String select = "SELECT e FROM $E e".replace("$E", this.entityClass.getName());
        return em.createQuery(select).getResultList();
    }

    /**
     * Speichern eines neuen Datensatzes.
     * @param entity Zu speichernder Datensatz
     * @return Gespeicherter Datensatz
     */
    public Entity saveNew(Entity entity) {
        em.persist(entity);
        return em.merge(entity);
    }

    /**
     * Änderungen an einem vorhandenen Datensatz speichern
     * @param entity Zu speichernder Datensatz
     * @return Gespeicherter Datensatz
     */
    public Entity update(Entity entity) {
        return em.merge(entity);
    }

    /**
     * Vorhandenen Datensatz löschen
     * @param entity Zu löschender Datensatz
     */
    public void delete(Entity entity) {
        em.remove(em.merge(entity));
    }
}
