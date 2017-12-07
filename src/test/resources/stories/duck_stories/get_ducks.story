Scenario: GET_DUCK_001 A request to get the number of ducks

Given the duck counting API
When the duck service is called with a GET request
Then a 200 response is returned

Scenario: GET_DUCK_002 A request to add a duck

Given the duck counting API
When the duck service is called with a GET request
Then the number of ducks is returned
