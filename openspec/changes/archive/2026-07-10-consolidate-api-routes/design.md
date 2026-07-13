## Context

The gateway has 6 routes (0-5). Three of the target services already have routes and need predicate updates; one service (`xarvis-scheduler`) is new.

| Route | Service | Existing Predicate | Change |
|-------|---------|-------------------|--------|
| 0 | xarvis-connectors | `/api/connectors/**` | Add `/connectors/**` |
| 3 | xarvis-authentication | `/sso,/oidc,/auth/**` | Add `/authentication/**` |
| 5 | xarvis-export | `/ioExport` | Add `/export/**` |
| 6 (new) | xarvis-scheduler | — | New: `/scheduler/**` |

## Goals / Non-Goals

**Goals:**
- Merge the 3 new paths into existing route predicates where the service already exists
- Create 1 new route for the new service

**Non-Goals:**
- No changes to existing route order or unrelated routes

## Decisions

- Merge into existing predicates rather than creating separate route entries — reduces total route count, keeps each service's paths together
- New route at index 6 for xarvis-scheduler — first available index after existing routes

## Risks / Trade-offs

- Longer predicate strings are less readable but functionally equivalent
- xarvis-scheduler must be deployed and registered with Eureka for `lb://` to resolve

## Migration Plan

1. Update route 0: append `,/connectors/**` to predicate
2. Update route 3: append `,/authentication/**` to predicate
3. Update route 5: append `,/export/**` to predicate
4. Add route 6 for xarvis-scheduler
5. Update SPA controller exclusion regex
6. Deploy

## Open Questions

None.
