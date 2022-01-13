Feature: To check The PUT method for UserSkills
Scenario Outline: Validate PUT all UserSkills when user logged in as "<UserName>" with "<Password>"
    Given User is on Endpoint: url/UserSkills
    When User sends PUT request on id and request body from "<SheetName>" and <RowNumber>
    And JSON schema is valid
    Then User validates StatusCode and StatusMessage from "<SheetName>" and <RowNumber>
    And User should receive a list of users with skills in JSON body with the fields - user_skill_id,user_id,Skill_Id,months_of_exp
    And check the Database
  Examples: 
    |SheetName|RowNumber|
    |InputData_UserSkills_PUT|0|
    |InputData_UserSkills_PUT|1|
    |InputData_UserSkills_PUT|2|
    |InputData_UserSkills_PUT|3|
    |InputData_UserSkills_PUT|4|
   