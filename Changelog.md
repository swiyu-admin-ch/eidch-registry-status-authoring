# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## 1.1.14

### Changed

- Update maven from 3.9.10 to 3.9.11
- Update maven-wrapper from 3.3.2 to 3.3.4
- Update maven-wrapper from 3.3.2 to 3.3.4
- Update maven-wrapper from 3.3.2 to 3.3.4
- Update postgres from 15.13 to 15.14
- Update org.apache.maven.plugins:maven-compiler-plugin from 3.14.0 to 3.14.1
- Update org.apache.maven.plugins:maven-surefire-plugin from 3.5.2 to 3.5.4
- Update com.rudikershaw.gitbuildhook:git-build-hook-maven-plugin from 3.5.0 to 3.6.0
- Update io.hypersistence:hypersistence-utils-hibernate-63 from 3.10.2 to 3.11.0
- Update com.diffplug.spotless:spotless-maven-plugin from 2.45.0 to 3.0.0
- Update ch.admin.bit.jeap:jeap-spring-boot-parent from 26.68.0 to 27.2.0



## 1.1.13

### Changed

- Update maven from 3.9.9 to 3.9.10
- Update io.hypersistence:hypersistence-utils-hibernate-63 from 3.9.10 to 3.10.2
- Update com.diffplug.spotless:spotless-maven-plugin from 2.44.4 to 2.45.0
- Update ch.admin.bit.jeap:jeap-spring-boot-parent from 26.50.1 to 26.68.0


## 1.1.12

### Other

- Update Interface Summaries

## 1.1.11

### Changed

- Update org.apache.maven.plugins:maven-surefire-plugin from 3.5.2 to 3.5.3
- Update ch.admin.bit.jeap:jeap-spring-boot-parent from 26.50.0 to 26.50.1

## 1.1.11

### Changed

- Downgrade maven-surefire-plugin due to archunit incompatibility

## 1.1.10

### Changed

- Downgrade maven-sunfire-plugin due to archunit incompatibility

## 1.1.9

### Changed

- Update maven from 3.9.6 to 3.9.9
- Update postgres from 15.8 to 15.13
- Update org.apache.maven.plugins:maven-compiler-plugin from 3.12.1 to 3.14.0
- Update com.tngtech.archunit:archunit-junit5 from 1.4.0 to 1.4.1
- Update io.hypersistence:hypersistence-utils-hibernate-63 from 3.8.2 to 3.9.10
- Update org.jacoco:jacoco-maven-plugin from 0.8.9 to 0.8.13
- Update ch.admin.bit.jeap:jeap-spring-boot-parent from 26.48.0 to 26.50.0

## 1.1.8

### Other

- Fix audit metadata author for both user and api-gw call

## 1.1.7

### Other

- Added spotless plugin

## 1.1.6

### Changed

- internal improvements and dependency updates

## 1.1.5

### Changed

- Add AuditConfig to audit changes to db entities

## 1.1.4

### Changed

- internal improvements for local development

# 1.1.3

### Changed

- Set version of spring-security-crypto specifically to 6.4.4 to resolve vulnerability CVE CVE-2025-22228

# 1.1.2

### Changed

- Upgrade parent version due to vulnerabilities with CVEs: CVE-2024-57699, CVE-2025-24813 and CVE-2025-22228

# 1.1.1

### Changed

- Refactored code structure to use JEAP, since it is now open-sourced

## 1.1.0

### Added

- Extending prometheus export with metrics for build

## 1.0.0

- Initial Release 
