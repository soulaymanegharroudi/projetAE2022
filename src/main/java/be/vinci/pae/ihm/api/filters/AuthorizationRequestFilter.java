package be.vinci.pae.ihm.api.filters;

import be.vinci.pae.business.ucc.MemberUCC;
import jakarta.inject.Singleton;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Singleton
@Provider
@Authorize
public class AuthorizationRequestFilter implements ContainerRequestFilter {

  //  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  //  private final JWTVerifier jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0")
  //      .build();
  //  @Inject
  private MemberUCC memberUCC;


  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
//    String token = requestContext.getHeaderString("Authorization");
//    if (token == null) {
//      requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
//          .entity("A token is needed to access this resource").build());
//    } else {
//      DecodedJWT decodedToken = null;
//      try {
//        decodedToken = this.jwtVerifier.verify(token);
//      } catch (Exception e) {
//        throw new TokenDecodingException(e);
//      }
    //    }
  }
}