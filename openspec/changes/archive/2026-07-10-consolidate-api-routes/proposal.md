## Why

New API path prefixes need to be exposed for existing microservices. Since routes already exist for `xarvis-connectors`, `xarvis-export`, and `xarvis-authentication`, the new paths should be merged into their existing predicates to keep the configuration compact and maintain a single route per service.

## What Changes

- Add `/connectors/**` to existing route 0 predicate (`xarvis-connectors`, currently `/api/connectors/**`)
- Add `/authentication/**` to existing route 3 predicate (`xarvis-authentication`, currently `/sso,/oidc,/auth/**`)
- Add `/export/**` to existing route 5 predicate (`xarvis-export`, currently `/ioExport`)
- Add new route for `/scheduler/**` → `lb://xarvis-scheduler`

## Capabilities

### New Capabilities
- `scheduler-routing`: Route requests with `/scheduler/**` path prefix to the `xarvis-scheduler` backend service

### Modified Capabilities
- `routing`: Add `/connectors/**`, `/export/**`, `/authentication/**` to existing route predicates

## Impact

- `application.properties`: Modify predicates of routes 0, 3, 5; add 1 new route entry for xarvis-scheduler
- `SpaForwardController.java`: Add `connectors`, `scheduler`, `export`, `authentication` to path exclusion regex
- No breaking changes
