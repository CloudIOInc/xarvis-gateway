## ADDED Requirements

### Requirement: Gateway MUST route scheduler requests to xarvis-scheduler

The gateway SHALL route requests with the `/scheduler/**` path prefix to the `xarvis-scheduler` service.

Feature: Scheduler Routing
Rule: The gateway routes requests with the `/scheduler/**` path prefix to the `xarvis-scheduler` backend service

#### Scenario: Route matches a scheduler job creation request
- **GIVEN** a request arrives at `/scheduler/jobs/create`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-scheduler` service

#### Scenario: Route matches a scheduler job status request
- **GIVEN** a request arrives at `/scheduler/jobs/123/status`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-scheduler` service

#### Scenario: Route matches a scheduler configuration request
- **GIVEN** a request arrives at `/scheduler/config`
- **WHEN** the gateway evaluates the route predicates
- **THEN** the request is forwarded to the `xarvis-scheduler` service
