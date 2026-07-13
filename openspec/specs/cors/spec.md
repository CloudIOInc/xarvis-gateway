## CORS

### Requirement: Allow cross-origin requests from permitted origins

Feature: Cross-Origin Resource Sharing
Rule: The gateway responds to CORS preflight requests and includes CORS headers on responses for configured allowed origins

#### Scenario: Preflight request from a permitted origin succeeds
- **GIVEN** the origin is `http://localhost:3000`
- **WHEN** a browser sends an `OPTIONS` preflight request with method `POST`
- **THEN** the response includes `Access-Control-Allow-Origin` matching the request origin
- **AND** the response includes `Access-Control-Allow-Methods` with `*`
- **AND** the response includes `Access-Control-Allow-Headers` with `*`
- **AND** the response includes `Access-Control-Allow-Credentials` with `true`

#### Scenario: Preflight request from a subdomain of cloudio.io succeeds
- **GIVEN** the origin is `https://xarvis-dev.cloudio.io`
- **WHEN** a browser sends an `OPTIONS` preflight request
- **THEN** the response includes `Access-Control-Allow-Origin` matching the request origin

#### Scenario: Preflight request from any subdomain of cloudio.io succeeds
- **GIVEN** the origin is `https://any-team.cloudio.io`
- **WHEN** a browser sends an `OPTIONS` preflight request
- **THEN** the response includes `Access-Control-Allow-Origin` matching the request origin

#### Scenario: Preflight request from an Azure BYOP origin succeeds
- **GIVEN** the origin is `https://byop.azurewebsites.net`
- **WHEN** a browser sends an `OPTIONS` preflight request
- **THEN** the response includes CORS headers allowing the request

#### Scenario: Preflight request from Microsoft login origin succeeds
- **GIVEN** the origin is `https://login.microsoftonline.com`
- **WHEN** a browser sends an `OPTIONS` preflight request
- **THEN** the response includes CORS headers allowing the request

#### Scenario: Preflight request from a non-permitted origin is rejected
- **GIVEN** the origin is `https://untrusted-site.com`
- **WHEN** a browser sends an `OPTIONS` preflight request
- **THEN** the response does not include `Access-Control-Allow-Origin`

### Requirement: Credentials are supported in cross-origin requests

#### Scenario: Authenticated cross-origin request includes credentials
- **GIVEN** the origin is `http://localhost:3000`
- **AND** the request includes cookies or authorization headers
- **WHEN** the gateway processes the request
- **THEN** the response includes `Access-Control-Allow-Credentials: true`
