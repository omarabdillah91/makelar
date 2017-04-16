package model;

/**
 * Created by omaabdillah on 4/2/17.
 */

public class User {
    public int user_id;
    public String username;
    public String email;
    public String password;
    public String date_of_birth;
    public String phone_number;
    public String sex;
    public String description;
    public String address;
    public String address_village;
    public String address_district;
    public String address_regency;
    public String address_province;
    public String address_postal_code;

    public User(String username, String email, String password, String date_of_birth, String phone_number, String sex, String description) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.date_of_birth = date_of_birth;
        this.phone_number = phone_number;
        this.sex = sex;
        this.description = description;
    }


    public User(String username, String email, String password, String date_of_birth, String phone_number, String sex, String description, String address, String address_village, String address_district, String address_regency, String address_province, String address_postal_code) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.date_of_birth = date_of_birth;
        this.phone_number = phone_number;
        this.sex = sex;
        this.description = description;
        this.address = address;
        this.address_village = address_village;
        this.address_district = address_district;
        this.address_regency = address_regency;
        this.address_province = address_province;
        this.address_postal_code = address_postal_code;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_village() {
        return address_village;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress_village(String address_village) {
        this.address_village = address_village;
    }

    public String getAddress_district() {
        return address_district;
    }

    public void setAddress_district(String address_district) {
        this.address_district = address_district;
    }

    public String getAddress_regency() {
        return address_regency;
    }

    public void setAddress_regency(String address_regency) {
        this.address_regency = address_regency;
    }

    public String getAddress_province() {
        return address_province;
    }

    public void setAddress_province(String address_province) {
        this.address_province = address_province;
    }

    public String getAddress_postal_code() {
        return address_postal_code;
    }

    public void setAddress_postal_code(String address_postal_code) {
        this.address_postal_code = address_postal_code;
    }
}
