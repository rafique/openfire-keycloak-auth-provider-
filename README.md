Openfire JWT/OAuth2 authentication Provider for Keycloak 
========================================================



Installation
------
1. Build with maven and copy the assembly jar into the openfire/lib directory

		
3. Configure openfire/conf/openfire.xml

```	
	<provider>
  		<auth>
  			<className>me.rafique.openfire.auth.HybridAuthProvider</className>
  		</auth>
  	</provider>
	<hybridAuthProvider>
  		<primaryProvider>
  			<className>org.jivesoftware.openfire.auth.DefaultAuthProvider</className>
  		</primaryProvider>
  		<secondaryProvider>
  			<className>me.rafique.openfire.auth.KeycloakAuthProvider</className>
  		</secondaryProvider>
  	</hybridAuthProvider>
```
