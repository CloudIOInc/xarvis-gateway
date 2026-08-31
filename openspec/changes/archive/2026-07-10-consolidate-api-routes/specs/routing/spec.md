## MODIFIED Requirements

### Requirement: Gateway MUST route requests to backend services by path prefix

The gateway SHALL route incoming requests to backend services based on path prefix matching.

Feature: Request Routing
Rule: The gateway routes incoming requests to the registered backend service whose path predicate matches first

#### Scenario: Route matches a connector API request
- **GIVEN** a request arrives at `/api/connectors/list`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-connectors` service

#### Scenario: Route matches a direct connector request via new path
- **GIVEN** a request arrives at `/connectors/status`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-connectors` service

#### Scenario: Route matches an existing export request
- **GIVEN** a request arrives at `/ioExport`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-export` service

#### Scenario: Route matches a direct export request via new path
- **GIVEN** a request arrives at `/export/reports`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-export` service

#### Scenario: Route matches an existing SSO authentication request
- **GIVEN** a request arrives at `/sso/login`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-authentication` service

#### Scenario: Route matches an authentication service request via new path
- **GIVEN** a request arrives at `/authentication/login`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-authentication` service

#### Scenario: Route matches a scheduler service request
- **GIVEN** a request arrives at `/scheduler/jobs`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-scheduler` service
