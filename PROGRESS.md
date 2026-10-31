# Project Progress Roadmap: kmp-auto

## Milestone 1: Living Foundation (Cimientos Vivos)
- [x] Initialize KMP multiplatform library scaffolding for Android and iOS (Apple CarPlay)
- [x] Configure `build.gradle.kts` with `androidx.car.app` and iOS CarPlay framework linking
- [x] Verify clean compilation of foundation targets

## Milestone 2: Feature - In-Car Navigation & Backstack Safety (#1)
- [x] Scenario: Navigating forward within safety depth limits (`navigation.feature:Scenario 1`)
- [x] Scenario: Preventing crashes when exceeding the 5-step car backstack limit (`navigation.feature:Scenario 2`)
- [x] Scenario: Popping screens and returning to root (`navigation.feature:Scenario 3`)

## Milestone 3: Feature - Unified Declarative Templates (#2)
- [x] Scenario: Creating a List Template with sections and actions (`templates.feature:Scenario 1`)
- [x] Scenario: Creating a Grid Template with grid buttons (`templates.feature:Scenario 2`)
- [x] Scenario: Creating an Information Pane Template (`templates.feature:Scenario 3`)
- [x] Scenario: Creating a Message Template for alerts and notifications (`templates.feature:Scenario 4`)

## Milestone 4: Native Platform Engines & Mappers
- [ ] Implement `androidMain` engine (mapping `AutoScreen` & `AutoTemplate` to `androidx.car.app`)
- [ ] Implement `iosMain` engine (mapping `AutoScreen` & `AutoTemplate` to `platform.CarPlay.*`)
- [ ] Implement reactive template invalidation (`StateFlow` & `screen.invalidate()`)

## Milestone 5: Sample Application & Integration Verification
- [ ] Build multiplatform automotive sample app (EV Charging & POI Stations)
- [ ] Verify Android Auto DHU / AAOS compatibility and CarPlay scene delegate wiring
