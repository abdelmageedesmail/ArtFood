package com.art4musilm.artfoodCustomer.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FamilySearchResponse {

    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private List<DataEntity> data;
    @Expose
    @SerializedName("status")
    private boolean status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class DataEntity {
        @Expose
        @SerializedName("familystatus_nameen")
        private String familystatus_nameen;
        @Expose
        @SerializedName("familystatus_name")
        private String familystatus_name;
        @Expose
        @SerializedName("isdelivery")
        private String isdelivery;
        @Expose
        @SerializedName("subtypefamily")
        private String subtypefamily;
        @Expose
        @SerializedName("knownnumber")
        private String knownnumber;
        @Expose
        @SerializedName("order_active")
        private String order_active;
        @Expose
        @SerializedName("deliveryprice")
        private String deliveryprice;
        @Expose
        @SerializedName("address")
        private String address;
        @Expose
        @SerializedName("stars")
        private String stars;
        @Expose
        @SerializedName("expectedtime")
        private String expectedtime;
        @Expose
        @SerializedName("distance")
        private String distance;
        @Expose
        @SerializedName("hourto")
        private String hourto;
        @Expose
        @SerializedName("hourfrom")
        private String hourfrom;
        @Expose
        @SerializedName("vehicleplatenumber")
        private String vehicleplatenumber;
        @Expose
        @SerializedName("typevehicle")
        private String typevehicle;
        @Expose
        @SerializedName("nationality")
        private String nationality;
        @Expose
        @SerializedName("idphoto")
        private String idphoto;
        @Expose
        @SerializedName("familystatus")
        private String familystatus;
        @Expose
        @SerializedName("iban")
        private String iban;
        @Expose
        @SerializedName("bankname")
        private String bankname;
        @Expose
        @SerializedName("commercialrecord")
        private String commercialrecord;
        @Expose
        @SerializedName("categors")
        private String categors;
        @Expose
        @SerializedName("codeactive")
        private String codeactive;
        @Expose
        @SerializedName("image")
        private String image;
        @Expose
        @SerializedName("coderecovery")
        private String coderecovery;
        @Expose
        @SerializedName("license")
        private String license;
        @Expose
        @SerializedName("commercialregister")
        private String commercialregister;
        @Expose
        @SerializedName("clienttype")
        private String clienttype;
        @Expose
        @SerializedName("rewards")
        private String rewards;
        @Expose
        @SerializedName("token")
        private String token;
        @Expose
        @SerializedName("gender")
        private String gender;
        @Expose
        @SerializedName("year")
        private String year;
        @Expose
        @SerializedName("month")
        private String month;
        @Expose
        @SerializedName("day")
        private String day;
        @Expose
        @SerializedName("code")
        private String code;
        @Expose
        @SerializedName("lastdate")
        private String lastdate;
        @Expose
        @SerializedName("lng")
        private String lng;
        @Expose
        @SerializedName("lat")
        private String lat;
        @Expose
        @SerializedName("points")
        private String points;
        @Expose
        @SerializedName("stepfour")
        private String stepfour;
        @Expose
        @SerializedName("stepthree")
        private String stepthree;
        @Expose
        @SerializedName("steptwo")
        private String steptwo;
        @Expose
        @SerializedName("stepone")
        private String stepone;
        @Expose
        @SerializedName("active")
        private String active;
        @Expose
        @SerializedName("numberhome")
        private String numberhome;
        @Expose
        @SerializedName("street")
        private String street;
        @Expose
        @SerializedName("neighborhood")
        private String neighborhood;
        @Expose
        @SerializedName("date")
        private String date;
        @Expose
        @SerializedName("emailencrption")
        private String emailencrption;
        @Expose
        @SerializedName("country")
        private String country;
        @Expose
        @SerializedName("city")
        private String city;
        @Expose
        @SerializedName("mobile")
        private String mobile;
        @Expose
        @SerializedName("email")
        private String email;
        @Expose
        @SerializedName("lname")
        private String lname;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("type")
        private String type;
        @Expose
        @SerializedName("copypassword")
        private String copypassword;
        @Expose
        @SerializedName("password")
        private String password;
        @Expose
        @SerializedName("id")
        private String id;

        public String getFamilystatus_nameen() {
            return familystatus_nameen;
        }

        public void setFamilystatus_nameen(String familystatus_nameen) {
            this.familystatus_nameen = familystatus_nameen;
        }

        public String getFamilystatus_name() {
            return familystatus_name;
        }

        public void setFamilystatus_name(String familystatus_name) {
            this.familystatus_name = familystatus_name;
        }

        public String getIsdelivery() {
            return isdelivery;
        }

        public void setIsdelivery(String isdelivery) {
            this.isdelivery = isdelivery;
        }

        public String getSubtypefamily() {
            return subtypefamily;
        }

        public void setSubtypefamily(String subtypefamily) {
            this.subtypefamily = subtypefamily;
        }

        public String getKnownnumber() {
            return knownnumber;
        }

        public void setKnownnumber(String knownnumber) {
            this.knownnumber = knownnumber;
        }

        public String getOrder_active() {
            return order_active;
        }

        public void setOrder_active(String order_active) {
            this.order_active = order_active;
        }

        public String getDeliveryprice() {
            return deliveryprice;
        }

        public void setDeliveryprice(String deliveryprice) {
            this.deliveryprice = deliveryprice;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public String getExpectedtime() {
            return expectedtime;
        }

        public void setExpectedtime(String expectedtime) {
            this.expectedtime = expectedtime;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getHourto() {
            return hourto;
        }

        public void setHourto(String hourto) {
            this.hourto = hourto;
        }

        public String getHourfrom() {
            return hourfrom;
        }

        public void setHourfrom(String hourfrom) {
            this.hourfrom = hourfrom;
        }

        public String getVehicleplatenumber() {
            return vehicleplatenumber;
        }

        public void setVehicleplatenumber(String vehicleplatenumber) {
            this.vehicleplatenumber = vehicleplatenumber;
        }

        public String getTypevehicle() {
            return typevehicle;
        }

        public void setTypevehicle(String typevehicle) {
            this.typevehicle = typevehicle;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        public String getIdphoto() {
            return idphoto;
        }

        public void setIdphoto(String idphoto) {
            this.idphoto = idphoto;
        }

        public String getFamilystatus() {
            return familystatus;
        }

        public void setFamilystatus(String familystatus) {
            this.familystatus = familystatus;
        }

        public String getIban() {
            return iban;
        }

        public void setIban(String iban) {
            this.iban = iban;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getCommercialrecord() {
            return commercialrecord;
        }

        public void setCommercialrecord(String commercialrecord) {
            this.commercialrecord = commercialrecord;
        }

        public String getCategors() {
            return categors;
        }

        public void setCategors(String categors) {
            this.categors = categors;
        }

        public String getCodeactive() {
            return codeactive;
        }

        public void setCodeactive(String codeactive) {
            this.codeactive = codeactive;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCoderecovery() {
            return coderecovery;
        }

        public void setCoderecovery(String coderecovery) {
            this.coderecovery = coderecovery;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getCommercialregister() {
            return commercialregister;
        }

        public void setCommercialregister(String commercialregister) {
            this.commercialregister = commercialregister;
        }

        public String getClienttype() {
            return clienttype;
        }

        public void setClienttype(String clienttype) {
            this.clienttype = clienttype;
        }

        public String getRewards() {
            return rewards;
        }

        public void setRewards(String rewards) {
            this.rewards = rewards;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLastdate() {
            return lastdate;
        }

        public void setLastdate(String lastdate) {
            this.lastdate = lastdate;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getStepfour() {
            return stepfour;
        }

        public void setStepfour(String stepfour) {
            this.stepfour = stepfour;
        }

        public String getStepthree() {
            return stepthree;
        }

        public void setStepthree(String stepthree) {
            this.stepthree = stepthree;
        }

        public String getSteptwo() {
            return steptwo;
        }

        public void setSteptwo(String steptwo) {
            this.steptwo = steptwo;
        }

        public String getStepone() {
            return stepone;
        }

        public void setStepone(String stepone) {
            this.stepone = stepone;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public String getNumberhome() {
            return numberhome;
        }

        public void setNumberhome(String numberhome) {
            this.numberhome = numberhome;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getNeighborhood() {
            return neighborhood;
        }

        public void setNeighborhood(String neighborhood) {
            this.neighborhood = neighborhood;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getEmailencrption() {
            return emailencrption;
        }

        public void setEmailencrption(String emailencrption) {
            this.emailencrption = emailencrption;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLname() {
            return lname;
        }

        public void setLname(String lname) {
            this.lname = lname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCopypassword() {
            return copypassword;
        }

        public void setCopypassword(String copypassword) {
            this.copypassword = copypassword;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
