Feature: As Kalah game player
  I would like create game
  In order to play game

  Scenario: Create Kalah game
    When Game is created
    Then I should receive game

  Scenario: Play Kalah game
    Given Game is created
    When I make a move
    Then Game is updated