# Baseball-Scoresheet
Die Webanwendung Baseball-Scoresheet löst das Problem der manuellen Spielprotokol-lierung im Baseball Betrieb. 
Aktuell ist es so, dass das Scoren nur handschriftlich passiert, was mit einem hohhen Zeitaufwand verbunden ist. Eine elektronische Verarbeitung ist bis jetzt nicht möglich. Der Zeitaufwand soll durch diese Anwendung stark reduziert werden. Mit dem Projekt sollen folgende Möglichkeiten geschaffen werden:
Der Scorer kann sich bei der Anwendung anmelden, nur ausgewählten Personen sind dazu autorisiert. Danach können Voreinstellungen getroffen werden, z.B. Teams oder teilnehmende Spieler auswählen. Mit diesen wird anschließend das Score Sheet automatisch erstellt.

Im Verlauf des Spiels kann jeder Spielzug über eine GUI vom Scorer erfasst werden. Korrekturen sind jederzeit möglich. Da die Dauer eines Spiels stark variieren kann, soll das Score Sheet in der Größe frei skalierbar sein.

Am Ende der Partie wird eine statistische Auswertung erstellt, z.B. Batting-Average, On-Base-Average…

Es muss außerdem die Möglichkeit der Protesterfassung geben. Die Erfassung erfolgt auf einem separaten Formular.

Bevor das Sheet an den entsprechenden Verband weitergeleitet wird, müssen Trainer, Schiedsrichter und Scorer unterschreiben. Dies soll elektronisch erfolgen.

Nachdem alles eingetragen und unterschrieben ist, werden die Formulare als PDF exportiert und an den Verband geschickt. 

# Authentifizierung

# Baseball-Scoresheet

### How to start Keycloak locally?

To run keycloak locally, open terminal and do following steps...

#### Step 0 - requirements

For running Keycloak locally you need to have **Docker** installed.

Use this [**Docker download page link**](https://docs.docker.com/engine/install/) to download **Docker**.

You can check that docker is installed on your computer with this command:
```bash
docker --version
```

#### Step 1 - change working directory
```bash
# go into `docker/local` folder
cd .\docker\local\keycloak\dev  # Windows
# or
cd docker/local/keycloak/dev  # Linux, MacOS
```
#### Step 2 - create and start containers
```bash
docker compose up  # Windows, Linux, MacOS
```

Now you can open keycloak in your web browser:

Go at **http://localhost:8070**

Now you should see the login page. Input admin credential from `docker-compose.yml` file to log in.

**Note**: by default we have `admin` as **username** and **password**.
You **must** change credentials before go live.

#### Step 3 - create user

Once you are in admin web panel, do following steps:

1. Change realm to `BaseballScoresheet`
2. Go to the `Users` page
2. Add new user. Activate `Email verified` and fill all fields. Click `create`. 
3. Go to the `Credentials` tab
4. Set the password, set temporary off and click `save`.
5. Navigate to the `Role Mappings` tab
6. Assign the `user` role to new user

#### Step 4 - Generating Access Tokens With Keycloak’s API

Use `GetBearerToken.http` file to make requests to KeyCloak.

**Note**: you must change credentials in this file

#### Step 5 - Test protected and public endpoint with Postman

To test protected endpoint with Postman, do following steps:

1. Start `BaseballScoresheetApplication`
2. In Postman: create new GET connection for URL http://localhost:8080/protected
3. Go into `Authorization` tab
4. Select type `Bearer Token`
5. Pass your access token into the `Token` field
6. Press `Send`

You should get status **200 OK** and JSON `{"text": "hello from protected backend GET resource"}`

### (Production) Manually Configure Keycloak:

See this [**step-by-step guide**](https://www.baeldung.com/spring-boot-keycloak).

#### Step 1 - Creating a Realm

1. Navigate to the upper left corner to discover the `Create realm` button.
2. Add a new realm called `BaseballScoresheet`.
3. Ensure that the rearm is enabled
4. Switch to the new realm `BaseballScoresheet`.

#### Step 2 - Creating a Client

1. Navigate to the `Clients` page.
2. Click `Create`. We’ll call the new Client `login-app`.
3. We must change `Valid Redirect URIs` field. This field should contain the application URL(s) that will use this client for authentication:

#### Step 3 - Creating a Role and a User

1. Navigate to the `Realm Roles` page
2. Add the `user` role
3. Go to the `Users` page
4. Add new user
5. Go to the `Credentials` tab
6. Set the initial password
7. Navigate to the `Role Mappings` tab
8. Assign the `user` role to new user
9. Navigate to the `Client Scopes` page and then set `microprofile-jwt` to “default”.

#### Step 4 - Generating Access Tokens With Keycloak’s API

Use `GetBearerToken.http` file to make requests to KeyCloak.

**Note**: you must change credentials in this file







