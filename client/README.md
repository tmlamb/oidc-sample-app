This is an Open ID Connect Public Client written in React with Typescript.

This project integrates with Okta's client SDK (okta-react and okta-auth-js) to perform the OAuth 2.0 / Open ID Connect flows. Configure a .env.local file with your issuer and client ID values before running the app:

```
REACT_APP_ISSUER=https://dev-123456.okta.com/oauth2/default
REACT_APP_CLIENT_ID=0oe1faz3...
```

To run locally on http://localhost:3000 execute the `npm start` script:

```shell
npm start
```

Pages that depend on API calls to the Resource Server's Graph QL API require running the [resource server](../server) application locally.
