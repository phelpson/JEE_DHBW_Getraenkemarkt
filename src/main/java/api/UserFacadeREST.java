/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import api.data.UserDTO;
import api.data.UserFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
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

// REST-API für die User
@Stateless
@Path("user")
@RolesAllowed("app-user")
public class UserFacadeREST {

//    Abstraktion durch UserFacade für das EntityMapping, wird hier als EJB injected
    @EJB
    UserFacade userFacade;
    
//    User anhand eines übertragenen Query-String-Parameters auslesen ?query=
//    Aufruf der UserFacade um das Data Transfer Object zu mappen
    @GET
    @Path("/findByUsername")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("app-user")
    public List<UserDTO> findByUsername(@QueryParam("query") @DefaultValue("") String query) {
        return userFacade.findByUsername(query);
    }
}
