<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/pablo-schmeiser/Scholarizer-Mirror">
    <img src="https://github.com/pablo-schmeiser/Scholarizer-Mirror/blob/main/reactapp/scholarizer/src/components/images/ScholarizerLogo.png" alt="Logo" width="280" height="144">
  </a>
<h3 align="center">Scholarizer</h3>

  <p align="center">
    A webplatform for scholars to find other scholars in their field of expertice. The key difference to similar platforms, is Schloarizers differentiation between normal citations and self-citations aswell as the option to compare multiple scholars regarding multiple different metrics, as well as the feature to compare multiple scholars with each other.
    <br />
    <a href="https://github.com/pablo-schmeiser/Scholarizer-Mirror"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/pablo-schmeiser/Scholarizer-Mirror/issues">Report Bug</a>
    ·
    <a href="https://github.com/pablo-schmeiser/Scholarizer-Mirror/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#usage">Usage</a></li>
      </ul>
    </li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
        <li><a href="#compilation">Compilation</a></li>
        <li><a href="#running">Running</a></li>
      </ul>
    </li>
    <li><a href="#built-with">Built With</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
# About The Project

<!-- [![Product Name Screen Shot][product-screenshot]](https://example.com) -->

A webplatform for scholars to find other scholars in their field of expertice. The key difference to similar platforms, is Schloarizers differentiation between normal citations and self-citations aswell as the option to compare multiple scholars regarding multiple different metrics, as well as the feature to compare multiple scholars with each other.


<!-- USAGE EXAMPLES -->
## Usage



<!-- ROADMAP -->
# Roadmap

- [x] Planing
- [x] Design
- [x] Implementation
- [x] Quality Assurance
- [ ] Presentation


<!-- CONTRIBUTING -->
# Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


<!-- GETTING STARTED -->
# Getting Started

Here we can give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

## Prerequisites
Windows 10 or higher or a Linux System.

### Java:

#### Ubuntu:
**Install java 19:**
```sh
sudo apt install openjdk-19-jre-headless
```
**Verify:**
```sh
java -version
```

#### Windows:
**Install Java 19:**

download the Java SE Development Kit 19.0.2:
https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html
- install the Java SE Development Kit 19.0.2 by following the setup wizard
- setup Java SE Development Kit 19.0.2:
  - add the "bin" folder inside of your Java installation to your "Path" (Default should be: "C:\Program Files\Java\jdk-19\bin")
    - open your start menu and type: "edit the system environment variables" or (if your OS is in german): "Systemumgebungsvariablen bearbeiten"
    - On the bottom right, click environment variables (ger: Umgebungsvariablen)
    - In the top field (User variables for User, select "Path")
    - cick on "edit"
    - in the newly opened window, click on "new"
    - paste the path to your PostgreSQL installations "bin" folder (Default: C:\Program Files\PostgreSQL\15\bin)
    - hit OK and exit the window

  - create a "JAVA_HOME" environment variable in the window under the one where you set the "Path", which holds the path to your Java installation (Default should be "C:\Program Files\Java\jdk-19")

### Maven:

#### Ubuntu:

```sh
sudo apt install maven
```

#### Windows:

Install maven (from the apache website):
https://maven.apache.org/download.cgi

### Node.js:

#### Ubuntu:
```sh
curl -sL https://deb.nodesource.com/setup_18.x | sudo bash -

sudo apt install nodejs
```

#### Windows:
download Node.js (18.x):
https://nodejs.org/en/download/
- install Node.js by following the setup wizard
- setup Node.js:
  - if not added already, add the "npm" folder found in "C:\Users\User\AppData\Roaming" to your "Path" (same process as before)

### Postgres:

#### Ubuntu:
**Install postgres:**
```sh
sudo apt install postgresql postgresql-contrib

sudo systemctl start postgresql.service
```
**Setup:**
```sh
sudo -u postgres psql
```

**In the postgresql console:**
```sh
create database scholarizer;

\password		        //set password to "postgres"
```

#### Windows:
downlad PostgreSQL (15.x):
https://www.enterprisedb.com/downloads/postgres-postgresql-downloads

- Follow the installation wizard until you are prompted to select a password for the default user (postgres)
- set the password for the user postgres to "postgres"
- once you are prompted, set the port for PostgreSQL to "5432" (should be the default)
- continue following the installation wizard
- once the installation is finished, you will need to add PostgreSQL to your
PATH environment variable
- open your terminal
- type "psql -U postgres" to login to the postgres user account, you should be prompted to enter your password
- type "postgres", you should now be logged in
- type "CREATE DATABASE scholarizer;"
- to test if the creation was successful, type "\c scholarizer" to connect to the database
- you can now close your terminal


## Installation
1. Clone the repo
   ```sh
   git clone https://github.com/pablo-schmeiser/Scholarizer-Mirror.git
   ```
2. Install NPM packages
   ```sh
   cd ./Scholarizer/reactapp/scholarizer
   npm install
   ```
3. Install Maven packages
   ```sh
   cd ./Scholarizer
   mvn clean install -DskipTests=True
   ```

## Compilation:

Do
   ```sh
   cd ./Scholarizer
   mvn clean install -DskipTests=True
   ```
to build the backend.
<br/>
You can find the compiled .jar file in "./Scholarizer/target".

<br/>

Do
   ```sh
   cd ./Scholarizer/reactapp/scholarizer
   npm run build
   ```
to build the frontend.

## Running:
1. Start serving frontend on your local network:
   ```sh
   npm install -g serve        //on Linux you might need to prefix with sudo
   ```
   
2. Get an API Key from [Semantic Scholar](https://www.semanticscholar.org/)
3. Start the backend on your local network:
   ```console
   java -DEMAIL='your_mail' -DPASSWORD='smtp_password' -DSS_API_KEY='your_api_key' -jar ./scholarizer-0.0.1-SNAPSHOT.jar
   ```
   Replace "your_api_key" with your API Key for Semantic Scholar.

if the search does not work (error code 500 in the network analytics tab), you need to set an environment variable for the api key. 
You can set an environment variable for the api key via the following:

Linux:
  ```sh
  export SS_API_KEY='your_api_key'"
  ```
  
Windows:
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


# Built With

[![React][React.js]][React-url]
[![Java][Java.com]][Java-url]
[![Material-UI][MUI.com]][MUI-url]
[![Spring][Spring.com]][Spring-url]
[![TypeScript][TS.com]][TS-url]
[![SonarLint][Sonar.com]][Sonar-url]
[![IntelliJ][IntelliJ.com]][IntelliJ-url]
[![WebStorm][WebStorm.com]][WebStorm-url]


<!-- LICENSE -->
# License

Distributed under the Apache License 2.0. See `LICENSE.txt` for more information.


<!-- CONTACT -->
# Contact
Project Link: [https://github.com/pablo-schmeiser/Scholarizer-Mirror](https://github.com/pablo-schmeiser/Scholarizer)


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/pablo-schmeiser/Scholarizer.svg?style=for-the-badge
[contributors-url]: https://github.com/pablo-schmeiser/Scholarizer-Mirror/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/pablo-schmeiser/Scholarizer.svg?style=for-the-badge
[forks-url]: https://github.com/pablo-schmeiser/Scholarizer-Mirror/network/members
[stars-shield]: https://img.shields.io/github/stars/pablo-schmeiser/Scholarizer.svg?style=for-the-badge
[stars-url]: https://github.com/pablo-schmeiser/Scholarizer-Mirror/stargazers
[issues-shield]: https://img.shields.io/github/issues/pablo-schmeiser/Scholarizer.svg?style=for-the-badge
[issues-url]: https://github.com/pablo-schmeiser/Scholarizer-Mirror/issues
[license-shield]: https://img.shields.io/github/license/pablo-schmeiser/Scholarizer.svg?style=for-the-badge
[license-url]: https://github.com/pablo-schmeiser/Scholarizer-Mirror/blob/master/LICENSE.txt
[product-screenshot]: images/screenshot.png

[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
[TS.com]: https://img.shields.io/badge/TypeScript-007ACC?style=for-the-badge&logo=typescript&logoColor=white
[TS-url]: https://www.typescriptlang.org/
[MUI.com]: https://img.shields.io/badge/Material--UI-0081CB?style=for-the-badge&logo=material-ui&logoColor=white
[MUI-url]: https://mui.com
[Java.com]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white
[Java-url]: https://java.com
[Spring.com]: https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[Spring-url]: https://spring.io/
[Sonar.com]: https://img.shields.io/badge/SonarLint-CB2029?style=for-the-badge&logo=sonarlint&logoColor=white
[Sonar-url]: https://www.sonarsource.com/products/sonarlint/
[IntelliJ.com]: https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white
[IntelliJ-url]: https://www.jetbrains.com/idea/
[WebStorm.com]: https://img.shields.io/badge/WebStorm-000000?style=for-the-badge&logo=WebStorm&logoColor=white
[WebStorm-url]: https://www.jetbrains.com/webstorm/
