What is this?
-------------

This is the source for the Java variant of the course in Test Driven
Development, Part 2.  You will need Maven and Java at least version 8 to build and
run the sources.

Tests are divided into three different categories:

  1. Level 0 - tests without dependencies (unit tests)
  2. Level 1 - tests that include the database only
  3. Level 2 - tests that runs against the hosted API

# Building

Run the `mvn clean compile` command from the CLI:

```shell
mvn clean compile
```

or simply use your IDE to build normally.

# Running the REST API

The REST API can be started by using the following command:

```
mvn spring-boot:run (-Dspring.active.profile=<profile>)
```

or use your IDE to start the spring-boot application `Application.java`.

The application needs to be configured with settings for the MongoDB
instance.  This is done using standard practice for spring boot using properties
[configuration][1].  In order, settings are read from:

  1. File `application.properties`
  2. File `application-default.properties`
  3. Spring profile specific properties (`application-<profile>.properties`)

where the spring profile is the active profile set when running the application. See the [documentation][2] for details.
Currently the default spring-profiles are: default, mock-integration and dev. I.e. the application is fine for level2, 
but for level3 you need to run with the real-integration profile.

So in order to specify the MongoDB settings, you would typically
create a new file `application-<profile>.properties` and just override
the values that needs to be different from the existing
`application.properties` file.

# Swagger documentation

The REST API is documented using Swagger.  Once you have
started the REST API, you will find the documentation at path
`/api/swagger-ui.html`.  So if you are running on port 8080, you can
point your browser to http://localhost:8080/api/swagger-ui.html.

# Running tests

All tests are categorized according to the test level described above.
The categories are "L0", "L1" and "L2".  You can use the test runner
to filter tests if you want to run a subset of the tests only.

## Level 0 tests

If you want to run the unit tests only, you can do so from a command
prompt:

```
mvn verify -PtestLevel0
```

* https://github.com/junit-team/junit4/wiki/categories

## Level 1 tests

When running the database integration tests, you need to specify the
credentials for the MongoDB database first. Make sure that you have 
created a spring profile application property file and that that 
spring profile is selected as active in the pom properties. 


Once you have created settings for your MongoDB instance, you can run
the level 1 tests only from the command prompt:

```
mvn verify -PtestLevel1
```

## Level 2 tests

The full system tests require a running service, so you need to start
the service first:

```
mvn spring-boot:run
```

Then you can run the tests:

```
mvn verify -PtestLevel2
```

You can run all tests by just ignoring the profile flag.

Products service
----------------

This solution depends on an HTTP resource that will return a product
list as a JSON structure.  You can setup Azure Functions to do this:

```json
{
    "$schema": "http://json.schemastore.org/proxies",
    "proxies": {
        "Products": {
            "matchCondition": {
                "route": "/products",
                "methods": [
                    "GET"
                ]
            },
            "responseOverrides": {
                "response.statusCode": "200",
                "response.body": "[{ \"id\": 1, \"name\": \"Apple\", \"cost\": { \"units\": 1376, \"decimalPlaces\": 2, \"currencyCode\": \"SEK\"}}, { \"id\": 2, \"name\": \"Banana\", \"cost\": { \"units\": 4455, \"decimalPlaces\": 2, \"currencyCode\": \"SEK\"}}]",
                "response.headers.Content-Type": services
            }
        },
        "ProductById": {
            "matchCondition": {
                "route": "/products/{id}",
                "methods": [
                    "GET"
                ]
            },
            "responseOverrides": {
                "response.statusCode": "200",
                "response.body": "{ \"id\": \"{id}\", \"name\": \"Apple\", \"cost\": { \"units\": 1376, \"decimalPlaces\": 2, \"currencyCode\": \"SEK\"}}",
                "response.headers.Content-Type": services
            }
        }
    }
}
```

See the documentation on Azure Functions for more information:

* https://docs.microsoft.com/en-us/azure/azure-functions/functions-create-serverless-api

[1]: https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html
[2]: https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-profiles.html

