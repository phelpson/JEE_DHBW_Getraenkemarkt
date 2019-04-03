/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import api.data.UserDTO;
import api.data.UserFacade;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.User;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
public class UserFacadeREST extends AbstractFacade<User> {
    
    // Annotation eines aktuellen Entity Manager mit Domain default
    @PersistenceContext(unitName = "default")
    private EntityManager em;
    
    @EJB
    UserFacade userFacade;

    public UserFacadeREST() {
        super(User.class);
    }

//    Methode zur Rückgabe von allen Usern. Da User serializable ist, wird kein DTO dafür benötigt
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findAll() {
        return super.findAll();
    }
    
//    User anhand eines übertragenen Query-String-Parameters auslesen ?query=
//    Aufruf der UserFacade um das Data Transfer Object zu mappen
    @GET
    @Path("/findByUsername")
    @Produces({MediaType.APPLICATION_JSON})
    public List<UserDTO> findByUsername(@QueryParam("query") @DefaultValue("") String query) {
        return userFacade.findByUsername(query);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
