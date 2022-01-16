Feature: To check The POST method for Skills
Background:
	Given User is on Endpoint: url/Skills with valid username and password

Scenario Outline: Validate POST all Skills when user logged with UserName with Password   
    When User sends POST request body in skills from "<SheetName>" and <RowNumber> with valid JSON Schema
    Then User validates the StatusCode and StatusMessage from "<SheetName>" sheet and <RowNumber> row
    And JSON schema is valid for "<Method>" in Skills
    And User should receive the posted skill in JSON body from "<SheetName>" and <RowNumber>
    And check the Database for newly created skill
    Examples: 
    |SheetName  |RowNumber|Method|
    |Skills_POST|0        |POST |
    |Skills_POST|1        |POST |
    |Skills_POST|2        |POST |
    |Skills_POST|3        |POST |
    |Skills_POST|4        |POST |
    |Skills_POST|5        |POST |
    |Skills_POST|6        |POST |
    |Skills_POST|7        |POST |
   