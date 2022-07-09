# Cloning the repository
- Run `git clone https://github.com/maxkmy/CSC207-app.git` in a directory where you would like to store this repository.

# Opening the project in Intellij
- Due to the use of file paths in this project (to read from JSON files), it is important that the project is opened in the right directory. Otherwise, some portions of code will fail to run.
- Please open the `phase0` directory which is found within the `group_0245` directory.

# Downloading dependencies 
- there are 2 main dependencies for this project 
  - simple.json 
  - gson 

## Downloading simple.json
- In Intellij navigate to `file -> project structure -> modules`
- Click the `+` symbol
- Select `jars or directories`
- Navigate to the `dependencies` folder
- Select json-simple-1.1.1.jar 
- Click all highlighted buttons (from popups screens)
- Select `Apply` and hit `ok`

## Downloading gson 
- In Intellij navigate to `file -> project structure -> modules`
- Click the `+` symbol
- Select `libraries`
- Select `New Library -> From Maven`
- Search `google.code.gson` and download version 2.6.2
- Click all highlighted buttons (from popups screens)
- Select `Apply` and hit `ok`

# Notes about testing the application
- The "main app" is found in the file `App.java`. Run that file and the app should be running in the console in Intellij.
- By default, there is already an admin user stored in in `data/userData.json` (it is the only account in that file when you first run `App.java`). This is mainly because admins can only be created from other admins. Thus, we need some "starting admin" account.
- The login information for the "starting admin" is as follows:
    - username: admin
    - password: password
- Please make sure that you do not run the `Delete` command on the "starting admin" without having created other admins. If you do so, there will be no way to test any more admin features since the one and only admin account has been deleted. And, admins can only be created from an admin. 
- However, in the case all admins accounts are accidentally deleted, you may copy the following JSON object into `data/userData.json`

`{"admin":{"hashedPassword":"b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86","isBanned":false,"history":[],"isAdmin":true,"username":"admin"}}`
- this is the JSON representing the starting admin. That is, the login information is same as above.
- Note that inputs from users have to perfectly match strings in quotation. 
- To check whether Java objects are preserved between different login session, please make sure to quit the App instead of rerunning it without quitting.

# Use of code from online 
- the hashing method in the class `PasswordHash.java` was not fully implemented on our own.
- It was taken from online.
