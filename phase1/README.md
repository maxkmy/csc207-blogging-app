# Cloning the repository 
- Run `git clone https://github.com/maxkmy/csc207-blogging-app.git` in a directory where you would like to store this repository. 

# Opening the project in Intellij 
- Due to use of file paths in this project, please be sure to open the project in the `phase1` folder. 
- The `phase1` folder is found within `csc207-blogging-app`.

# Generating data to test the program
- Run the main methods within `CommentDataGenerator.java`, `PostDataGenerator.java` and `UserDataGenerator.java`.
- `UserDataGenerator.java`'s main method creates an admin (which can later be used to login to the app). The credentials of the admin are as follows: 
  - username: admin
  - password: password 

# Data persistence 
- For the changes to data during a session to be stored, be sure to quit the app (by using the quit option) instead of closing the CLI.

# Functionalities of the program 
- The program contains the same functionalities as phase 0. 
  - Users can sign up, login, view login history, quit the app and delete their accounts. 
  - In addition, admins can ban users, unban users, delete other accounts, promote users and create new admins. 
- Additionally, some new features were added in phase 1. We split these changes into 4 categories. 

## User features 
- Users can unfollow and follow other users by searching for other users' usernames. Please note the username searched has to be a perfect match. A search algorithm will be implemented as a part of phase 2. 
- Users can view their own profiles. 
- Users can view others' profiles by searching for other users' usernames. Again, the username searched has to be a perfect match. 

## Post features 
- Upon viewing their own profile, users can create a new post. Data such as post title and content will be read by a controller. 
- Users can view posts (displayed through profiles or a feed) by providing a post number. 
- Upon viewing a post, a user can delete the post (if it is their own post). 

## Feed feature 
- Users can request to view their feed which results in display of posts written by users they follow (sorted by recency). 

## Comment features 
- Upon viewing a post, a user can add a comment to any post. 
- Upon viewing a post, a user can view the comments written under the post (sorted by recency). 

# Use of code from online
- The hashing method implemented in `PasswordHash.java` is not fully implemented by ourselves.