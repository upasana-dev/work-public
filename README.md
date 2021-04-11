# Transformer Application

The application uses Maven Wrapper and can be built and run using the "mvnw" command:-

* To build : mvnw clean install
  * The unit tests are automatically run as part of this command
*  To run the application : mvnw spring-boot:run

## API Endpoints
### Ping (GET)
Can be used to check connectivity to the application

Endpoint : __localhost:8080/__  

### List Existing Transformers (GET)
Lists all saved Transformers along with their details

Endpoint : __localhost:8080/list__ 

Example output : 
`[{"name":"Optimus Prime","strength":8,"intelligence":5,"speed":6,"endurance":3,"rank":1,"courage":1,"firepower":9,"skill":4,"overallRating":20,"transformerType":"A"},{"name":"Bumblebee","strength":7,"intelligence":5,"speed":5,"endurance":6,"rank":9,"courage":8,"firepower":7,"skill":10,"overallRating":30,"transformerType":"A"},{"name":"Ironhide","strength":6,"intelligence":4,"speed":8,"endurance":6,"rank":7,"courage":7,"firepower":8,"skill":7,"overallRating":32,"transformerType":"A"},{"name":"Starscream","strength":9,"intelligence":3,"speed":8,"endurance":6,"rank":6,"courage":7,"firepower":5,"skill":7,"overallRating":31,"transformerType":"D"},{"name":"Predaking","strength":8,"intelligence":5,"speed":6,"endurance":3,"rank":2,"courage":1,"firepower":9,"skill":2,"overallRating":31,"transformerType":"D"},{"name":"Megatron","strength":6,"intelligence":8,"speed":8,"endurance":5,"rank":4,"courage":9,"firepower":7,"skill":9,"overallRating":34,"transformerType":"D"}]`

### Create Transformer (POST)
Creates and saves a new Transformer based on the data provided. This endpoint returns the saved Transformer

Endpoint : __localhost:8080/create__

Example Request : `Curl -H "Content-Type: application/json" -s -XPOST localhost:8080/create -d "{ \"name\":\"Optimus Nemesis\",\"strength\":8,\"intelligence\":5,\"speed\":6,\"endurance\":3,\"rank\":3,\"courage\":1,\"firepower\":9,\"transformerType\":\"D\", \"skill\":7}"`

Example Output : 
`{"name":"Optimus Nemesis","strength":8,"intelligence":5,"speed":6,"endurance":3,"rank":3,"courage":1,"firepower":9,"skill":7,"overallRating":31,"transformerType":"D"}`

###  Update Transformer (POST)
Updates an existing Transformer based on the data provided. This endpoint returns the saved Transformer

Endpoint : __localhost:8080/update__

Example Request : `Curl -H "Content-Type: application/json" -s -XPOST localhost:8080/update -d "{ \"name\":\"Optimus Prime\",\"strength\":8,\"intelligence\":9,\"speed\":6,\"endurance\":5,\"rank\":1,\"courage\":1,\"firepower\":9,\"transformerType\":\"D\", \"skill\":7}"`

Example Output : 
`{"name":"Optimus Prime","strength":8,"intelligence":9,"speed":6,"endurance":5,"rank":1,"courage":1,"firepower":9,"skill":7,"overallRating":37,"transformerType":"D"}`

### Compute War Outcome
Accepts the names of Transformers and calculates the outcome of a war between the specified transformers

Endpoint : __localhost:8080/wage-war__

Sample Request : `Curl -H "Content-Type: application/json" -s -XPOST localhost:8080/wage-war -d "[\"Optimus Prime\", \"Ironhide\", \"Starscream\", \"Bumblebee\",  \"Optimus Nemesis\"]"`

Sample Response : `{"winningTeam":"Autobots","numberOfBattles":2,"teams":[{"name":"Autobots","survivors":["Bumblebee","Ironhide"]},{"name":"Decepticons","survivors":["Optimus Prime"]}]}`





