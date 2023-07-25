package me.rafique.openfire.auth;

import org.jivesoftware.openfire.auth.AuthProvider;
import org.jivesoftware.openfire.auth.UnauthorizedException;
import org.jivesoftware.openfire.user.UserAlreadyExistsException;
import org.jivesoftware.openfire.user.UserManager;
import org.jivesoftware.util.JiveProperties;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OidcAuthProvider implements AuthProvider {

    private static Logger logger = LoggerFactory.getLogger(OidcAuthProvider.class);

    private OidcTokenValidator tokenValidator;

    private UserManager userManager;

    public OidcAuthProvider() {
        logger.info("Init keycloak auth provider");
        JiveProperties jiveProperties = JiveProperties.getInstance();
        tokenValidator = new OidcTokenValidator(jiveProperties);
        userManager = UserManager.getInstance();
    }

    @Override
    public void authenticate(final String username, final String password)
            throws UnauthorizedException {
        if (password == null || password.isEmpty()) {
            throw new UnauthorizedException();
        }

        try {
            logger.info("trying to login using {}/{}", username, password);
            JwtClaims jwtClaims = tokenValidator.verifyToken(password);
            //Todo: check account existed
            importKeycloakUser(jwtClaims);
        } catch (Exception e) {
            logger.info("authentication failed: {}", e.getMessage(), e);
            throw new UnauthorizedException(e.getMessage());
        }
    }

    private void importKeycloakUser(JwtClaims jwtClaims) {
        try {
//            String username = jwtClaims.getSubject();
            String username = jwtClaims.getClaimValue("preferred_username", String.class);
//            String name = jwtClaims.getClaimValue("name", String.class);
            String password = "keycloakuser";
            userManager.createUser(username, password, null, null);
            logger.info("imported user from keycloak using username={}, password={}", username, password);
        } catch (MalformedClaimException | UserAlreadyExistsException e) {
            logger.error("failed to import keycloak user" + e.getMessage(), e);
        }

    }

    @Override
    public String getPassword(final String username) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPassword(final String username, final String password)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean supportsPasswordRetrieval() {
        return false;
    }

    @Override
    public boolean isScramSupported() {
        return false;
    }

    @Override
    public String getSalt(String username) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getIterations(String username) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getServerKey(String username) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getStoredKey(String username) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

}
