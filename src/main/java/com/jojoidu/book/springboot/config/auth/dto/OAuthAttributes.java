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
        System.out.println("OAuthAttirbutes.java Constructor()");
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributesName,
                                     Map<String,Object> attributes){
        System.out.println("OAuthAttirbutes.java of()");
        return ofGoogle(userNameAttributesName,attributes);
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String,Object> attributes){
        System.out.println("OAuthAttirbutes.java ofGoogle()");
        return OAuthAttributes.builder().name((String)attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
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
