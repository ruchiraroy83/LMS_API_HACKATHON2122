Feature: To check The POST method for Users
Background:
	Given UsersAPI User is on Endpoint: url/Users with valid username and password

Scenario Outline: Validate POST Users   
    When UsersAPI User  sends POST request body from "<SheetName>" and <RowNumber> with valid JSON Schema
    Then UsersAPI User validates StatusCode and StatusMessage from "<SheetName>" sheet and <RowNumber> row
    And  UsersAPI JSON schema is valid for "<Method>"
    And  UsersAPI User should receive the user details in JSON body from "<SheetName>" and <RowNumber>
    And  UsersAPI check the Database
    Examples: 
    |SheetName  				 |RowNumber|Method|
    |InputData_Users_POST|0        |POST |
    