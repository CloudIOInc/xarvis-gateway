## 1. Merge Paths into Existing Route Predicates

- [x] 1.1 Add `/connectors/**` to route 0 predicate (`xarvis-connectors`)
- [x] 1.2 Add `/authentication/**` to route 3 predicate (`xarvis-authentication`)
- [x] 1.3 Add `/export/**` to route 5 predicate (`xarvis-export`)

## 2. Add New Route

- [x] 2.1 Add route for `/scheduler/**` → `lb://xarvis-scheduler`

## 3. Update SPA Forward Controller

- [x] 3.1 Add `connectors`, `scheduler`, `export`, `authentication` to path exclusion regex

## 4. Verify

- [x] 4.1 Run `openspec validate consolidate-api-routes --type change --strict`
