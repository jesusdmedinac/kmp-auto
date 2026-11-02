Feature: Advanced In-Car Templates
  As an in-car application developer
  I want unified declarative templates for Search, TabBar, and Action Sheets
  So that I can build comprehensive automotive experiences across Android Auto and Apple CarPlay

  Scenario: Interactive in-car search with query filter and dynamic results
    Given a Search Screen configured with AutoSearchTemplate
    When the driver types the search query "Supercharger"
    Then the search query callback is triggered with "Supercharger"
    And the template renders matching search result items
    And back navigation returns cleanly to the previous screen

  Scenario: Root TabBar container with seamless section switching
    Given a root TabBar configured with AutoTabBarTemplate containing 4 tabs
    When the driver switches from tab index 0 "Explorar" to tab index 1 "Favoritos"
    Then the active tab updates to index 1
    And the corresponding tab root screen is displayed
    And the car safety backstack depth is preserved across tab transitions

  Scenario: In-car Action Sheet modal with selectable choices
    Given a screen displaying an AutoActionSheetTemplate with 3 actions
    When the driver selects the secondary action "Cancelar Reserva"
    Then the action callback executes
    And the modal action sheet dismisses cleanly
