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
- [x] Implement `androidMain` engine (mapping `AutoScreen` & `AutoTemplate` to `androidx.car.app`)
- [x] Implement `iosMain` engine (mapping `AutoScreen` & `AutoTemplate` to `platform.CarPlay.*`)
- [x] Implement reactive template invalidation (`StateFlow` & `screen.invalidate()`)

## Milestone 5: Sample Application & Integration Verification
- [x] Build multiplatform automotive sample app (EV Charging & POI Stations)
- [x] Verify Android Auto DHU / AAOS compatibility and CarPlay scene delegate wiring
- [x] Live execution & verification on Android Automotive emulator (`Automotive_1408p_landscape`)

## Milestone 6: Advanced In-Car Templates Expansion (`:core`) (#3)
- [ ] Scenario: Interactive in-car search with query filter and dynamic results (`advanced_templates.feature:Scenario 1`)
- [ ] Scenario: Root TabBar container with seamless section switching (`advanced_templates.feature:Scenario 2`)
- [ ] Scenario: In-car Action Sheet modal with selectable choices (`advanced_templates.feature:Scenario 3`)
- [ ] Update `AndroidTemplateMapper` for Search, Tab, and Action Sheet templates
- [ ] Update `IosTemplateMapper` for `CPSearchTemplate`, `CPTabBarTemplate`, and `CPActionSheetTemplate`

## Milestone 7: Interactive Template Showcase Suite (`:sample`) (#4)
- [ ] Scenario: Navigating to Quick Vehicle Controls Grid from Catalog (`template_showcase.feature:Scenario 1`)
- [ ] Scenario: Inspecting Vehicle Diagnostics and Battery via Pane Template (`template_showcase.feature:Scenario 2`)
- [ ] Scenario: Executing in-car destination search (`template_showcase.feature:Scenario 3`)
- [ ] Scenario: Switching sections in Multi-Tab Automotive Shell (`template_showcase.feature:Scenario 4`)
- [ ] Implement `TemplateCatalogScreen` as central hub for all template samples
- [ ] Verify live rendering and touch events on Android Automotive emulator

## Milestone 8: Apple CarPlay Shell & Host (`iosApp`) (#5)
- [ ] Create `iosApp` host project with `iOSApp.swift`
- [ ] Configure `Info.plist` with `CPTemplateApplicationSceneSessionRoleApplication`
- [ ] Verify non-navigation scene delegate connection in `SampleCarPlaySceneDelegate`
- [ ] Validate compilation with `embedAndSignAppleFrameworkForXcode`

## Milestone 9: Template Official para `kmp-cli` (`kmp create --template kmp-auto`) (#6)
- [ ] Scenario: Scaffolding a new in-car project with kmp create --template kmp-auto (`kmp_cli_integration.feature:Scenario 1`)
- [ ] Package template structure with parameters (`__PROJECT_NAME__`, `__PACKAGE_NAME__`)
- [ ] Register `kmp-auto` template in `kmp-cli` template registry

## Milestone 10: Skill Oficial `kmp-auto-expert` y Auto-Aprovisionamiento (#7)
- [ ] Scenario: Automatic automotive skills provisioning on project creation (`kmp_cli_integration.feature:Scenario 2`)
- [ ] Author `kmp-auto-expert` skill in `kmp-cli-plugin/skills/kmp-auto-expert/SKILL.md`
- [ ] Document in-car distraction rules, template catalog, and diagnostic runbooks

## Milestone 11: Documentación, Referencias Cruzadas y Publicación (#8)
- [ ] Scenario: Mutual ecosystem cross-referencing between kmp-auto and kmp-cli (`kmp_cli_integration.feature:Scenario 3`)
- [ ] Comprehensive `kmp-auto/README.md` with library usage instructions and `kmp-cli` recommendation
- [ ] Cross-reference `kmp-auto` in `kmp-cli` templates documentation
