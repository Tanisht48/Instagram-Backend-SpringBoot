package com.geekster.Instagram.backend.Controller;

import com.geekster.Instagram.backend.Model.Dto.SignInInput;
import com.geekster.Instagram.backend.Model.Dto.SignUpOutput;
import com.geekster.Instagram.backend.Model.Post;
import com.geekster.Instagram.backend.Model.PostType;
import com.geekster.Instagram.backend.Model.User;
import com.geekster.Instagram.backend.Service.AuthenticationService;
import com.geekster.Instagram.backend.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("SignUp")
    public SignUpOutput userSignUp(@RequestBody @Valid User user)
    {
        return userService.userSignUp(user);
    }

    @PostMapping("SignIn")
    public String userLogIn(@RequestBody @Valid SignInInput signInInput)
    {
        return userService.userLogIn(signInInput);
    }

    @PostMapping("new/InstaPost")
    public String createInstaPost(@RequestBody @Valid Post post,@RequestParam String userEmail,@RequestParam String userToken)
    {
        boolean checkSignInUser = authenticationService.checkSignInUser(userEmail,userToken);
        if(checkSignInUser)
        {
            return userService.createInstaPost(post,userEmail);
        }
        else{
            return "You are not a Authenticated User";
        }
    }

    @GetMapping("post/type")
    public List<Post> getPostOfSimilarType(@RequestParam String userEmail, @RequestParam String userToken, @RequestParam PostType postType)
    {
        boolean checkSignInUser = authenticationService.checkSignInUser(userEmail,userToken);
        if(checkSignInUser)
        {
            return userService.getPostOfSimilarType(userEmail,postType);
        }
       throw new IllegalStateException("Not a Authenticated user");
    }



}
