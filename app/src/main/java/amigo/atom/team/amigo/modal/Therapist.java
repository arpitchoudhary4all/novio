package amigo.atom.team.amigo.modal;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by MOHIT MALHOTRA on 21-04-2018.
 */

public class Therapist {

    private String tid;
    private String name;
    private String email;
    private String phone;
    private double longitude;
    private double latitude;
    private Map<String, Double> charges;

    private ArrayList<Map<String, String>> bookings;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Map<String, Double> getCharges() {
        return charges;
    }

    public void setCharges(Map<String, Double> charges) {
        this.charges = charges;
    }

    public ArrayList<Map<String, String>> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Map<String, String>> bookings) {
        this.bookings = bookings;
    }

    public ArrayList<String> getUsers() {

        ArrayList<String> users = new ArrayList<>();

        for(Map<String, String> b : this.bookings){
            users.add(b.get("user"));
        }

        return users;
    }
}
