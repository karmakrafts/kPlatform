## [Unreleased]

### Changed

- Updated to Kotlin 2.4.10
- Updated to AGP 9.3.0
- Updated to Karma Convention 1.19.0
- Updated to Kotlin Wrappers 2026.7.2

## [1.1.0]

### Fixed

- Linux having no vendor string when running under NodeJS

### Changed

- Switched Kotlin/JS from CommonJS to ES modules
- Enabled ES2015 target for Kotlin/JS

## [1.0.1]

### Changed

- Run unit tests on all available platform in CI

### Fixed

- Automatic changelog update in CI
- `Platform.memory` being reported incorrectly on Windows
- Error cases not being handled for `AppleGlobalMemory` and `AppleRuntimeMemory`

## [1.0.0]

### Changed

- Initial release