/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.ejb.BestellungBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Bestellung;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Philip Mayer
 */
@Stateless
@Path("api/bestellunganzeigen")
@Consumes({"application/json", "text/xml"})
@Produces({"application/json", "text/xml"})
public class BestellungFacadeREST extends AbstractFacade<Bestellung> {
    
    @EJB
    BestellungBean bestellungBean;

    public BestellungFacadeREST() {
        super(Bestellung.class);
    }

    
    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    @Consumes(MediaType.APPLICATION_JSON) 
    public List<Bestellung> findBestellung (@QueryParam("query") @DefaultValue("") String query) {
        List<Bestellung> bestellungen = this.bestellungBean.findByUsername(query);
        return bestellungen.stream().map((Bestellung) -> {
                Bestellung bestellungEntity = new Bestellung();
                bestellungEntity.setkunde(Bestellung.getkunde());
                bestellungEntity.setDueDate(Bestellung.getDueDate());
                return bestellungEntity;
            }).collect(Collectors.toList());
    }

    @Override
    protected EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
