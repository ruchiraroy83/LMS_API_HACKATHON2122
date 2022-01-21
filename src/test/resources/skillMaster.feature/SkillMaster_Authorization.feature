Feature: Check the Authorization for LMS API

Scenario Outline: Check the Authorization with with different set of username & password
		Given Skills User with  username & password from "<sheetName>" and <rowNumber> is on Endpoint: url/Skills
    When skills User sends GET request
    Then Skills User Checks for StatusCode StatusCode and StatusMessage from "<sheetName>" sheet and <rowNumber> row

    
    Examples:
    | sheetName    | rowNumber|
    | Skills_Authorization| 0        |
    | Skills_Authorization| 1        |
    | Skills_Authorization| 2        |
    | Skills_Authorization| 3        |