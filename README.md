# Java application demo for Semantic Web project
A simple java command line application which demonstrates some of the functionality and usage of the linked data exctracted from the TU Vienna page.

## Deployment
A linked data graph has been loaded into the Triple store from which the application reads and queries the necessary data in order to provide useful information to the user.

The application uses [Apache Jena TDB](https://jena.apache.org/index.html) as a triple store into which the data graph has been loaded.
All queries and requests are made against the triple store and formatted and handled further with Java in order to create a useful output for the user.

In order to replicate the setup used for this project, it is required to have Jena jar collection downloaded and imported into the Eclipse project, or simply use Maven to pull all the necessary Jena dependecies automatically.

Eclipse was used as the IDE. In Eclipse, it is required to first create a maven project, and in the `.pom` file include the following as a dependency entry:

This is how to specify in your pom.xml file the dependency on a version of Jena:


    <dependency>
       <groupId>org.apache.jena</groupId>
       <artifactId>apache-jena-libs</artifactId>
       <type>pom</type>
       <version>X.Y.Z</version>
    </dependency>

The version number of TDB needs to be checked on the official Apache Jena website since it is not the same as apache-jena.

## Data
Jena provides support for some well-known vocabularies such as `FOAF`. However, since we used our custom made vocabularies as well, it is necessary to generate the Java class representations of each custom vocabulary.

In this project there were two such vocabularies:
* `tuvienna`
* `teach`

Aforementioned custom vocabulary Java classes 
