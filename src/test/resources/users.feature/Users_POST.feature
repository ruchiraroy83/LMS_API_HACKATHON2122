Feature: To check The POST method for Users
Background:
	Given User is on Endpoint: url/Users with valid username and password
	
Scenario Outline: Validate POST all UserSkills    
    When User sends POST request body from "<SheetName>" and <RowNumber> with valid JSON Schema for user api  
    Then User validates StatusCode and StatusMessage from "<SheetName>" sheet and <RowNumber> row for user api  
    And JSON schema is valid for Users api
    And User should receive a list of users in JSON body with the fields like user_id,name,phone_number,location,time_zone,linkedin_url from "<SheetName>" and <RowNumber>
    And check the Database for users
  Examples: 
    |SheetName|RowNumber|
    |InputData_UserSkills_POST|0|
    |InputData_UserSkills_POST|1|
    |InputData_UserSkills_POST|2|
    |InputData_UserSkills_POST|3|
    |InputData_UserSkills_POST|4|
    |InputData_UserSkills_POST|5|
    |InputData_UserSkills_POST|6|
    |InputData_UserSkills_POST|7|
    |InputData_UserSkills_POST|8|

