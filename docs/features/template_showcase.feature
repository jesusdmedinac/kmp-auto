Feature: Interactive In-Car Template Showcase Suite
  As a developer evaluating kmp-auto
  I want dedicated sample screens for every official in-car template
  So that I can verify visual rendering and touch interactions on Android Auto and Apple CarPlay

  Scenario: Navigating to Quick Vehicle Controls Grid from Catalog
    Given the driver is on the Template Catalog screen
    When the driver selects the "Controles Rápidos (Grid)" item
    Then the AutoGridTemplate is pushed onto the car screen stack
    And 6 quick control buttons are rendered with icons
    When the driver taps "Modo Sport"
    Then the vehicle drive mode state toggles to active

  Scenario: Inspecting Vehicle Diagnostics and Battery via Pane Template
    Given the driver is on the Template Catalog screen
    When the driver selects the "Diagnóstico de Batería (Pane)" item
    Then the AutoPaneTemplate is pushed with vehicle telemetry items
    And primary action "Optimizar Carga" and secondary action "Ver Historial" are visible

  Scenario: Executing in-car destination search
    Given the driver navigates to the Search Template Showcase
    When the driver searches for "Polanco"
    Then the results list dynamically updates showing "Polanco Carga Rápida"
    When the driver selects the station
    Then the station detail screen is displayed

  Scenario: Switching sections in Multi-Tab Automotive Shell
    Given the driver opens the TabBar Showcase app
    Then tabs "Explorar", "Favoritos", "Buscar", and "Ajustes" are presented
    When the driver taps the "Favoritos" tab
    Then the Favorites screen is rendered without exceeding backstack limits
