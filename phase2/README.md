# Cloning the repository 
- Run `git clone https://github.com/maxkmy/csc207-blogging-app.git` in a directory where you would like to store this repository. 

# Opening the project in Intellij 
- Due to use of file paths in this project, please be sure to open the project in the `phase2` folder. 
- The `phase2` folder is found within `csc207-blogging-app`.
- Select Amazon Corretto 11 as your SDK. 

# Downloading libraries 
- Navigate to `File -> Project Structure -> Libraries`
- Select `+ -> From Maven`
- Search for `undertow-core` and add the `io.undertow:undertow-core:2.2.18.Final` library. 
- Search for `jinjava` and add the `com.hubspot.jinjava:jinjava:2.6.0` library. 

# Generating data to test the program
- Run the main methods within `CommentDataGenerator.java`, `PostDataGenerator.java` and `UserDataGenerator.java`. Note that these classes are found within the `dataGenerator` folder.
- `UserDataGenerator.java`'s main method creates an admin (which can later be used to login to the app). The credentials of the admin are as follows: 
  - username: admin
  - password: password 
  
# Running the app 
- Run the main method in `app.java`. 
- Open your browser and navigate to `http://localhost:8080`. 

# Data persistence 
- For the changes to data during a session to be stored, be sure to logout after signing up or logging into an account. 
- The exception to this is if you self-delete an account. 

# Functionalities of the program 
- The program contains the same functionalities as phase 0. 
  - Users can sign up, login, view login history, quit the app and delete their accounts. 
  - In addition, admins can ban users, unban users, delete other accounts, and promote users. 
- Additionally, some new features were added in phase 1. We split these changes into 4 categories (further discussed below). 
- In phase 2, we made the app into a webapp that can be ran on localhost and added a search functionality.
- Note: a manual test plan has been added to guide with some basic uses of the app. 

## User features 
- Users can unfollow and follow other users by searching for other users' usernames. A list of top 10 closest match will be displayed. 
- Users can view their own profiles. 
- Users can view others' profiles by searching for other users' usernames. 
- Users can view their followers and followees. 

## Post features 
- Upon viewing their own profile, users can create a new post. 
- Users can view posts (displayed through profiles or a feed). 
- Upon viewing a post, a user can also view comments under the post. 

## Feed feature 
- Users can request to view their feed which results in display of posts written by users they follow (sorted by recency). 

## Comment features 
- Upon viewing a post, a user can add a comment to any post. 
- Upon viewing a post, a user can view the comments written under the post (sorted by recency). 

# Use of code from online
- The hashing method implemented in `PasswordHash.java` is not fully implemented by ourselves.
