Feature: To check The POST method for UserSkills
Scenario Outline: Validate POST all UserSkills when user logged in as "<UserName>" with "<Password>"
    Given User is on Endpoint: url/UserSkills
    When User sends POST request body from "<SheetName>" and <RowNumber>
    And JSON schema is valid
    Then User validates StatusCode
    And User should receive a list of users with skills in JSON body with the fields - user_skill_id,user_id,Skill_Id,months_of_exp
    And check the Database for all users
  Examples: 
    |SheetName|RowNumber|
    |InputData_UserSkills_POST|0|
    |InputData_UserSkills_POST|1|
