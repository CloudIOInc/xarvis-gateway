## Authentication Proxy

### Requirement: Route SSO login requests to the authentication service

Feature: Authentication Proxy
Rule: The gateway forwards authentication and SSO-related requests to the dedicated authentication service

#### Scenario: SSO login page request is proxied
- **GIVEN** a request arrives at `/sso`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-authentication` service

#### Scenario: SSO callback with path is proxied
- **GIVEN** a request arrives at `/sso/callback`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-authentication` service

#### Scenario: OIDC discovery request is proxied
- **GIVEN** a request arrives at `/oidc/.well-known/openid-configuration`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-authentication` service

#### Scenario: SSO handoff request is proxied
- **GIVEN** a request arrives at `/auth/sso-handoff/token`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-ds-core` service

#### Scenario: Authentication error page is proxied
- **GIVEN** a request arrives at `/auth/error`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-authentication` service

#### Scenario: External authentication callback is proxied to data service
- **GIVEN** a request arrives at `/auth/externalAuth`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-ds-core` service
