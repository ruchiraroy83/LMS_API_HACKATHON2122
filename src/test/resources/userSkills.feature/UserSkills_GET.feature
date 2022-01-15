Feature: To check The GET method for UserSkills
Background:
	Given User is on Endpoint: url/UserSkills with valid username and password
Scenario: Validate GET all UserSkills
    When User sends GET request
    Then User validates StatusCode
    And JSON schema is valid
    And check the Database for userSkills
	
	Scenario Outline: Validate UserSkills Api with specific ID  
    When User sends GET request on  id from "<SheetName>" and <RowNumber>
    Then User validates StatusCode and StatusMessage from "<SheetName>" sheet and <RowNumber> row
    And JSON schema is valid for "<Method>"
    And User should receive a list of users with skills in JSON body with the fields like user_skill_id,user_id,Skill_Id,months_of_exp from "<SheetName>" and <RowNumber>
    And check the Database
    Examples: 
    |SheetName               |RowNumber|Method|
    |InputData_UserSkills_GET|0        |GET|
   	|InputData_UserSkills_GET|1        |GET|
   	|InputData_UserSkills_GET|2        |GET|
   	|InputData_UserSkills_GET|3        |GET|
   	|InputData_UserSkills_GET|4        |GET|
   	|InputData_UserSkills_GET|5        |GET|
    
 