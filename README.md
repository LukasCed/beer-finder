# beer-finder
Program to find (optimal?) path around the breweries

 
### How to build, install and run ###
With Docker:
1) "mvn clean install" to build the jar
2) "docker build ." to build the docker image from dockerfile 
3) Create the container, run it. To pass the arguments simply pass X Y where X is latitude and Y is longitude and both X and Y are any numbers. 

Or, just run the app from your favourite IDE, by passing the arguments first. Or you can build the JAR and run it (should require no external dependencies).

### Configurable parameters ###
max-depth - how deep to allow algorithm to run in terms of nodes - currently, the maximum depth that has been confirmed to work in a reasonable time is max-depth=5
