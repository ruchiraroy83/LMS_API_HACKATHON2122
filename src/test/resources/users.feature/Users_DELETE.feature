Feature: Delete user skills details
Background:
	Given User is on Endpoint: url/Users with valid username and password
	
Scenario Outline: Validate the delete method for Users
    When User sends request id ON DELETE Method from "<sheetName>" and <rowNumber>
    Then User validates StatusCode and StatusMessage from "<SheetName>" sheet and <RowNumber> row
    And check the Database to validate deletion
    
    Examples:
    | sheetName                  | rowNumber|
    | InputData_UserSkills_DELETE| 0        |
    | InputData_UserSkills_DELETE| 1        |
    | InputData_UserSkills_DELETE| 2        |
    | InputData_UserSkills_DELETE| 3        |
    | InputData_UserSkills_DELETE| 4        |
  
   