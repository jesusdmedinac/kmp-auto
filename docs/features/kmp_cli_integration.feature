Feature: KMP CLI Template and Automotive Skills Integration
  As a developer using kmp-cli
  I want to scaffold in-car applications and access specialized automotive skills
  So that I can build verified Android Auto and Apple CarPlay apps with zero boilerplate

  Scenario: Scaffolding a new in-car project with kmp create --template kmp-auto
    Given the developer executes "kmp create my-car-app --template kmp-auto"
    Then a multiplatform project is generated with ":core" dependency
    And the Android Automotive shell is configured with CarAppActivity and automotive_app_desc.xml
    And the Apple CarPlay shell is configured with CarPlaySceneDelegate and Info.plist
    And compilation targets pass verification for Android and iOS

  Scenario: Automatic automotive skills provisioning on project creation
    Given a new project is created using "--template kmp-auto"
    Then the "kmp-auto-expert" skill is linked into the project
    And companion skills "carplay" and "android-cli" are available for agent assistance

  Scenario: Mutual ecosystem cross-referencing between kmp-auto and kmp-cli
    Given the kmp-auto documentation and README
    Then it features kmp-cli as the recommended tooling for scaffolding and diagnostics
    And the kmp-cli template catalog lists kmp-auto as the official automotive template
