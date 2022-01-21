Feature: Check the Authorization for LMS API

Scenario Outline: Check the Authorization with with different set of username & password
		Given UserSkillsMap User with  username & password from "<sheetName>" and <rowNumber>  is on Endpoint: url/UserSkillsMap"
    When UserSkillsMap User sends GET request
    Then UserSkillsMap User Checks for StatusCode StatusCode and StatusMessage from "<sheetName>" sheet and <rowNumber> row

    
    Examples:
    | sheetName    | rowNumber|
    | UserSkillsMap_Authorization| 0        |
    | UserSkillsMap_Authorization| 1        |
    | UserSkillsMap_Authorization| 2        |
    | UserSkillsMap_Authorization| 3        |
   