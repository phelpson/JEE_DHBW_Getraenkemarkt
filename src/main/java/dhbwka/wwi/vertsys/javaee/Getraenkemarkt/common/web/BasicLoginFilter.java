
/**
 *
 * @author Martin Kutscher
 * 
 * This Filter is required to support "Form Auth" and "Basic Auth" methods alongside in the same web application on the TomEE.
 * It can be configured via web.xml via role parameter "role-names-comma-sep" (role names as comma separated list, e.g. admin,user,read-only) and url pattern for endpoints.
 * The Filter will read the HTTP Request Header "Authorization" and decode the Base64 Credentials. Afterwards, the user is logged in via role-checking against standard Principal.
 * 
 * <filter>
      <filter-name>BasicLoginFilter</filter-name>
      <filter-class>dhbwka.wwi.vertsys.javaee.jtodo.common.web.BasicLoginFilter</filter-class>
      <init-param>
        <param-name>role-names-comma-sep</param-name>
        <param-value>app-user</param-value>
      </init-param>
    </filter>
    <filter-mapping>
      <filter-name>BasicLoginFilter</filter-name>
      <url-pattern>/api/*</url-pattern>
    </filter-mapping>
 * 
 * 
 *  c.f. :  https://stackoverflow.com/questions/27588665/how-do-i-configure-both-basic-and-form-authentication-methods-in-the-same-java-e
 * 
 */

package dhbwka.wwi.vertsys.javaee.Getraenkemarkt.common.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.realm.GenericPrincipal;



public class BasicLoginFilter implements Filter {
    
  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BASIC_PREFIX = "Basic ";
  private static final String FILTER_PARAMETER_ROLE_NAMES_COMMA_SEPARATED = "role-names-comma-sep";
  private static final String ROLE_SEPARATOR = ",";
  private static final String BASIC_AUTH_SEPARATOR = ":";


  /**
   * List of roles the user must have to authenticate
   */
  private List<String> roleNames;
  
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    String roleNamesParam = filterConfig.getInitParameter(FILTER_PARAMETER_ROLE_NAMES_COMMA_SEPARATED);
    String[] roleNamesParsed = null;
    
    if (roleNamesParam != null) { 
       roleNamesParsed = roleNamesParam.split(ROLE_SEPARATOR);
    }
    if(roleNamesParsed != null){
     this.roleNames =  Arrays.asList(roleNamesParsed);
    }
    
    if(this.roleNames == null || this.roleNames.isEmpty()){
        throw new IllegalArgumentException("No roles defined!");
    }
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest)req;
    HttpServletResponse response = (HttpServletResponse)resp;

    // get username and password from the Authorization header
    String authHeader = request.getHeader(AUTHORIZATION_HEADER);
    if (authHeader == null || !authHeader.startsWith(BASIC_PREFIX)) {
      throwBasicAuthRequired();
    }

    String autfHeaderUserPwPart = authHeader.substring(BASIC_PREFIX.length());
    if(autfHeaderUserPwPart == null){
      throwBasicAuthRequired();
    }
    
    String headerDecoded = new String(Base64.getDecoder().decode(autfHeaderUserPwPart));
    if (!headerDecoded.contains(BASIC_AUTH_SEPARATOR)) {
        throwBasicAuthRequired();
    }
    String[] userPwPair = headerDecoded.split(BASIC_AUTH_SEPARATOR);
    if (userPwPair.length != 2) {
        throwBasicAuthRequired();
    }
    String userDecoded = userPwPair[0];
    String passDecoded = userPwPair[1];
    
    request.login(userDecoded, passDecoded);

    // check roles for the user
    final GenericPrincipal userPrincipal = (GenericPrincipal)request.getUserPrincipal();

    String[] rolesOfCurrentUser = userPrincipal.getRoles();
    
    // calculate intersection between the roles the current user has and the roles the filter was configured with
    boolean hasRoles  = !Collections.disjoint(this.roleNames, Arrays.asList(rolesOfCurrentUser));

    if (hasRoles) {
      // login successful
      chain.doFilter(request, response);
      request.logout();// optional
    } else {
      // login failed
      throwLoginFailed();
    }
  }

  @Override
  public void destroy() {
  }

  public static void throwBasicAuthRequired() throws ServletException {
    throw new ServletException("The /api/* resources require BASIC authentication");
  }

  public static void throwLoginFailed() throws ServletException {
    throw new ServletException("Login failed");
  }
}
