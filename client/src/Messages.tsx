import { useOktaAuth } from '@okta/okta-react';
import { useState, useEffect } from 'react';

import config from './config';

const Messages = () => {
  const { authState, oktaAuth } = useOktaAuth();
  const [messages, setMessages] = useState('');
  const [messageFetchFailed, setMessageFetchFailed] = useState(false);

  // fetch messages
  useEffect(() => {
    if (authState && authState.isAuthenticated) {
      const accessToken = oktaAuth.getAccessToken();
      fetch(config.resourceServer.messagesUrl, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      })
        .then((response) => {
          if (!response.ok) {
            return Promise.reject();
          }
          return response.text();
        })
        .then((data) => {
          setMessages(data);
          setMessageFetchFailed(false);
        })
        .catch((err) => {
          setMessageFetchFailed(true);
          /* eslint-disable no-console */
          console.error(err);
        });
    }
  }, [authState, oktaAuth]);

  return (
    <div>
      {messageFetchFailed && <div>Error Loading Messages</div>}
      {!messages && !messageFetchFailed && <p>Loading Messages..</p>}
      {messages
      && (
      <div>
        <div>
            {messages}
        </div>
      </div>
      )}
    </div>
  );
};

export default Messages;
