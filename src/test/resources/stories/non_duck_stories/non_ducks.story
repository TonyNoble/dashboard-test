Scenario: NON_DUCKS_001 An invalid duck request

Given the duck counting API
When a page is requested that is not the duck REST endpoint
Then a 404 response is returned
