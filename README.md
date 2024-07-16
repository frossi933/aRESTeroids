# aRESTeroids

Application to explore asteriods data.
The server runs on port 8080

### REST API


#### Get asteroids by dates and sorted by name

List all the asteroids whose "close approach date" is between DATE1 and DATE2. The list can be sorted by name (asc or desc)

`GET /asteroids?startDate=DATE1&endDate=DATE2&sortByName=SORTING`

startDate and endDate are optionals, but if one of them is present, the other one must be present. If none of them is defined, the service defaults the dates to today.
sortByName param is also optional. If it's not included (or its value is different from asc and desc) the order of the list is not modified

#### Get more details about a specific asteroid

The service returns an object containing all the data related to the asteroid identified by ID

`GET /asteroids/{ID}`
