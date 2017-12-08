Scenario: DELETE_DUCK_001 A request to delete a duck

Given the duck counting API
When the current number of ducks is non-zero
And the duck service is called with a DELETE request
Then a 200 response is returned

Scenario: DELETE_DUCK_002 A request to delete a duck

Given the duck counting API
When the current number of ducks is non-zero
And the duck service is called with a DELETE request
Then the number of ducks is decremented

Scenario: DELETE_DUCK_003 A request to delete a duck

Given the duck counting API
When the current number of ducks is zero
And the duck service is called with a DELETE request
Then a 500 response is returned
