package com.jojoidu.book.springboot.config.auth.dto;

import com.jojoidu.book.springboot.domain.user.Role;
import com.jojoidu.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String,Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String,Object> attributes, String nameAttributeKey,
                           String name, String email, String picture){
        System.out.println("OAuthAttributes Constructor()");
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributesName,
                                     Map<String,Object> attributes){
        System.out.println("OAuthAttributes of()");
        if("naver".equals(registrationId))
            return ofNaver(userNameAttributesName,attributes);
        
        return ofGoogle(userNameAttributesName,attributes);
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String,Object> attributes){
        System.out.println("OAuthAttributes ofGoogle()");
        return OAuthAttributes.builder().name((String)attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String,Object> attributes){
        Map<String,Object> response = (Map<String, Object>) attributes.get("response");

        System.out.println("OAuthAttributes ofNaver()");
        return OAuthAttributes.builder().name((String)response.get("name"))
                                        .email((String) response.get("email"))
                                        .picture((String) response.get("profile_image"))
                                        .attributes(response)
                                        .nameAttributeKey(userNameAttributeName)
                                        .build();
    }

    public User toEntity(){
        System.out.println("OAuthAttirbutes.java toEntity()");
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
