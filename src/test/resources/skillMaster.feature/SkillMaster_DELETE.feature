Feature: To check The DELETE method for Skills
Background:
	Given User is on Endpoint: url/Skills with valid username and password

Scenario Outline: Validate DELETE Skills when user logged with UserName with Password   
    When User sends DELETE skill id ON DELETE Method from "<SheetName>" and <RowNumber>
    Then User validates the StatusCode and StatusMessage from "<SheetName>" sheet and <RowNumber> row
    And check the Database at "<SheetName>" and <RowNumber> to validate the deletion
 
    Examples: 
    |SheetName     |RowNumber|Method|
    |Skills_DELETE |0        |DELETE |
    |Skills_DELETE |1        |DELETE |
    |Skills_DELETE |2        |DELETE |
    |Skills_DELETE |3        |DELETE |
    |Skills_DELETE |4        |DELETE |
    