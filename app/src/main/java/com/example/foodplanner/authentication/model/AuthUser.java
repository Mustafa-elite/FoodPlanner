package com.example.foodplanner.authentication.model;

public class AuthUser {
    private String name;
    private String email;
    private String password;
    private boolean autherized;
    private static AuthUser authUser =null;

    private AuthUser(String name, String email,String password) {
        this.name = name;
        this.email = email;
        this.password=password;
    }


    public static AuthUser getInstance(String name, String email,String password)
    {
        if(authUser ==null)
        {
            authUser = new AuthUser(name,email,password);

        }
        return authUser;
    }
    public static AuthUser getInstance()
    {
        if(authUser !=null)
        {
            return authUser;
        }
        else {
            throw new RuntimeException("No Auth user found");
        }

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAutherized() {
        return autherized;
    }

    public void setAutherized(boolean autherized) {
        this.autherized = autherized;
    }





    public static void deleteUser()
    {
        authUser=null;
    }

}
