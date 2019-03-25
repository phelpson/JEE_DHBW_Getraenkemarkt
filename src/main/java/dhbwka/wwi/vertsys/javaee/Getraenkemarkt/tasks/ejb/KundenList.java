/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.ejb;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.KundeEntity;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.tasks.jpa.Category;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Kategorien.
 */
@Stateless
@RolesAllowed("app-user")
public class KundenList extends EntityBean<KundeEntity, Long> {

    public KundenListBean() {
        super(KundeEntity.class);
    }

    /**
     * Auslesen aller Kategorien, alphabetisch sortiert.
     *
     * @return Liste mit allen Kategorien
     */
    public List<KundeEntity> findAllSorted() {
        return this.em.createQuery("SELECT c FROM Category c ORDER BY c.name").getResultList();
    }
}
