# Open ID Connect (OIDC) Sample Application

This sample application demonstrates how easy it is to setup a React frontend and Spring Boot backend to act as a public client and resource server in an OAuth 2.0 OIDC secured flow, with a GraphQL API for good measure! It takes advantage of powerful, simple tools provided by Okta and the Open Source community.

### Open ID Connect public client with PKCE
- See [client](/client)
- Integrates with the okta-react and okta-auth-js libraries in an example React Typescript frontend to implement the OIDC Public Client flow. This flow redirects users to an Okta authorization server page's login form, and generates an access token that the client can use to authenticate requests to the resource server. "Proof Key for Code Exchange" (PKCE) is enabled to allow the public client to perform a secure OAuth exchange.
- Integrates with the apollo-client library for interacting with the resource server's GraphQL API. Apollo is integrated with the Okta auth flow in order to add the access token to the requests for authentication of GraphQL calls.
### OAuth 2.0 resource server 
- See [server](/server)
- Integrates with the okta-spring-boot-starter library in an example Java Spring Boot backend to implement the OAuth 2.0 Resource Server flow. This flow validates the access tokens provided by the public client during the execution of the Spring Security filter chain through interactions with the authorization server.
- Extends the Spring Security filter chain to inject custom roles to a user's list of authorities. By default Okta's starter will configure a filter that maps the JWT groups claim to the user's list of authorities, so this is useful if a user's authorization data must come from another source.
- Integrates with the graphql-spring-boot-starter library to expose an example GraphQL schema. This setup demonstrates how to integrate the GraphQL interface with Spring Security and the application's OAuth flow in order to authenticate and authorize access globally and at the fine-grained query/mutation level.
### Oauth 2.0 authorization server (external dependency) 
- See [https://developer.okta.com/](https://developer.okta.com/)
