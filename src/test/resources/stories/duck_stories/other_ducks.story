Scenario: OTHER_DUCKS_001 An invalid duck request

Given the duck counting API
When the duck service is called with a request that is not GET, POST or DELETE
Then a 500 response is returned
