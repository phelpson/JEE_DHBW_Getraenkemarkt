/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.User;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Philip Mayer
 */
@Stateless
@Path("User")
@Consumes({"application/json", "text/xml"})
@Produces({"application/json", "text/xml"})
public class UserFacadeREST{

    @EJB
    private UserBean userBean;
    
    @XmlRootElement
    public static class Response {

        public List<User> user;
        public String status;
        public String exception;
        public String message;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    @Consumes(MediaType.APPLICATION_JSON) 
    public Response findUser(@QueryParam("query") @DefaultValue("") String query) {
        Response response = new Response();
        try {
            response.user = this.userBean.findByQuery(query);
            response.status = "OK";
        } catch (Exception ex) {
            response.status = "ERROR";
            response.exception = ex.getClass().getName();
            response.message = ex.getMessage();
        }
        return response;
    }
    
}
