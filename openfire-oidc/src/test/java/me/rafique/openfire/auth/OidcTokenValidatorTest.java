package me.rafique.openfire.auth;

import org.jose4j.jwt.JwtClaims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.security.Key;

public class OidcTokenValidatorTest {

    private OidcTokenValidator validator;

    public OidcTokenValidatorTest() {

        validator = new OidcTokenValidator();
    }

    @Disabled("This test have to use valid Token")
    @Test
    public void testAuthenticateToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJOeWNnR01zeHVLV0lXLWprekJqeXJyb2puUjhBSDhTWTJsdWVnLVpGSVhJIn0.eyJleHAiOjE2OTAxMDQwMjEsImlhdCI6MTY5MDEwMzcyMSwianRpIjoiMGYwNDdjMTEtOTZjZi00YWFmLWFkMDEtMGEzN2VkOWFjM2E5IiwiaXNzIjoiaHR0cHM6Ly9nc3R0ZWNoLnZkZG5zLnZuOjgwMTAvcmVhbG1zL0N1bmdMYW1UZXN0IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImRjZmYyMjFlLWUxNWEtNGI4Zi04YWU2LWQ2YTJiZDEzZTRiYSIsInR5cCI6IkJlYXJlciIsImF6cCI6InNhZmVmaXJlLWFnZW5jeSIsInNlc3Npb25fc3RhdGUiOiI5NmQ2Mzg3Ni1hMjBkLTQ3MWQtOWJkOC0yYmUyN2RkY2MwNTEiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHBzOi8vd3d3LmtleWNsb2FrLm9yZyJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiIsImRlZmF1bHQtcm9sZXMtY3VuZ2xhbXRlc3QiXX0sInJlc291cmNlX2FjY2VzcyI6eyJzYWZlZmlyZS1hZ2VuY3kiOnsicm9sZXMiOlsiVGVjaGluaWNpYW4iXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsInNpZCI6Ijk2ZDYzODc2LWEyMGQtNDcxZC05YmQ4LTJiZTI3ZGRjYzA1MSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwicHJlZmVycmVkX3VzZXJuYW1lIjoidGVjaG5pY2lhbjAxIiwiZ2l2ZW5fbmFtZSI6IiIsImZhbWlseV9uYW1lIjoiIn0.TqYaQVY9K_yA8RYyO0ZTGATtVXX1JyHxRQwwudYhhpg0ZyvxQ_vdfZQeZBnlwl3mMxeQ11TxyB0rj8BzKW0dB3oQZGgYDmrGWqQVvQh7iAzBNWJI7UZTBz7k_zHuTRHewsN8YF8zOUfCW5sxffj0XgIBy0vSMYcLY1i60m72d9z8Idp6bQGBf8BbWeXWWa2HYL8BqILIM0DiVOTzJsR39jWlonkVssNRr_huaeUeswZmfGkBfDTkqNcWoZGxoZkBi0-5ESCH6GVFFrC-m2PP8WIcqE3LTdizqOgtQ_TY_6J4dVGfJ-pAe7cpL9iOvX6LD6TYdf_nMJxxhUughN7Z6g";
        String url = validator.getIssuerFromToken(token);
        Assertions.assertEquals(url, "https://gsttech.vddns.vn:8010/realms/CungLamTest");
        Key key = validator.getKeycloakPublicKey(url);
        Assertions.assertNotNull(key);

        JwtClaims data = validator.verifyClaims(token, key);
        Assertions.assertNotNull(data);

    }

}
