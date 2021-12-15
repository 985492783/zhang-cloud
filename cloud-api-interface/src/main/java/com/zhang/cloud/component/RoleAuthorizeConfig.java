package com.zhang.cloud.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 98549
 * @date 2021/10/24 23:19
 */
@Component
@ConfigurationProperties(prefix = "zhang")
public class RoleAuthorizeConfig {
    private boolean enabled;
    private static String profile;


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public static String getProfile() {
        return profile;
    }

    public static void setProfile(String profile) {
        RoleAuthorizeConfig.profile = profile;
    }
    public static String getUploadPath()
    {
        return getProfile();
    }
}
