## Routing

### Requirement: Route requests to backend services by path prefix

Feature: Request Routing
Rule: The gateway routes incoming requests to the registered backend service whose path predicate matches first

#### Scenario: Route matches a connector API request
- **GIVEN** a request arrives at `/api/connectors/list`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-connectors` service

#### Scenario: Route matches a direct connector request
- **GIVEN** a request arrives at `/connectors/status`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-connectors` service

#### Scenario: Route matches a data-service API request
- **GIVEN** a request arrives at `/api/datasets`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-ds-core` service

#### Scenario: Route matches a health check request
- **GIVEN** a request arrives at `/health`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-workflow` service

#### Scenario: Route matches an admin request
- **GIVEN** a request arrives at `/admin/users`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-admin-app` service

#### Scenario: Route matches a workflow request
- **GIVEN** a request arrives at `/wf/start`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-workflow` service

#### Scenario: Route matches a scheduler request
- **GIVEN** a request arrives at `/scheduler/jobs`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-scheduler` service

#### Scenario: Route matches an export request
- **GIVEN** a request arrives at `/ioExport`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-export` service

#### Scenario: Route matches a direct export request
- **GIVEN** a request arrives at `/export/reports`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-export` service

#### Scenario: Route matches an authentication service request
- **GIVEN** a request arrives at `/authentication/login`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-authentication` service

#### Scenario: Route matches Swagger documentation
- **GIVEN** a request arrives at `/v3/api-docs`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-ds-core` service

### Requirement: Routes are evaluated in definition order, first match wins

Feature: Route Priority
Rule: Routes are evaluated in the order they are defined and the first matching predicate determines the target service

#### Scenario: More specific route is defined before broader route
- **GIVEN** route 0 matches `/api/connectors/**` to `xarvis-connectors`
- **AND** route 1 matches `/api/**` to `xarvis-ds-core`
- **WHEN** a request arrives at `/api/connectors/list`
- **THEN** the request is forwarded to `xarvis-connectors` (route 0)
- **AND** route 1 is not evaluated

#### Scenario: First matching route wins for overlapping paths
- **GIVEN** route 2 matches `/admin/**` to `xarvis-admin-app`
- **AND** route 1 matches `/api/**` to `xarvis-ds-core`
- **WHEN** a request arrives at `/admin/dashboard`
- **THEN** the request is forwarded to the first matching route

### Requirement: Unmatched paths fall through to the SPA controller

#### Scenario: No route matches a request
- **GIVEN** a request arrives at an unknown path `/unknown/resource`
- **WHEN** the gateway evaluates all route predicates
- **AND** no predicate matches the path
- **THEN** the request is handled by the SPA forward controller

### Requirement: The gateway preserves the original host header for proxied requests

#### Scenario: Host header is preserved when proxying
- **GIVEN** a request arrives at `/api/datasets` with a `Host` header
- **WHEN** the gateway forwards the request to `xarvis-ds-core`
- **THEN** the original `Host` header is included in the proxied request

### Requirement: Forwarded headers are propagated to downstream services

Feature: Forwarded Headers
Rule: The gateway forwards proxy protocol headers so downstream services receive the original client IP and protocol

#### Scenario: Forwarded header is set by the gateway
- **GIVEN** a client sends a request through the gateway
- **WHEN** the gateway forwards the request to a backend service
- **THEN** the proxied request includes forwarded headers indicating the original client IP and protocol

### Requirement: Routing to the data service covers multiple path prefixes

#### Scenario: Cache path is routed to data service
- **GIVEN** a request arrives at `/cache/entries`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to `xarvis-ds-core`

#### Scenario: Service status path is routed to data service
- **GIVEN** a request arrives at `/service/status`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to `xarvis-ds-core`

#### Scenario: Data source path is routed to data service
- **GIVEN** a request arrives at `/ds/config`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to `xarvis-ds-core`

#### Scenario: Health check path is routed to workflow service
- **GIVEN** a request arrives at `/hs/ping`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to `xarvis-ds-core`
