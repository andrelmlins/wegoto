package com.simbora;

/**
 * Created by AndreLucas on 20/05/2018.
 */

public class Viagem {
    private String depLat;
    private String depLon;
    private String depMod;
    private String depDate;
    private String groupName;
    private User userAdmin;

    public Viagem(String name, String depLat, String depLon, String depMod, String depDate, String groupName, User userAdmin) {
        this.depLat = depLat;
        this.depLon = depLon;
        this.depMod = depMod;
        this.depDate = depDate;
        this.groupName = groupName;
        this.userAdmin = userAdmin;
    }

    public String getDepLat() {
        return depLat;
    }

    public void setDepLat(String depLat) {
        this.depLat = depLat;
    }

    public String getDepLon() {
        return depLon;
    }

    public void setDepLon(String depLon) {
        this.depLon = depLon;
    }

    public String getDepMod() {
        return depMod;
    }

    public void setDepMod(String depMod) {
        this.depMod = depMod;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public User getUserAdmin() {
        return userAdmin;
    }

    public void setUserAdmin(User userAdmin) {
        this.userAdmin = userAdmin;
    }
}
