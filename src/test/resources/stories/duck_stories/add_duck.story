Scenario: ADD_DUCK_001 A request to add a duck

Given the duck counting API
When the current number of ducks is less than the maximum longint value
And the duck service is called with a PUT request
Then a 200 response is returned

Scenario: ADD_DUCK_002 A request to add a duck

Given the duck counting API
When the current number of ducks is less than the maximum longint value
And the duck service is called with a PUT request
Then the number of ducks is incremented
