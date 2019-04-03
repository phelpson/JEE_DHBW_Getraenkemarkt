package api;

import api.data.BestellungDTO;
import api.data.BestellungFacade;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Bestellung;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Philip Mayer
 */

//REST-API für die Bestellungen
@Stateless
@Path("bestellungen")
public class BestellungFacadeREST extends AbstractFacade<Bestellung> {

    @PersistenceContext(unitName = "default")
    private EntityManager em;
    
    @EJB
    BestellungFacade bestellungFacade;
    
    public BestellungFacadeREST() {
        super(Bestellung.class);
    }
    
    
    // Alle Bestellungen zurückgeben
    @Override
    @GET
    @Path("getAllBestellungen")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Bestellung> findAll() {
        return super.findAll();
    }
    
//    Bestellung anhand eines übertragenen Query-String-Parameters auslesen ?query=
//    Aufruf der BestellungFacade um das Data Transfer Object zu mappen
    @GET
    @Path("findBestellung")
    @Produces({MediaType.APPLICATION_JSON})
    public List<BestellungDTO> findBestellungByName(@QueryParam("query") @DefaultValue("") String query) {
        return bestellungFacade.findBestellungByName(query);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    

}
