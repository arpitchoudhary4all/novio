package amigo.atom.team.amigo.modal;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by MOHIT MALHOTRA on 21-04-2018.
 */

public class User {

    private String uid;
    private String name;
    private String bio;
    private String address;
    private double latitude;
    private double longitude;
    private String email;
    private String phone;

    private ArrayList<Map<String, String>> bookings;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public ArrayList<Map<String, String>> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Map<String, String>> bookings) {
        this.bookings = bookings;
    }

    public ArrayList<String> getTherapists() {
        ArrayList<String> therapists = new ArrayList<>();

        for(Map<String, String> b : this.bookings){
            therapists.add(b.get("therapist"));
        }

        return therapists;
    }
}
