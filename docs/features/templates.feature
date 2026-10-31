Feature: Unified Declarative In-Car Templates
  As a multiplatform developer building automotive applications
  I want to declare common in-car templates (List, Grid, Pane, Message)
  So that they map deterministically to Android Auto (androidx.car.app) and Apple CarPlay (CarPlay.framework)

  Scenario: Creating a List Template with sections and actions
    Given a list of items with titles, subtitles, and icons
    When building an AutoListTemplate with a back HeaderAction
    Then the template contains the expected sections and item count
    And it complies with maximum list item constraints

  Scenario: Creating a Grid Template with grid buttons
    Given an icon grid with titles and click actions
    When building an AutoGridTemplate
    Then the grid item count must not exceed the automotive platform limit of 8 items

  Scenario: Creating an Information Pane Template
    Given key-value information rows and call-to-action buttons
    When building an AutoPaneTemplate
    Then the pane encapsulates rows and action buttons within platform limits

  Scenario: Creating a Message Template for alerts and notifications
    Given an alert message, an icon, and confirm/dismiss actions
    When building an AutoMessageTemplate
    Then the message template defines the primary and secondary user actions
