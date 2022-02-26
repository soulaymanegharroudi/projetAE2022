package be.vinci.pae.api.filters;

import be.vinci.pae.utils.Config;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.inject.Singleton;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

@Singleton
@Provider
@Authorize
public class AuthorizationRequestFilter implements ContainerRequestFilter {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
//  private final JWTVerifier jwtVerifier = JWT.require(this.jwtAlgorithm).withIssuer("auth0")
//  .build();


  @Override
  public void filter(ContainerRequestContext requestContext) /*throws IOException*/ {

  }

}