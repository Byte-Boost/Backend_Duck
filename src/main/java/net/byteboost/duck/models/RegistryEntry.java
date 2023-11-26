package net.byteboost.duck.models;

public class RegistryEntry {
    String userId;
    String title;
    String access;
    String username;

    public RegistryEntry(String userId, String title, String access) {
        this.userId = userId;
        this.title = title;
        this.access = access;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getAccess() {
        return access;
    }

    public String getUsername() {
        return username;
    }
}
