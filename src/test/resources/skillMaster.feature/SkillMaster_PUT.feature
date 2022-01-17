Feature: To check The PUT method for Skills
Background:
	Given Skills User is on Endpoint: url/Skills with valid username and password

Scenario Outline: Validate PUT all Skills when user logged with UserName with Password   
    When User sends PUT request body in skills from "<SheetName>" and <RowNumber> with valid JSON Schema
    Then skills User validates the StatusCode and StatusMessage from "<SheetName>" sheet and <RowNumber> row
    And JSON schema is valid for "<Method>" in Skills
    And User should receive a particular skill from "<SheetName>" and <RowNumber>
    And skills check the Database
    Examples: 
    |SheetName  |RowNumber|Method|
    |Skills_PUT |0        |PUT |
    |Skills_PUT |1        |PUT |
    |Skills_PUT |2        |PUT |
    |Skills_PUT |3        |PUT |
    |Skills_PUT |4        |PUT |
    |Skills_PUT |5        |PUT |
    |Skills_PUT |6        |PUT |
    |Skills_PUT |7        |PUT |
    |Skills_PUT |8        |PUT |
    