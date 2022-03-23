import { Route, Switch, useHistory } from 'react-router-dom';
import { OktaAuth, toRelativeUrl } from '@okta/okta-auth-js';
import { Security, SecureRoute, LoginCallback } from '@okta/okta-react';
import config from './config';
import './App.css';
import HeaderNav from './HeaderNav';
import Profile from './Profile';
import Messages from './Messages';
import Books from './Books';
import {
  ApolloClient,
  InMemoryCache,
  ApolloProvider,
  createHttpLink
} from "@apollo/client";
import { setContext } from '@apollo/client/link/context';
import Grid from '@mui/material/Grid';

const oktaAuth = new OktaAuth(config.oidc);

function App() {
  const history = useHistory();
  const restoreOriginalUri = async (oktaAuth: OktaAuth, originalUri: string) => {
    history.replace(toRelativeUrl(originalUri || '/', window.location.origin));
  };

  const httpLink = createHttpLink({
    uri: 'http://localhost:8000/graphql',
  });
  
  const authLink = setContext((_, { headers }) => {
    // get the authentication token from local storage if it exists
    const accessToken = oktaAuth.getAccessToken();
    // return the headers to the context so httpLink can read them
    return {
      headers: {
        ...headers,
        authorization: accessToken ? `Bearer ${accessToken}` : "",
      }
    }
  });
  
  const client = new ApolloClient({
    link: authLink.concat(httpLink),
    cache: new InMemoryCache()
  });

  return (
    <Security oktaAuth={oktaAuth} restoreOriginalUri={restoreOriginalUri}>
      <ApolloProvider client={client}>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <HeaderNav/>
          </Grid>
          <Grid item xs={12}>
            <Switch>
              {/* <Route path="/" exact={true} component={Home}/> */}
              <Route path="/login/callback" component={LoginCallback}/>
              <SecureRoute path="/messages" component={Messages}/>
              <SecureRoute path="/profile" component={Profile}/>
              <SecureRoute path="/books" component={Books}/>
            </Switch>
          </Grid>
        </Grid>
      </ApolloProvider>
    </Security>
  );
}

export default App;
