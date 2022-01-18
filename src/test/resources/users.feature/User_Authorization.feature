Feature: Check the Authorization for LMS API

Scenario Outline: Check the Authorization with with different set of username & password
		Given User is on Endpoint: url/Users username & password from "<sheetName>" and <rowNumber>
    When userAPI User sends GET request
    Then userAPI User validates StatusCode and StatusMessage from "<sheetName>" sheet and <rowNumber> row

    
    Examples:
    | sheetName    | rowNumber|
    | Authorization| 0        |
    | Authorization| 1        |
    | Authorization| 2        |
    | Authorization| 3        |
   