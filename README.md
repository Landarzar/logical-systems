# alchourron - logical_systems
An library about the structure of logics

## Compile the code

`mvn compile`

## Run test classes
The test classes are configured as maven exec executions and can be run by their ID:

`mvn exec:exec@FOTest`

`mvn exec:exec@PLTest`

`mvn exec:exec@SOTest`

## Dependencies from github packages
This repository depends on a package that is published in github packages. As those are currently not publicly readable (https://github.community/t/how-to-allow-unauthorised-read-access-to-github-packages-maven-repository/115517/2) it uses the following workaround to read the package: https://github.community/t/how-to-allow-unauthorised-read-access-to-github-packages-maven-repository/115517/4.