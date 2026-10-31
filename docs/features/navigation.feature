Feature: In-Car Navigation and Backstack Safety
  As a multiplatform developer building for Android Auto and Apple CarPlay
  I want a unified AutoNavigator with automatic backstack depth guards
  So that my app never exceeds the 5-step host driver distraction limits and crashes

  Scenario: Navigating forward within safety depth limits
    Given a freshly initialized AutoNavigator with a root AutoScreen
    When pushing 3 subsequent AutoScreens
    Then the active stack depth should be 4
    And the top screen should match the last pushed screen

  Scenario: Preventing crashes when exceeding the 5-step car backstack limit
    Given an AutoNavigator with 5 screens on the backstack
    When attempting to push a 6th AutoScreen without popping
    Then the navigator should trigger the configured BackstackGuard policy
    And avoid exceeding platform host limits

  Scenario: Popping screens and returning to root
    Given an AutoNavigator with 4 screens on the backstack
    When calling pop
    Then the active stack depth should be 3
    When calling popToRoot
    Then the active stack depth should be 1
    And the top screen should be the initial root screen
