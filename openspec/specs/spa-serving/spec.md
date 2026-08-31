## SPA Serving

### Requirement: Serve the SPA index page at the root

Feature: SPA Frontend Hosting
Rule: The gateway serves the built SPA from a configurable static path and forwards client-side routes to `index.html`

#### Scenario: Root path returns the SPA index
- **GIVEN** the SPA is built and available at the configured static path
- **WHEN** a client requests `GET /`
- **THEN** the gateway responds with `index.html`
- **AND** the content type is `text/html`

#### Scenario: Static path not configured returns 404
- **GIVEN** the SPA is not built or the static path is misconfigured
- **WHEN** a client requests `GET /`
- **THEN** the gateway responds with `404 Not Found`

### Requirement: Excluded paths are not forwarded to the SPA

Feature: SPA Exclusion List
Rule: Paths matching configured backend route prefixes (api, sso, oidc, auth, admin, etc.) are excluded from SPA forwarding and handled by route predicates instead

#### Scenario: Resource3 path is excluded from SPA
- **GIVEN** a request path starts with `/resource3`
- **WHEN** the gateway evaluates the SPA forward controller
- **THEN** the request is not handled by the SPA forward controller

#### Scenario: React path is excluded from SPA
- **GIVEN** a request path starts with `/react`
- **WHEN** the gateway evaluates the SPA forward controller
- **THEN** the request is not handled by the SPA forward controller

#### Scenario: Export path is excluded from SPA
- **GIVEN** a request path starts with `/ioExport`
- **WHEN** the gateway evaluates the SPA forward controller
- **THEN** the request is not handled by the SPA forward controller

#### Scenario: Cache path is excluded from SPA
- **GIVEN** a request path starts with `/cache`
- **WHEN** the gateway evaluates the SPA forward controller
- **THEN** the request is not handled by the SPA forward controller

#### Scenario: Data source path is excluded from SPA
- **GIVEN** a request path starts with `/ds`
- **WHEN** the gateway evaluates the SPA forward controller
- **THEN** the request is not handled by the SPA forward controller

#### Scenario: Swagger path is excluded from SPA
- **GIVEN** a request path starts with `/swagger-ui`
- **WHEN** the gateway evaluates the SPA forward controller
- **THEN** the request is not handled by the SPA forward controller

#### Scenario: Swagger resources path is excluded from SPA
- **GIVEN** a request path starts with `/swagger-resources`
- **WHEN** the gateway evaluates the SPA forward controller
- **THEN** the request is not handled by the SPA forward controller

#### Scenario: Workflow path is excluded from SPA
- **GIVEN** a request path starts with `/wf`
- **WHEN** the gateway evaluates the SPA forward controller
- **THEN** the request is not handled by the SPA forward controller

### Requirement: Forward client-side routes to the SPA

#### Scenario: Client-side route without a dot is forwarded
- **GIVEN** a client-side route such as `/dashboard/settings`
- **WHEN** the request path does not match a backend route predicate
- **AND** the path contains no file extension
- **THEN** the gateway returns `index.html`

#### Scenario: API routes are not forwarded to the SPA
- **GIVEN** a request path starts with `/api`
- **WHEN** the gateway evaluates the SPA forward controller
- **THEN** the request is not handled by the SPA forward controller

#### Scenario: SSO routes are not forwarded to the SPA
- **GIVEN** a request path starts with `/sso`
- **WHEN** the gateway evaluates the SPA forward controller
- **THEN** the request is not handled by the SPA forward controller

#### Scenario: Admin routes are not forwarded to the SPA
- **GIVEN** a request path starts with `/admin`
- **WHEN** the gateway evaluates the SPA forward controller
- **THEN** the request is not handled by the SPA forward controller

#### Scenario: Deep client-side route with multiple segments is forwarded
- **GIVEN** a client-side route such as `/workspaces/123/projects/456/settings`
- **WHEN** the request path does not match a backend route
- **THEN** the gateway returns `index.html`

#### Scenario: Direct file asset requests are not forwarded
- **GIVEN** a request path includes a file extension such as `/static/js/app.js`
- **WHEN** the gateway evaluates the SPA forward controller
- **THEN** the request is not handled by the SPA forward controller
- **AND** the static resource is served directly
