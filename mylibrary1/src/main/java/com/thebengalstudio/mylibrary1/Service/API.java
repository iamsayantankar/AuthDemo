package com.thebengalstudio.mylibrary1.Service;

public class API {
    private static final String website_url = "https://user.thebengalstudio.com/api";


    //TODO: User Auth Api
    private static final String auth_api = website_url+"/userauth/";
    public static final String signup_api = auth_api+"/signup";
    public static final String signup_email_otp_api = auth_api+"/signup_email_otp";
    public static final String signup_resend_email_otp_api = auth_api+"/signup_resend_email_otp";
    public static final String signup_email_change_api = auth_api+"/signup_email_change";
    public static final String signup_email_confirm_api = auth_api+"/signup_email_confirm";
    public static final String login_api = auth_api+"/login";
    public static final String login__otp_api = auth_api+"/login_otp";
    public static final String login_resend_otp_api = auth_api+"/login_resend_otp";
    public static final String forgatepassword_email_otp_api = auth_api+"/forgatepassword_email_otp";
    public static final String forgatepassword_resend_email_otp_api = auth_api+"/forgatepassword_resend_email_otp";
    public static final String forgatepassword_confirm_api = website_url+"/forgatepassword_confirm";



}
