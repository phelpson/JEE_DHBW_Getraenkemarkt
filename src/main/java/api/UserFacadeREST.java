/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.jpa.User;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Philip Mayer
 */
@Stateless
@Path("api/User")
@Consumes({"application/json", "text/xml"})
@Produces({"application/json", "text/xml"})
public class UserFacadeREST{

    @EJB
    private UserBean userBean;
    
    public static class Response {

        public List<User> user;
        public User singleUser;
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
            /*
            response.user = this.userBean.findByQuery(query);
            response.user.stream().map((user) -> {

            User userEntity = new User();

            userEntity.setUsername(user.getUsername());
            userEntity.setEmail(user.getEmail());
            userEntity.setVorname(user.getVorname());
            userEntity.setNachname(user.getNachname());

            return user;
            }).collect(Collectors.toList());
            */
            
            // Single User Test
            response.singleUser = userBean.findByUsername(query);
            response.status = "OK";
        } catch (Exception ex) {
            response.status = "ERROR";
            response.exception = ex.getClass().getName();
            response.message = ex.getMessage();
        }
        return response;
    }
    
}