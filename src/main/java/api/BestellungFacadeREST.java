package api;

import api.data.BestellungDTO;
import api.data.BestellungFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
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
public class BestellungFacadeREST {
    
//    Aufruf der abstrahierten bestellungFacade für das List-Mapping der Bestellungen
    @EJB
    BestellungFacade bestellungFacade;
            
//    Bestellung anhand eines übertragenen Query-String-Parameters auslesen ?query=
//    Aufruf der BestellungFacade um das Data Transfer Object zu mappen
//    Um alle Bestellungen abzurufen, kann der query-String leer bleiben (?query="") - analog Default-Value
    @GET
    @Path("findBestellung")
    @Produces({MediaType.APPLICATION_JSON})
    public List<BestellungDTO> findBestellungByName(@QueryParam("query") @DefaultValue("") String query) {
        return bestellungFacade.findBestellungByName(query);
    }
}
