package com.jojoidu.book.springboot.config.auth;

import com.jojoidu.book.springboot.config.auth.dto.OAuthAttributes;
import com.jojoidu.book.springboot.domain.user.User;
import com.jojoidu.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("CustomOAth2UserService.java loadUser()");
        OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId,userNameAttributeName,oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        System.out.println(user.getEmail());
        System.out.println(attributes.getAttributes());
        System.out.println(attributes.getNameAttributeKey());
        System.out.println("*****httpSession.setAttribute");
        httpSession.setAttribute("user",user);
        User user1 = (User)httpSession.getAttribute("user");
        System.out.println(user1.getName());
        System.out.println(user1.getEmail());
        System.out.println("*****//httpSession.setAttribute");
        System.out.println("CustomOAth2UserService.java /loadUser()");
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),attributes.getAttributes(),attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        System.out.println("CustomOAth2UserService.java saveOrUpdateUser()");
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        System.out.println("CustomOAth2UserService.java /saveOrUpdateUser()");
        return userRepository.save(user);
    }
}
