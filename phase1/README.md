# Cloning the repository
- Run `git clone https://github.com/maxkmy/CSC207-app.git` in a directory where you would like to store this repository.

# Opening the project in Intellij
- Due to the use of file paths in this project (to read from txt files), it is important that the project is opened in the right directory. Otherwise, some portions of code will fail to run.
- Please open the `phase0` folder which is found within the `group_0245` folder.

# Notes about testing the application
- The "main app" is found in the file `App.java`. Run that file and the app should be running in the console in Intellij.
- By default, there is already an admin user stored in `data/userData.txt` (it is the only account in that file when you first run `App.java`). This is mainly because admins can only be created from other admins. Thus, we need some "starting admin" account.
- The login information for the "starting admin" is as follows:
    - username: admin
    - password: password
- Please make sure that you do not run the account self deletion command on the "starting admin" without having created other admins. If you do so, there will be no way to test any more admin features since the one and only admin account has been deleted. And, admins can only be created from an admin. 
- However, in the case all admins accounts are accidentally deleted, you may run the main method in `userDataGenerator.java` which generates the starting admin account.
- Note that inputs from users are based on numbers. The numbers should be input without trailing or leading spaces unless you would like to test whether the controllers can validate inputs correctly. 
- To check whether Java objects are preserved between different login session, please make sure to quit the App instead of rerunning the app without quitting.

# Use of code from online 
- the hashing method in the class `PasswordHash.java`, `SimilarityScoreLevenshtein.Java` and `SimilarityScoreJaroWrinkler.Java` was not fully implemented on our own.
- They were taken from online sources.
