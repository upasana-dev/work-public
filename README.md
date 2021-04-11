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

Sample Response : 
`[{"name":"Optimus Prime","strength":8,"intelligence":5,"speed":6,"endurance":3,"rank":1,"courage":1,"firepower":9,"skill":4,"overallRating":20,"transformerType":"A"},{"name":"Bumblebee","strength":7,"intelligence":5,"speed":5,"endurance":6,"rank":9,"courage":8,"firepower":7,"skill":10,"overallRating":30,"transformerType":"A"},{"name":"Ironhide","strength":6,"intelligence":4,"speed":8,"endurance":6,"rank":7,"courage":7,"firepower":8,"skill":7,"overallRating":32,"transformerType":"A"},{"name":"Starscream","strength":9,"intelligence":3,"speed":8,"endurance":6,"rank":6,"courage":7,"firepower":5,"skill":7,"overallRating":31,"transformerType":"D"},{"name":"Predaking","strength":8,"intelligence":5,"speed":6,"endurance":3,"rank":2,"courage":1,"firepower":9,"skill":2,"overallRating":31,"transformerType":"D"},{"name":"Megatron","strength":6,"intelligence":8,"speed":8,"endurance":5,"rank":4,"courage":9,"firepower":7,"skill":9,"overallRating":34,"transformerType":"D"}]`

### Create Transformer (POST)
Creates and saves a new Transformer based on the data provided. This endpoint returns the saved Transformer

Endpoint : __localhost:8080/create__

Sample Request : `Curl -H "Content-Type: application/json" -s -XPOST localhost:8080/create -d "{ \"name\":\"Optimus Nemesis\",\"strength\":8,\"intelligence\":5,\"speed\":6,\"endurance\":3,\"rank\":3,\"courage\":1,\"firepower\":9,\"transformerType\":\"D\", \"skill\":7}"`

Sample Response : 
`{"name":"Optimus Nemesis","strength":8,"intelligence":5,"speed":6,"endurance":3,"rank":3,"courage":1,"firepower":9,"skill":7,"overallRating":31,"transformerType":"D"}`

###  Update Transformer (POST)
Updates an existing Transformer based on the data provided. This endpoint returns the saved Transformer

Endpoint : __localhost:8080/update__

Sample Request : `Curl -H "Content-Type: application/json" -s -XPOST localhost:8080/update -d "{ \"name\":\"Optimus Prime\",\"strength\":8,\"intelligence\":9,\"speed\":6,\"endurance\":5,\"rank\":1,\"courage\":1,\"firepower\":9,\"transformerType\":\"D\", \"skill\":7}"`

Sample Response : 
`{"name":"Optimus Prime","strength":8,"intelligence":9,"speed":6,"endurance":5,"rank":1,"courage":1,"firepower":9,"skill":7,"overallRating":37,"transformerType":"D"}`

### Compute War Outcome
Accepts the names of Transformers and calculates the outcome of a war between the specified transformers

Endpoint : __localhost:8080/wage-war__

Sample Request : `Curl -H "Content-Type: application/json" -s -XPOST localhost:8080/wage-war -d "[\"Optimus Prime\", \"Ironhide\", \"Starscream\", \"Bumblebee\",  \"Optimus Nemesis\"]"`

Sample Response : `{"winningTeam":"Autobots","numberOfBattles":2,"teams":[{"name":"Autobots","survivors":["Bumblebee","Ironhide"]},{"name":"Decepticons","survivors":["Optimus Prime"]}]}`

## Assumptions
The following are the assumptions made when developing this API:-

* That each Transformer fights only one battle with the corresponding Transformer (in order of rank) from the opposing team
* That the sorting of the Transformers in each team is done in desceding order of rank
* That there is no team wins the war if both teams win an equal number of battles
* That if an invalid Transformer is specified for the war, the unknown Transformer excluded from the computation 
* That at any point, there can be only one Transformer with a given name
 * While an internal, computed ID is used in the database, the functional identifier of the Transformer is its name 
* That an update to a Transformer will allow overwriting of all its information except for its name and ID.

## Notes
The following could be enhancements to the application:- 

* Input validation and sanitization - Although some basic validation is performed on the data provided as part of the request, this could be improved on by sanitizing the input before passing it to the database to prevent SQL injection.
* Use of interfaces - Currently, the services in the project are directly referenced via their implementations. This could be improved on by adding interfaces on top of the implementations and referencing the interfaces instead of the implementations to decrease tight coupling.
* Use of smaller projects - Because of its simplicity, all the logic for the API is placed within the same project. However, this can be improved on by segmenting the project into smaller, more modular projects to allow separation of logic and loose coupling.  


