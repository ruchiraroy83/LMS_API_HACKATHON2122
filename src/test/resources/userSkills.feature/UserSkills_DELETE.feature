Feature: Delete user skills details
Background:
	Given userSkills User is on Endpoint: url/UserSkills with valid username and password
	
Scenario Outline: Validate the delete method for UserSkills
    When userSkills User sends request id ON DELETE Method from "<sheetName>" and <rowNumber>
    Then userSkills User validates StatusCode and StatusMessage from "<sheetName>" sheet and <rowNumber> row
    And userSkills users check the Database to validate deletion
    
    Examples:
    | sheetName                  | rowNumber|
    | InputData_UserSkills_DELETE| 0        |
    | InputData_UserSkills_DELETE| 1        |
    | InputData_UserSkills_DELETE| 2        |
    | InputData_UserSkills_DELETE| 3        |
    | InputData_UserSkills_DELETE| 4        |
  
   