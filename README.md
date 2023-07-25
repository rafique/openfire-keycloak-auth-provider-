Openfire JWT/OAuth2/OIDC authentication Provider
========================================================





Installation
------

1. Setup Openfire Follow the instructions for installing Openfire
   in https://download.igniterealtime.org/openfire/docs/latest/documentation/install-guide.html

2. Build with maven and copy the assembly jar into the openfire/lib directory

3. Go to admin page: http://localhost:9090/index.jsp and update below system properties
   (Server => Server Management => System Properties)

```
    provider.auth.className=org.jivesoftware.openfire.auth.HybridAuthProvider
    hybridAuthProvider.primaryProvider.className=org.jivesoftware.openfire.auth.DefaultAuthProvider
    hybridAuthProvider.secondaryProvider.className=me.rafique.openfire.auth.OidcAuthProvider
```

4. Restart OpenFire
5. Using test in smack example to check that everything is working. In the case of oidc users, password should be the
   valid jwt token

