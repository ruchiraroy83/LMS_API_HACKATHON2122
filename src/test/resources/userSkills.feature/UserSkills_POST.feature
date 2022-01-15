Feature: To check The POST method for UserSkills
Background:
	Given User is on Endpoint: url/UserSkills with valid username and password
	
Scenario Outline: Validate POST all UserSkills    
    When User sends POST request body from "<SheetName>" and <RowNumber> with valid JSON Schema
    Then User validates StatusCode and StatusMessage from "<SheetName>" sheet and <RowNumber> row
    And JSON schema is valid
    And User should receive a list of users with skills in JSON body with the fields like user_skill_id,user_id,Skill_Id,months_of_exp from "<SheetName>" and <RowNumber>
    And check the Database
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

