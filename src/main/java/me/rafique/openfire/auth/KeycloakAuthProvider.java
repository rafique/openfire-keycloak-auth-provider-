
package me.rafique.openfire.auth;

import org.jivesoftware.openfire.auth.AuthProvider;
import org.jivesoftware.openfire.auth.ConnectionException;
import org.jivesoftware.openfire.auth.InternalUnauthenticatedException;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.user.UserAlreadyExistsException;
import org.jivesoftware.openfire.user.UserManager;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.jivesoftware.util.JiveProperties;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeycloakAuthProvider implements AuthProvider {

	private static Logger logger = LoggerFactory.getLogger(KeycloakAuthProvider.class);
	
	private KeycloakTokenValidator tokenValidator;

	private UserManager userManager;

	public KeycloakAuthProvider() {
		JiveProperties jiveProperties = JiveProperties.getInstance();
		tokenValidator = new KeycloakTokenValidator(jiveProperties);
		userManager = UserManager.getInstance();
	}

	public boolean isPlainSupported() {
		return true;
	}

	public boolean isDigestSupported() {
		return false;
	}

	public void authenticate(final String username, final String password)
			throws UnauthorizedException, ConnectionException, InternalUnauthenticatedException {
		if (password == null || password.isEmpty()) {
			throw new UnauthorizedException();
		}

		try {
			logger.info("trying to login using {}/{}", username, password);
			JwtClaims jwtClaims = tokenValidator.verifyToken(password);
			importKeycloakUser(jwtClaims);
		} catch (Exception e) {
			logger.info("authentication failed: {}", e.getMessage());
			throw new UnauthorizedException(e.getMessage());
		}
	}

	private void importKeycloakUser(JwtClaims jwtClaims) {
		try {
			String username = jwtClaims.getSubject();
			String email = jwtClaims.getClaimValue("email", String.class);
			String name = jwtClaims.getClaimValue("name", String.class);
			String password =  jwtClaims.getClaimValue("email", String.class);
			userManager.createUser(username, password, name, email);
			logger.info("imported user from keycloak using username={}, password={}, email={}, name={}", username, password, email, name);
		} catch (MalformedClaimException | UserAlreadyExistsException e) {
			logger.error("failed to import keycloak user" + e.getMessage(), e);
		}
	
	}

	public void authenticate(final String username, final String token, final String digest)
			throws UnauthorizedException, ConnectionException, InternalUnauthenticatedException {
		throw new UnsupportedOperationException();
	}

	public String getPassword(final String username) throws UserNotFoundException, UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public void setPassword(final String username, final String password)
			throws UserNotFoundException, UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public boolean supportsPasswordRetrieval() {
		return false;
	}

	public boolean isScramSupported() {
		return false;
	}

	public String getSalt(String username) throws UnsupportedOperationException, UserNotFoundException {
		throw new UnsupportedOperationException();
	}

	public int getIterations(String username) throws UnsupportedOperationException, UserNotFoundException {
		throw new UnsupportedOperationException();
	}

	public String getServerKey(String username) throws UnsupportedOperationException, UserNotFoundException {
		throw new UnsupportedOperationException();
	}

	public String getStoredKey(String username) throws UnsupportedOperationException, UserNotFoundException {
		throw new UnsupportedOperationException();
	}

}
