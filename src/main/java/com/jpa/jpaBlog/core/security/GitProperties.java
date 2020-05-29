package com.jpa.jpaBlog.core.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

@ConfigurationProperties("git")
public class GitProperties {
    private final Github gitHub = new Github();
    private final Security security = new Security();

    public Github getGithub() {
        return this.gitHub;
    }

    public Security getSecurity() {
        return this.security;
    }

    public static class Github {
        private String token;

        public String getToken() {
            return this.token;
        }

        public void setToken (String token) {
            this.token = token;
        }
    }

    public static class Security {
        private List<String> admins = Collections.singletonList("hyun gyu");

        public List<String> getAdmins() {
            return admins;
        }

        public void setAdmins(List<String> admins) {
            this.admins = admins;
        }
    }
}
