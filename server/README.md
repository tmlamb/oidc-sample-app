This is an OAuth 2.0 Resource Server written in Java.

This project integrates with Okta's OAuth server SDK (okta-spring-boot-starter) to perform the OAuth 2.0 flow. Configure `application.yml` with your Okta Issuer domain before running the app:

```yml
okta:
  oauth2:
    issuer: https://dev-1234567.okta.com/oauth2/default
```

It uses Gradle and Spring Boot, so to run locally execute the `bootRun` task:

```shell
./gradlew bootRun
```

With the resource server running, you can start and test the API calls from the [public client](../client) application.
