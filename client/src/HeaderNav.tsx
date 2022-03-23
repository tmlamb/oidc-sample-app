import { useOktaAuth } from "@okta/okta-react";
import { UserClaims } from "@okta/okta-auth-js";
import { useState, useEffect } from "react";

const HeaderNav = () => {
  const { authState, oktaAuth } = useOktaAuth();
  const [userInfo, setUserInfo] = useState({} as UserClaims | null);

  useEffect(() => {
    if (!authState || !authState.isAuthenticated) {
      setUserInfo(null);
    } else {
      oktaAuth.getUser().then((info) => {
        setUserInfo(info);
      });
    }
  }, [authState, oktaAuth]);

  const login = async () => {
    await oktaAuth.signInWithRedirect();
  };

  const logout = async () => {
    oktaAuth.signOut({ postLogoutRedirectUri: window.location.origin + "/" });
  };

  // if (!authState || (authState.isAuthenticated && !userInfo)) {
  //   return (
  //     <div>Loading...</div>
  //   );
  // }

  return (
    <header>
      {/* {(!authState || (authState.isAuthenticated && !userInfo)) && (
        <span>Loading...</span>
      )} */}
      {authState && !authState.isAuthenticated && (
        <button onClick={login}>Login</button>
      )}
      {authState && authState.isAuthenticated && userInfo && (
        <div>
          <a href="/profile">Profile</a>
          <br></br>
          <a href="/messages">Messages</a>
          <br></br>
          <a href="/books">Books</a>
          <button onClick={logout}>Logout</button>
        </div>
      )}
    </header>
  );
};
export default HeaderNav;
