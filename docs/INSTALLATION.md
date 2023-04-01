## Installation guide:
<h3><u>
Ubuntu
</u></h3>

**Install java 19:**
```console
sudo apt install openjdk-19-jre-headless
```
**Verify:**
```console
java -version
```
***

**Install Node.js:**
```console
curl -sL https://deb.nodesource.com/setup_18.x | sudo bash -

sudo apt install nodejs
```

**Install postgres:**
```console
sudo apt install postgresql postgresql-contrib

sudo systemctl start postgresql.service
```
**Setup:**
```console
sudo -u postgres psql
```

**In the postgresql console:**
```console
create database scholarizer;

\password		        //set password to "postgres"
```
___
<h3><u>
Windows
</u></h3>

**Install PostgreSQL:**

downlad PostgreSQL (15.x):
https://www.enterprisedb.com/downloads/postgres-postgresql-downloads

- Follow the installation wizard until you are prompted to select a password for the default user (postgres)
- set the password for the user postgres to "postgres"
- once you are prompted, set the port for PostgreSQL to "5432" (should be the default)
- continue following the installation wizard
- once the installation is finished, you will need to add PostgreSQL to your
PATH environment variable
  - open your start menu and type: "edit the system environment variables" or (if your OS is in german): "Systemumgebungsvariablen bearbeiten"
  - On the bottom right, click environment variables (ger: Umgebungsvariablen)
  - In the top field (User variables for User, select "Path")
  - cick on "edit"
  - in the newly opened window, click on "new"
  - paste the path to your PostgreSQL installations "bin" folder (Default: C:\Program Files\PostgreSQL\15\bin)
  - hit OK and exit the window
- open your terminal
- type "psql -U postgres" to login to the postgres user account, you should be prompted to enter your password
- type "postgres", you should now be logged in
- type "CREATE DATABASE scholarizer;"
- to test if the creation was successful, type "\c scholarizer" to connect to the database
- you can now close your terminal

***
**Install Java 19:**

download the Java SE Development Kit 19.0.2:
https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html
- install the Java SE Development Kit 19.0.2 by following the setup wizard
- setup Java SE Development Kit 19.0.2:
  - add the "bin" folder inside of your Java installation to your "Path" (same process as above, Default should be: "C:\Program Files\Java\jdk-19\bin")
  - create a "JAVA_HOME" environment variable in the window under the one where you set the "Path", which holds the path to your Java installation (Default should be "C:\Program Files\Java\jdk-19")
***
**Install Node.js:**

download Node.js (18.x):
https://nodejs.org/en/download/
- install Node.js by following the setup wizard
- setup Node.js:
  - if not added already, add the "npm" folder found in "C:\Users\User \AppData\Roaming" to your "Path" (same process as before)

___

## Run the application:

**Frontend**
go to ./Scholarizer/reactapp/scholarizer and run:
```console 
npm install
```
After a short wait, run
```console
npm start
```
after a little while the website will launch.
***
**Backend**

go to ./Scholarizer/target and run 
```console
java -DEMAIL='your_mail' -DPASSWORD='smtp_password' -DSS_API_KEY='your_api_key' -jar ./scholarizer-0.0.1-SNAPSHOT.jar
```


if the search does not work (error code 500 in the network analytics tab), you need to set an environment variable for the api key. 

you can set an environment variable for the api key via 
###### Linux
```console
export SS_API_KEY='your_api_key'
```

###### Windows
- open your start menu and type: "edit the system environment variables"
or (if your OS is in german): "Systemumgebungsvariablen bearbeiten"
- On the bottom right, click environment variables (ger: Umgebungsvariablen)
- In the top field (User variables for User, select "Path")
- click on "edit"
- in the newly opened window, click on "new"
- paste the path to your PostgreSQL installations "bin" folder (Default: C:\Program Files\PostgreSQL\15\bin)
- hit OK and exit the window 

the adjusted run command will then be 
```console
java -DEMAIL='your_mail' -DPASSWORD='smtp_password' -jar ./scholarizer-0.0.1-SNAPSHOT.jar
```

(Same if sending the verification mail does not work i.e. you dont recieve an email. Note, depending on your email provider, recieving may take a couple minutes, please wait for a little bit before trying this fix)

(repeat for *EMAIL* and *PASSWORD* if the described scenario rearding the verification mail occurs)

So, if you set all values, i.e. *EMAIL*, *PASSWORD* and *SS_API_KEY* as global environment variables, the command to run the comiled backend will be:
```console
java -jar ./scholarizer-0.0.1-SNAPSHOT.jar
```
