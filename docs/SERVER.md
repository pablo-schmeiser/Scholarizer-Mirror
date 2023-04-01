## Guide to launch the Application on a Server (networkwide)

### Changes to be made in Code:

###### Backend:

Go into "./Scholarizer/src/main/java/edu/kit/scholarizer/security"

Change BACKEND_ADDRESS.adr in WebAddresses.java to 
```code
'IPv4 of the Server':8080
```
 And FRONTEND_ADDRESS.adr in WebAddresses.java to
 ```code
 'IPv4 of the Server':3000
 ```

###### Frontend: 

Go into "./Scholarizer/reactapp/scholarizer"

Change BACKEND_URI in WebAddresses.js to 
```code
const BACKEND_URI = 'IPv4 of the Server'
```
And FRONTEND_URI in WebAddresses.js to
```code
const FRONTEND_URI = 'IPv4 of the Server'
```

### Run:

###### Backend:

see INSTALL.md

###### Frontend:

after building (see COMPILE.md) run:
```console
serve -s build
```

### Find out IPv4 Address:
#### Windows:
run
```console
ipconfig
```
There should be a value labeled IPv4 address

#### Ubuntu:
run

```console
hostname -I
```

The first value is your IPv4 address

Also, after starting the built frontend with
```console
serve -s build
```
The IPv4 address will be shown in your console.
