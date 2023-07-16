package com.geekster.Instagram.backend.Service;

import com.geekster.Instagram.backend.Model.AuthenticationToken;
import com.geekster.Instagram.backend.Repository.IAuthenticationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
   private IAuthenticationRepo authenticationRepo;
    public void saveAuthToken(AuthenticationToken authToken) {
        authenticationRepo.save(authToken);
    }

    public boolean checkSignInUser(String userEmail, String userToken) {
        AuthenticationToken authToken = authenticationRepo.findFirstByTokenValue(userToken);

        if(authToken == null)
        {
            return false;
        }

        String tokenConnectedEmail = authToken.getUser().getUserEmail();

        return tokenConnectedEmail.equals(userEmail);
    }

    public void deleteAuthToken(String userToken) {
        AuthenticationToken authToken = authenticationRepo.findFirstByTokenValue(userToken);

        authenticationRepo.delete(authToken);
    }
}
