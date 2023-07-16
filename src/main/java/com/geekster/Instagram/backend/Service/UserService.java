package com.geekster.Instagram.backend.Service;

import com.geekster.Instagram.backend.Model.AuthenticationToken;
import com.geekster.Instagram.backend.Model.Dto.SignInInput;
import com.geekster.Instagram.backend.Model.Dto.SignUpOutput;
import com.geekster.Instagram.backend.Model.Post;
import com.geekster.Instagram.backend.Model.PostType;
import com.geekster.Instagram.backend.Model.User;
import com.geekster.Instagram.backend.Repository.IUserRepo;
import com.geekster.Instagram.backend.Service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PostService postService;


    public SignUpOutput userSignUp(User user) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();

        if(newEmail == null)
        {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //check if this user email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(newEmail);

        if(existingUser != null)
        {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());

            //saveAppointment the user with the new encrypted password

            user.setUserPassword(encryptedPassword);
            userRepo.save(user);

            return new SignUpOutput(signUpStatus, "User registered successfully!!!");
        }
        catch(Exception e)
        {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
    }

    public String userLogIn(SignInInput signInInput) {

        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if(signInEmail == null)
        {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }

        //check if this user email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(signInEmail);

        if(existingUser == null)
        {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(existingUser.getUserPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingUser);
                authenticationService.saveAuthToken(authToken);

               // EmailHandler.sendEmail("mainakgh1@gmail.com","email testing",authToken.getTokenValue());
                return "Session Created";
            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e)
        {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }
    }

    public String createInstaPost(Post post, String userEmail) {
        User user = userRepo.findFirstByUserEmail(userEmail);

       return postService.createInstaPost(post,user);

    }

    public List<Post> getPostOfSimilarType(String userEmail, PostType postType) {
        User user = userRepo.findFirstByUserEmail(userEmail);
        Integer userId = user.getUserId();
        return  postService.findByPostTypeAndUser(postType,user);
    }
}
