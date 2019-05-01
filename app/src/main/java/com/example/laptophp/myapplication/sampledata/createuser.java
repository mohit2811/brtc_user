package com.example.laptophp.myapplication.sampledata;

public class createuser {
    public String  user_name;
    public String mobile;
    public String email;
    public String gender;

    public createuser() {
    }

    public createuser(String user_name,String email,String mobile,String gender)

    {
        this.user_name = user_name;
        this.email = email;

        this.mobile = mobile;

        this.gender = gender;
    }
}