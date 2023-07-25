package com.gst.example.smack;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.sasl.SASLErrorException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

@Disabled("This test actually connected to the server")
class SmackExampleTest {

    private SmackExample smackExample;

    @BeforeEach
    void setup() {
        smackExample = new SmackExample();
    }


    @ParameterizedTest(name = "{0}")
    @CsvSource({"ValidOpenFireUser_ShouldSuccess, cunglam, 123",
            "validKeycloakUser_ShouldSuccess, technician01, eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJOeWNnR01zeHVLV0lXLWprekJqeXJyb2puUjhBSDhTWTJsdWVnLVpGSVhJIn0.eyJleHAiOjE2OTAxMTA0MDgsImlhdCI6MTY5MDExMDEwOCwianRpIjoiM2JlNzMwMWMtYjQ3Yi00ZTlkLWFmN2ItNjU3N2E5ODViZTk5IiwiaXNzIjoiaHR0cHM6Ly9nc3R0ZWNoLnZkZG5zLnZuOjgwMTAvcmVhbG1zL0N1bmdMYW1UZXN0IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImRjZmYyMjFlLWUxNWEtNGI4Zi04YWU2LWQ2YTJiZDEzZTRiYSIsInR5cCI6IkJlYXJlciIsImF6cCI6InNhZmVmaXJlLWFnZW5jeSIsInNlc3Npb25fc3RhdGUiOiJmMWViNGNlMy00YTRjLTRlOGEtOWIwYi1mOTE5MGUyZjVjMzAiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHBzOi8vd3d3LmtleWNsb2FrLm9yZyJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiIsImRlZmF1bHQtcm9sZXMtY3VuZ2xhbXRlc3QiXX0sInJlc291cmNlX2FjY2VzcyI6eyJzYWZlZmlyZS1hZ2VuY3kiOnsicm9sZXMiOlsiVGVjaGluaWNpYW4iXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsInNpZCI6ImYxZWI0Y2UzLTRhNGMtNGU4YS05YjBiLWY5MTkwZTJmNWMzMCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwicHJlZmVycmVkX3VzZXJuYW1lIjoidGVjaG5pY2lhbjAxIiwiZ2l2ZW5fbmFtZSI6IiIsImZhbWlseV9uYW1lIjoiIn0.jdB9gVpLday4zrfCelPZiozo-94qhZIERSQbGUbv9nUOAnlPDiH-O4KVhVnMuYe7YjuCJTi7-rx6grw5adh4HVKFk8yY8kD5QxGJfxgfGa8A2G0-O9lXaT84oJ8UpUkZLJ2nSsiFPlY02aC2E-Xbbkk4MwHSopFhPvVnBokZ2Scot1SrlcyAd7ln0XADHlRXRh8UUG5EVWQsLH4MLL-bdvlgfZr6o_csTUUzhwyKgmbqRvrEu2WY1sHc40ZIa3nY0ZuTcLu9SHzJFAQcnF4wTdY-zqva3Gy0eWDg7apgQA8Ar_-RXNC2UkUvdnpTJR7jn2SI14CLtk6Y937FkADlog"
    })
    void testLogin_ValidOpenFireUser_ShouldSuccess(String testName, String username, String password) throws InterruptedException, XMPPException, SmackException, IOException {
        AbstractXMPPConnection connection = smackExample.createConnection(username, password);

        Assertions.assertTrue(connection.isConnected());

        connection.disconnect();
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource({"DefaultKeycloakPlainPassword_ShouldFailed, technician01, keycloakuser",
            "InvalidOpenFirePassword_ShouldFailed, cunglam, 1234",
            "InvalidKeycloakPassword_ShouldFailed, technician01, invalidToken",
            "NotExistedUser_ShouldFailed, notexisted, 123",})
    void testLogin_InvalidOpenFireUser_ShouldThrowNotAuthorizedException(String testName, String username, String password) {
        Assertions.assertThrowsExactly(SASLErrorException.class,
                () -> smackExample.createConnection(username, password),
                "SASLError using PLAIN: not-authorized");
    }

}
