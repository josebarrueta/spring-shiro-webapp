Sample-WebApp project 

This is a sample project to integrate a web application .

This project is open-source via the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0).

### Build Instructions ###

This project requires Maven 3.0.3 to build.  Run the following:

`> mvn install`


### Setup instructions ###

- Open an stormpath account.
- Get your API keys and place the file in a secure location. See http://www.stormpath.com/docs/get-api-key
- Register an application. See http://www.stormpath.com/docs/quickstart/register-app
- Create accounts for the created applications and assign them roles (e.g. admin or user).
- Create a properties file that will have at least the following two properties:

stormpath.apikey.location=<location of the apiKeys properties file>
stormpath.application.resturl=<REST URL of the stormpath application>

In the application-context.xml file (under src/amin/webapp/WEB-INF/spring) replace the
${stormpathPropertiesFileLocation} in PropertyPlaceholderConfigurer to point to the location of the
properties file in your local environment.

TODO: Logout
TODO: Add support to create users.
TODO: Enable shiro annotations.

TODO: Create unit tests.

TODO: Create templates using tiles.
TODO: Document classes.


