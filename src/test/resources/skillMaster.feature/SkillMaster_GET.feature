Feature: To check The GET method for Skills
Background:
   Given Skills User is on Endpoint: url/Skills with valid username and password
  
Scenario: Verify GET all Skills for Skill Master 
    When skills User sends GET request 
    Then skills User validates StatusCode
    And skills JSON schema is valid
    And check the Database for skills
     

Scenario Outline: Validate Skills Api with specific ID  
    When User sends GET request on skill id from "<SheetName>" and <RowNumber>
    Then User validates the StatusCode and StatusMessage from "<SheetName>" sheet and <RowNumber> row
    And JSON schema is valid for "<Method>" in Skills
    And User should receive a particular skill from "<SheetName>" and <RowNumber>
    And check the Database for validation  
    Examples: 
    |SheetName|RowNumber| Method|
    |Skills_GET|0       |  GET  |
   	|Skills_GET|1       |  GET  |
   	|Skills_GET|2       |  GET  |
   	|Skills_GET|3       |  GET  |
   	|Skills_GET|4       |  GET  |
   	|Skills_GET|5       |  GET  |
	