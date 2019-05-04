package com.example.laptophp.myapplication.sampledata;

public class createuser {
    public String  user_name;
    public String mobile;
    public String email;
    public String gender;

    public createuser() {
    }

    public createuser(String email,String gender,String mobile,String user_name)

    {
        this.user_name = user_name;
        this.email = email;

        this.mobile = mobile;

        this.gender = gender;
    }
}

