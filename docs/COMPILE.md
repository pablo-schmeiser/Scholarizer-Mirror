# Compilation Instructions

## Prerequisites:

### Ubuntu:

Install Maven:
```console
sudo apt install maven
```

___

### Windows:

Install maven (from the apache website):
https://maven.apache.org/download.cgi

___

## Compile:

### Backend:

Inside of ./Scholarizer:
```console
mvn clean install -DskipTests=True
```

After that, you can find the compiled .jar file in "./Scholarizer/target"

### Frontend:

In the ./Scholarizer/reactapp/scholarizer

```console
npm run build

npm install -g serve        //on Linux you might need to prefix with sudo
```
