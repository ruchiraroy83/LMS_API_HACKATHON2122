Feature: To check The PUT method for UserSkills
Background:
	Given User is on Endpoint: url/UserSkills with valid username and password
Scenario Outline: Validate PUT method for UserSkills
    When User sends PUT request on id and request body from "<SheetName>" and <RowNumber>
    And  request body is in valid json format
    Then User validates StatusCode and StatusMessage from "<SheetName>" and <RowNumber>
    And JSON schema is valid
    And User should receive a list of users with skills in JSON body with the fields like user_skill_id,user_id,Skill_Id,months_of_exp from "<SheetName>" and <RowNumber>
    And check the Database
  Examples: 
    |SheetName|RowNumber|
    |InputData_UserSkills_PUT|0|
    |InputData_UserSkills_PUT|1|
    |InputData_UserSkills_PUT|2|
    |InputData_UserSkills_PUT|3|
    |InputData_UserSkills_PUT|4|
   