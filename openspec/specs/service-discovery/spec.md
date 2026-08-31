## Service Discovery

### Requirement: Register the gateway with Eureka on startup

Feature: Service Discovery
Rule: The gateway registers itself with the Eureka discovery server so it is visible in the service registry

#### Scenario: Gateway registers on startup
- **GIVEN** the Eureka server is running at the configured URL
- **WHEN** the gateway application starts
- **THEN** the gateway registers with Eureka under the name `xarvis-gateway`

#### Scenario: Gateway uses IP address for registration
- **GIVEN** the gateway has `preferIpAddress` enabled
- **WHEN** the gateway registers with Eureka
- **THEN** the registration uses the container or host IP address rather than the hostname

### Requirement: Discover backend services via Eureka

Feature: Backend Service Discovery
Rule: The gateway resolves backend service URIs from the Eureka registry using load-balanced logical names

#### Scenario: Route resolves via service discovery
- **GIVEN** a request path matches a route with URI `lb://xarvis-ds-core`
- **WHEN** the gateway forwards the request
- **THEN** the target address is resolved from the Eureka registry
- **AND** the request reaches an available instance of `xarvis-ds-core`

#### Scenario: Route resolves for xarvis-connector via service discovery
- **GIVEN** a request path matches a route with URI `lb://xarvis-connector`
- **WHEN** the gateway forwards the request
- **THEN** the target address is resolved from the Eureka registry
- **AND** the request reaches an available instance of `xarvis-connector`

#### Scenario: Route resolves for xarvis-scheduler via service discovery
- **GIVEN** a request path matches a route with URI `lb://xarvis-scheduler`
- **WHEN** the gateway forwards the request
- **THEN** the target address is resolved from the Eureka registry
- **AND** the request reaches an available instance of `xarvis-scheduler`

#### Scenario: Route resolves for xarvis-export via service discovery
- **GIVEN** a request path matches a route with URI `lb://xarvis-export`
- **WHEN** the gateway forwards the request
- **THEN** the target address is resolved from the Eureka registry
- **AND** the request reaches an available instance of `xarvis-export`

#### Scenario: Route resolves for xarvis-authentication via service discovery
- **GIVEN** a request path matches a route with URI `lb://xarvis-authentication`
- **WHEN** the gateway forwards the request
- **THEN** the target address is resolved from the Eureka registry
- **AND** the request reaches an available instance of `xarvis-authentication`

#### Scenario: New service is unavailable and no instances are registered
- **GIVEN** a backend service has no instances registered in Eureka
- **WHEN** a request arrives matching that service's route
- **THEN** the gateway returns a service unavailable response

### Requirement: Fetch the registry on startup

#### Scenario: Gateway fetches registry on startup
- **GIVEN** the gateway has `fetchRegistry` enabled
- **WHEN** the gateway starts
- **THEN** it retrieves the current Eureka registry to discover available services
