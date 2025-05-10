package me.spring.authapp.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class User {
    private String registrationId;
    private String id;
    private String username;
    private String password;
    private String provider;
    private String email;
    private List<? extends GrantedAuthority> authorities;

    private User(Builder builder) {
        this.registrationId = builder.registrationId;
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.provider = builder.provider;
        this.email = builder.email;
        this.authorities = builder.authorities;
    }

    // 정적 builder 메서드 추가
    public static Builder builder() {
        return new Builder();
    }

    // Getter 메서드들
    public String getRegistrationId() {
        return registrationId;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProvider() {
        return provider;
    }

    public String getEmail() {
        return email;
    }

    public List<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // Builder 클래스 정의
    public static class Builder {
        private String registrationId;
        private String id;
        private String username;
        private String password;
        private String provider;
        private String email;
        private List<? extends GrantedAuthority> authorities;

        public Builder registrationId(String registrationId) {
            this.registrationId = registrationId;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder provider(String provider) {
            this.provider = provider;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder authorities(List<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}

