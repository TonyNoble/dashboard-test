Scenario: ADD_DUCK_001 A request to add a duck

Given the duck counting API
When the duck service is called with a POST request
Then a 200 response is returned

Scenario: ADD_DUCK_002 A request to add a duck

Given the duck counting API
When the duck service is called with a POST request
Then the number of ducks is incremented
