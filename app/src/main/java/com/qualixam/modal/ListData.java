package com.qualixam.modal;


/**
 * Created by akhtarraza on 11/02/16.
 */
public class ListData {
    private String registerid;
    private String name;
    private String address;
    private String ratings;
    private String specialisation;
    private String completeaddress;
    private String workinghours;
    private String phone;
    private String distance;
    private String offers;
    private String about;
    private String website;
    private String email;
    private String type;

    public ListData(String registerid,String name, String address, String ratings,String specialisation,
                    String completeaddress, String workinghours,String phone,
                    String distance, String offers,String about,String website, String email,String type){
        this.registerid = registerid;
        this.name = name;
        this.address = address;
        this.ratings = ratings;
        this.specialisation = specialisation;
        this.completeaddress = completeaddress;
        this.workinghours = workinghours;
        this.phone = phone;
        this.distance = distance;
        this.offers = offers;
        this.about = about;
        this.website = website;
        this.email = email;
        this.type = type;
    }
    public String getRegisterid(){
        return registerid;
    }
    public void setRegisterid(String registerid){
        this.registerid = registerid;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRatings()
    {
        return ratings;
    }
    public void setRatings(String ratings)
    {
        this.ratings = ratings;
    }
    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getCompleteAddress() {
        return completeaddress;
    }

    public void setCompleteAddress(String completeaddress) {
        this.completeaddress = completeaddress;
    }

    public String getWorkingHours()
    {
        return workinghours;
    }
    public void setWorkingHours(String workinghours)
    {
        this.workinghours = workinghours;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getOffers()
    {
        return offers;
    }
    public void setOffers(String offers)
    {
        this.offers = offers;
    }

    public String getAbout()
    {
        return about;
    }
    public void setAbout(String about)
    {
        this.about = about;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite()
    {
        return website;
    }
    public void setWebsite(String website)
    {
        this.website = website;
    }

    public String getType()
    {
        return  type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    /*
    * 1     NGO
    * 2     Hospital
    * 3     MedBanks
    * 4     Shop
    * 5     Rentals
    * 6     Clinics
    * 7     Doctors
    * */
}