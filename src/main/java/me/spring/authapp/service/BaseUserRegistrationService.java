package me.spring.authapp.service;


import me.spring.authapp.model.KeycloakUser;
import me.spring.authapp.model.ProviderUser;
import me.spring.authapp.model.User;
import me.spring.authapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseUserRegistrationService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public void register(ProviderUser providerUser, OAuth2UserRequest userRequest){
        User user = userRepository.findByUsername(providerUser.getUsername());

        if(user == null){
            ClientRegistration clientRegistration = userRequest.getClientRegistration();
            userService.register(clientRegistration.getRegistrationId(),providerUser);
        }else{
            System.out.println("userRequest = " + userRequest);
        }
    }

    public ProviderUser providerUser(ClientRegistration clientRegistration, OAuth2User oAuth2User){

        String registrationId = clientRegistration.getRegistrationId();
        if(registrationId.equals("keycloak")){
            return new KeycloakUser(oAuth2User,clientRegistration);

        }
        return null;
    }

    public UserService getUserService() {
        return userService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
