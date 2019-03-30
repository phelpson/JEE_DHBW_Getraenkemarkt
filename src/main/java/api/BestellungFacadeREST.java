package api;

import api.data.BestellungDTO;
import api.data.BestellungFacade;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.bestellungen.jpa.Bestellung;
import java.util.List;
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
@Path("bestellungen")
public class BestellungFacadeREST extends AbstractFacade<Bestellung> {

    @PersistenceContext(unitName = "default")
    private EntityManager em;
    
    @EJB
    BestellungFacade bestellungFacade;
    
    public BestellungFacadeREST() {
        super(Bestellung.class);
    }
    
//    @GET
//    @Override
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public List<Bestellung> findAll() {
//        return super.findAll();
//    }
    
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
