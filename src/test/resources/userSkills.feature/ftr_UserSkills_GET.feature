Feature: To check The GET method for UserSkills
Scenario: Validate GET all UserSkills when user logged in as "<UserName>" with "<Password>"
    Given User is on GET Method Endpoint: url/UserSkills
    When User sends request
    And JSON schema is valid
    Then User validates StatusCode
    And User should receive a list of users with skills in JSON body with the fields - user_skill_id,user_id,Skill_Id,months_of_exp
    And check the Database for all users
    
 Scenario Outline: Validate USERSkills Api with specific ID  when User logged in with valid UserName & Password
    Given User is on GET Method Endpoint: url/UserSkills
    When User sends request id from "<SheetName>" and <RowNumber>
    And JSON schema is valid
    Then User validates StatusCode and StatusMessage from "<SheetName>" and <RowNumber>
    And User should receive a list of users with skills in JSON body with the fields - user_skill_id,user_id,Skill_Id,months_of_exp
    And check the Database
    Examples: 
    |SheetName|RowNumber|
    |InputData_UserSkills_GET|0|
   	|InputData_UserSkills_GET|1|
   	|InputData_UserSkills_GET|2|
   	|InputData_UserSkills_GET|3|
   	|InputData_UserSkills_GET|4|
   	|InputData_UserSkills_GET|5|
   	|InputData_UserSkills_GET|6|