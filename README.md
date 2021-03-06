# movie-service

My playground spring boot app. I use data from IMDb's datasets (https://www.imdb.com/interfaces/). Currently only supporting the main movie title table.

## Build & run

### Preliminary
When using docker toolbox make sure the docker-machine has enough memory (default of 2GB is not enough):

```
docker-machine create -d virtualbox --virtualbox-cpu-count=2 --virtualbox-memory=4096 \
  --virtualbox-disk-size=50000 --engine-opt dns=8.8.8.8 default
```
### Build and run services
Build the app and docker image:
```
mvn package dockerfile:build
```
Start containers: see movie-docker project

Some choices:
- spring boot - try to go with spring boot's default choices and most simple setup as much as possible without too much performance trade-offs
- spring data specifications for data filtering   
- postgresql - earlier h2 database, but has performance issue when ordering large data sets
- lombok - why type those getters, setters, builder patterns, etc..
- hexagonal/union architecture: domain in middle, all ports depend on application or domain (rest api, importer), no dependencies between ports. However, keep JPA annotations on domain classes. Converting messages from domain to port or back with spring's ConversionService mechanism
- clear separation of concerns; global converters - annotation on fields shouldn't tell how to (de)serialize to json/database

(possible) TODOs:
- performance of SortingAndPagingRepository using the Page return type sucks due to additional count query. Possible fixes:
  - find out whether JPA's L2 cache mechanism can help?
  - endless search so no grand total needs to be fetched
  - asynchronous count query by splitting result set retrieval from count query
  - elastic search / solr like solution
- import more tables from IMDB
- investigate / experiment with spring web reactive framework
- further experiments with docker (swarm), kubernetes?
  
