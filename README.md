# beer-finder
Program to find (optimal?) path around the breweries

 
### How to build, install and run ###
1) build docker image from the dockerfile
2) Create the container, run it. To pass the arguments simply pass X Y where X is latitude and Y is longitude and both X and Y are any numbers. 

### Configurable parameters ###
max-depth - how deep to allow algorithm to run in terms of nodes - currently, the maximum depth that has been confirmed to work in a reasonable time is max-depth=5
