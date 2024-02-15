package com.example.portfolio;

public class data {
    String name;
    String age;
    String password;
    String strength;
    String weakness;
    String imageUrl;
    private long timestamp;
    public data() {
    }

    public data(String name, String age, String password, String strength, String weakness,String imageUrl) {
        this.name = name;
        this.age = age;
        this.password = password;
        this.strength = strength;
        this.weakness = weakness;
        this.imageUrl=imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getWeakness() {
        return weakness;
    }

    public void setWeakness(String weakness) {
        this.weakness = weakness;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    // Timestamp getter
    public long getTimestamp() {
        return timestamp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
