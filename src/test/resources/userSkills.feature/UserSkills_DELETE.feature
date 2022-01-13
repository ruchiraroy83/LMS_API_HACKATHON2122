Feature: Get All the Users with their skill details
Scenario Outline: User logged in as "<UserName>" with "<Password>"
    Given User is on Endpoint: url/UserSkills
    When User sends request id ON DELETE Method from "<sheetName>" and <rowNumber>
    Then User validates StatusCode and StatusMessage from "<sheetName>" and <rowNumber>
    And check the Database to validate deletion
    
    Examples:
    | sheetName                  | rowNumber|
    | InputData_UserSkills_DELETE| 0        |
    | InputData_UserSkills_DELETE| 1        |
    | InputData_UserSkills_DELETE| 2        |
    | InputData_UserSkills_DELETE| 3        |
    | InputData_UserSkills_DELETE| 4        |
  
   