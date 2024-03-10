## CMT
The CMT is a project for the university to show what is possible with Java Server Facelets. The focus is to show primefaces, the most important features, and the life cycle of JSF. That's why this isn't a consistent or practical website for the real world. This is mostly a showcase of this technology and shows the options for getting some features running in different ways, like navigation. 

## Installation guide.

### Prerequisites
- Java 11 or newer installed
- Maven installed

### Build the project
- Download the project from **"https://github.com/Paul-Abel/Content-Management-System/"**
- Build the project
- Run mvn clean install

### Deploy the project with wildfly (this is tested)
- Download wildfly "https://www.wildfly.org/"
- Extract the Archive: Once the download is complete, extract the ZIP or TAR.GZ file to a directory on your system.
- Set JBOSS_HOME environment variable to point to your WildFly installation directory.
- Update the PATH variable to include the bin directory inside the WildFly installation.
- Navigate to the bin directory inside your WildFly installation folder.
- Start add-user.bat/sh and add a Management User
- Start standalone.bat/sh and open it after the server starts "localhost:9990" and login with the user.
- Use "Deploy and Application", upload the war file from the project/target folder, and click on the context root to open the website

## What does this website showcase?

### General structure
Frontend to Backend

1. Frontend: 4 .xthml sites with 3 different templates for the navigation menu
2. Beans: 5 Beans for the .xhtml sites. Provides the functions for the xhtml sites
3. Utility class: 1 Utility class
4. Bridges: 3 java classes, which provides the function for the database access
5. Entities: 3 classes define the tables for the Jakarta.persistence database.

Furthermore there are some resource classes that contain CSS and XML files for the jsf configuration.

### Main features from the homepage
- Selection of a new navigation menu after saving by reloading the website (), by using the bean variable to select the template.
  - using session scope in bean instead of view scope to reduce initializing of the bean with their components.
  - This is possible with the templating feature, which allows the developer to dynamically build the websites / their components if they want to (ui:insert / ui:define).
- Updating / rendering partially the partial submit showcase using an ajax update command.
  - Value gets saved in the database.
- Partial submit showcase shows how the partial submit can work to reduce network traffic.
  - Shows messges (growl).
  - Uses the faces.context in the bean, which contains important information about the current context / view.
 
### Main features from the magazine list
- Allows the user the user to see the data by using the data table.
  - Implementing a paginator to navigate better over a lot of data.
  - Add a filter to find data.
  - Allow to sort the data.
- Update the delete button text, based on how many objects are selected.
- Allow to delete one or multiple Magazines at one.
- Allow to create a new Magazine.
  - navigate to the magazine.xtml not using a URL (template), but instead the faces-config.xml configuration.
- Using the confirm dialog or pop-up and the growl to inform the user.

 ### Main features from the magazine
- Create or update a magazine
  - If magazine gets updated, the id from the magazine gets carried over to the new page
- Shows every publisher that is created to link them together with a foreign key.
- Navigate by returning a URL when saving

### Main features from the publisher
-  Allows the user the user to see the data by using the data table.
  - Implementing a paginator to navigate better over a lot of data.
  - Add a filter to find data.
  - Allow to sort the data.
- Update the delete button text, based on how many objects are selected.
- Allow to delete one or multiple Magazines at once.
- Update and create opens a new pop-up (output panel) instead of navigating to a new page
- Some fields are mandatory or shown as optional, depending on the input.
- Also provide some restrictions in the input (input number with regex).
- Validating name by checking if a name is already in the database
