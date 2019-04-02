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
import javax.annotation.security.RolesAllowed;
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
@Path("user")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "default")
    private EntityManager em;
    
    @EJB
    UserFacade userFacade;

    public UserFacadeREST() {
        super(User.class);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findAll() {
        return super.findAll();
    }
    
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
