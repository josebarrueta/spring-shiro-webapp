Sample-WebApp project 

This is a sample project to integrate a web application with [Stormpath](http://www.stormpath.com) and [Apache Shiro](http://shiro.apache.org/)

This project is open-source via the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0).

### Build Instructions ###

This project requires Maven 3.0.4 to build.  Run the following:

`> mvn install`

### Requirements ###

- Open an [Stormpath account](http://www.stormpath.com) .
- Get your API keys and place the file in a secure location. See [http://www.stormpath.com/docs/get-api-key](how to get your API key).
- Register an application. See [http://www.stormpath.com/docs/quickstart/register-app](how to register an application)
- Create groups (roles) in the new directory to assign them to new accounts. Used roles in this project are: admin and user.
- Create accounts for the created applications and assign them roles (i.e. admin or user).

More on setup and running? See project [wiki](https://github.com/josebarrueta/spring-shiro-webapp/wiki)


