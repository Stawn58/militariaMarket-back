# Copilot / Agent Instructions

This file contains concise, practical instructions for AI assistants and contributors working on this repository. Follow
these rules to produce safe, high-quality, and buildable changes.

Application Purpose

- MilitiariaApp Backend: A Java-based backend service for managing a collection of militaria items, including user
  authentication, item cataloging, and gallery management.

Tech Stack

- Java 21, Maven, Spring Boot, JPA/Hibernate, JUnit 5, Mockito, MapStruct.

Purpose

- Help contributors and automated agents make small-to-medium code changes, add tests, and fix bugs without breaking the
  build.

Repository overview (quick)

- Java (Maven) backend service.
- Uses JDK 21 (see compiler flags in build output).
- Testing: JUnit 5 and Mockito; MapStruct is used for mappers.
- Build wrapper available: `./mvnw` (use this to ensure consistent Maven version).

High-level workflow for changes

1. Read the relevant source files before editing. Trace symbols (classes, interfaces) to their implementations and
   tests.
2. Make the smallest change possible that solves the issue. Prefer focused, well-tested edits.
3. Run the tests that cover the changed code, then run the full test suite when practical.

Commands (copyable)

```bash
# Run all tests
./mvnw test

# Run a single test class (fast)
./mvnw -Dtest=com.militiariaapp.backend.gallery.service.impl.GalleryServiceImplTest test

# Run a single test method
./mvnw -Dtest=com.militiariaapp.backend.gallery.service.impl.GalleryServiceImplTest#saveGallery_happyPath_savesGallery test
```

Editing and coding conventions

- Preserve existing code style and imports. Avoid large reformatting unrelated to the change.
- When editing production code, also add or update unit tests for the behavior change.
- Keep public APIs stable unless a breaking change is explicitly requested; if a breaking change is required, include a
  migration note in the PR.
- For DTO/entity mapping, reuse existing MapStruct mappers (use `Mappers.getMapper(...)`) if present.
- For database entities, respect JPA annotations and cascade rules; avoid adding DB schema changes without a Liquibase
  change set.
- When trying to fetch the first element in a list, prefer using 'getFirst()' instead of 'get(0)'.
- Avoid adding comments in the code unless asked; prefer self-explanatory code.
- Write clean, readable code; prefer meaningful variable/method names over comments.
- In a Service class, always name the variable "repository" when referring to the corresponding Repository.

Testing guidance

- Add unit tests under `src/test/java` following existing patterns (see `MilitariaUnitTests` base class).
- Use Mockito for mocking repositories and other dependencies, and prefer `ArgumentCaptor` to assert saved entities.
- After adding tests, run the tests locally using `./mvnw -Dtest=... test`.
- If adding long-running integration tests, tag them separately so they can be excluded from quick runs.
- Avoid adding comments in tests; prefer self-explanatory test method names.
- Avoid using @DisplayName in tests; prefer descriptive method names instead.
- Avoid using @MockBean as it is deprecated; use Mockito's @Mock and @InjectMocks instead.
- Use method names like "<TestedMethodName>_Should<ExpectedBehavior>When<Condition>" for test methods.

Safety and secrets

- Never hard-code secrets, credentials, or private keys in code or tests.
- Do not attempt to access external services or the network from unit tests (mock them instead).

Build and validation steps (minimum before creating a PR)

- Compile the project: `./mvnw -q -DskipTests=false test` (this compiles and runs tests; remove `-q` to see full
  output).
- Ensure `mvn` surefire results show 0 failures and 0 errors.
- Run `mvn -DskipTests=false package` only if you need to create an artifact.

Commit and PR guidance

- Create a focused branch: `feat/<short-desc>` or `fix/<short-desc>`.
- Write a concise commit message: one-line summary and an optional short body explaining the why.
- Include test results or a short note about manual verification in the PR description.

When to ask the human

- If a change affects database schema, public API contracts, or versioning, request human review before merging.
- If authentication, secrets management, or external integrations are involved, stop and ask.

Agent constraints

- Do not modify files outside the repository root without explicit permission from a human maintainer.
- Do not add large or uncommon dependencies; prefer standard, widely-used libraries already in the Maven central
  ecosystem.
- If uncertain about design choices, create a draft PR and include questions for reviewers.

Helpful patterns & examples

- Use `ArgumentCaptor` when asserting saved entity fields in repository tests.
- Mock repository `save(...)` to return the passed entity via `thenAnswer(invocation -> invocation.getArgument(0))` for
  simple mapper tests.

Contact / escalation

- If tests fail or changes are non-trivial, leave a clear note in the PR and ping a human maintainer.

This file should be kept short and actionable. If you need to document longer processes (DB migrations, release steps),
add separate markdown files in `.github/` or `docs/` and link them here.

