package com.thebengalstudio.mylibrary1.SignUpProcess;

import static com.thebengalstudio.mylibrary1.Service.API.login_api;
import static com.thebengalstudio.mylibrary1.Service.API.login_resend_otp_api;
import static com.thebengalstudio.mylibrary1.Service.CreateOTP.UNIC_OTP;
import static com.thebengalstudio.mylibrary1.Service.ProgressDialog.Show_loader;
import static com.thebengalstudio.mylibrary1.Service.ProgressDialog.cancel_loader;
import static com.thebengalstudio.mylibrary1.Service.SharePref.LoadSharePref;
import static com.thebengalstudio.mylibrary1.Service.SharePref.SaveInSharePref;
import static com.thebengalstudio.mylibrary1.Service.StoreValue.app_uid;
import static com.thebengalstudio.mylibrary1.Service.StoreValue.app_passcode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.thebengalstudio.mylibrary1.R;
import com.thebengalstudio.mylibrary1.Service.network;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class LogIn extends AppCompatActivity {


    Context context = LogIn.this;
    Activity activity = LogIn.this;

    LinearLayout logInPanel , signUpPanel , forgetPasswordPanel;
    EditText lUserName , lPassword , lOTP ,  sEmail , sPassword , sConfirmPassword , sPhoneNumber , sOTP , sNewEmail , fUserName , fOTP , fPassword , fConfirmPassword;
    Button lLogIn , lVerifyOTP , lSignUp , sSignUp , sVerifyOTP , sUpdateNewEmail , sLogIn , fSendOTP , fVerifyOTP , fSetPassword , fLogIn , fSignUp;
    TextView lResendOTP , lForgetPassword , sResendOTP, sChangeEmail , fResendOTP , fChangeUsername;

    String email,password,cpassword,number,otp;

    String create_OTP = "" , tbs_verify_uid = "" , tbs_uid = "";

    long countDownTimer_statusInt = 0;
    CountDownTimer countDownTimer;
    boolean countDownTimer_status = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        url_list.MyUserUid= LoadSharePref(context,"user_uid");

        Bundle extras= getIntent().getExtras();

        if(extras.getString("app_passcode")!= null)
        {
            app_passcode = extras.getString("app_passcode");
        }

        if(extras.getString("app_uid")!= null)
        {
            app_uid= extras.getString("app_uid").toString();
        }


        logInPanel = findViewById(R.id.logInPanel) ;
        signUpPanel = findViewById(R.id.signUpPanel) ;
        forgetPasswordPanel = findViewById(R.id.forgetPasswordPanel) ;

        lUserName = findViewById(R.id.lUserName) ;
        lPassword = findViewById(R.id.lPassword) ;
        lOTP = findViewById(R.id.lOTP) ;
        sEmail = findViewById(R.id.sEmail) ;
        sPassword = findViewById(R.id.sPassword) ;
        sConfirmPassword = findViewById(R.id.sConfirmPassword) ;
        sPhoneNumber = findViewById(R.id.sPhoneNumber) ;
        sOTP = findViewById(R.id.sOTP) ;
        sNewEmail = findViewById(R.id.sNewEmail) ;
        fUserName = findViewById(R.id.fUserName) ;
        fOTP = findViewById(R.id.fOTP) ;
        fPassword = findViewById(R.id.fPassword) ;
        fConfirmPassword = findViewById(R.id.fConfirmPassword) ;

        lLogIn = findViewById(R.id.lLogIn) ;
        lVerifyOTP = findViewById(R.id.lVerifyOTP) ;
        lSignUp = findViewById(R.id.lSignUp) ;
        sSignUp = findViewById(R.id.sSignUp) ;
        sVerifyOTP = findViewById(R.id.sVerifyOTP) ;
        sUpdateNewEmail = findViewById(R.id.sUpdateNewEmail) ;
        sLogIn = findViewById(R.id.sLogIn) ;
        fSendOTP = findViewById(R.id.fSendOTP) ;
        fVerifyOTP = findViewById(R.id.fVerifyOTP) ;
        fSetPassword = findViewById(R.id.fSetPassword) ;
        fLogIn = findViewById(R.id.fLogIn) ;
        fSignUp = findViewById(R.id.fSignUp) ;

        lResendOTP = findViewById(R.id.lResendOTP) ;
        lForgetPassword = findViewById(R.id.lForgetPassword) ;
        sResendOTP = findViewById(R.id.sResendOTP) ;
        sChangeEmail = findViewById(R.id.sChangeEmail) ;
        fResendOTP = findViewById(R.id.fResendOTP) ;
        fChangeUsername= findViewById(R.id.fChangeUsername) ;


        logInPanel.setVisibility(View.VISIBLE) ;
        signUpPanel.setVisibility(View.GONE) ;
        forgetPasswordPanel.setVisibility(View.GONE) ;

        lUserName.setVisibility(View.VISIBLE) ;
        lPassword.setVisibility(View.VISIBLE) ;
        lLogIn.setVisibility(View.VISIBLE) ;
        lOTP.setVisibility(View.GONE) ;
        lVerifyOTP.setVisibility(View.GONE) ;
        lResendOTP.setVisibility(View.GONE) ;
        lForgetPassword.setVisibility(View.VISIBLE) ;




        fUserName.setVisibility(View.VISIBLE) ;
        fSendOTP.setVisibility(View.VISIBLE) ;
        fOTP.setVisibility(View.GONE) ;
        fVerifyOTP.setVisibility(View.GONE) ;
        fResendOTP.setVisibility(View.GONE) ;
        fChangeUsername.setVisibility(View.GONE) ;
        fPassword.setVisibility(View.GONE) ;
        fConfirmPassword.setVisibility(View.GONE) ;
        fSetPassword.setVisibility(View.GONE) ;


        sEmail.setVisibility(View.VISIBLE) ;
        sPassword.setVisibility(View.VISIBLE) ;
        sConfirmPassword.setVisibility(View.VISIBLE) ;
        sPhoneNumber.setVisibility(View.VISIBLE) ;
        sSignUp.setVisibility(View.VISIBLE) ;
        sOTP.setVisibility(View.GONE) ;
        sVerifyOTP.setVisibility(View.GONE) ;
        sNewEmail.setVisibility(View.GONE) ;
        sUpdateNewEmail.setVisibility(View.GONE) ;
        sResendOTP.setVisibility(View.GONE) ;
        sChangeEmail.setVisibility(View.GONE) ;


        if(LoadSharePref(context, "auth_uid").isEmpty()){
            SaveInSharePref(context,"auth_uid","null");
        }


//        StringRequest createLogIn = new StringRequest(Request.Method.POST, login_api,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                NetworkResponse networkResponse = error.networkResponse;
//                if (networkResponse != null) {
//
//                }else{
//
//                }
//            }
//        }){
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String,String> params = new HashMap<String,String>();
//
//                params.put("app_passcode",LoadSharePref(context, "app_passcode").toString());
//                params.put("app_uid",LoadSharePref(context, "app_uid").toString());
//                params.put("email",email);
//                params.put("password",password);
//                params.put("create_OTP",create_OTP);
//
//
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue_createLogIn = Volley.newRequestQueue(context);
//        requestQueue_createLogIn.add(createLogIn);
    }



    public void lLogIn(View view) {
        email = lUserName.getText().toString();
        password = lPassword.getText().toString();
        cpassword = "";
        number = "";
        otp = "";

        create_OTP = UNIC_OTP(6);

        if(email.isEmpty()){
            StyleableToast.makeText(context,"Enter Email", Toast.LENGTH_LONG,R.style.toaststyle_info).show();

        }else if(password.isEmpty()){
            StyleableToast.makeText(context,"Enter Password", Toast.LENGTH_LONG,R.style.toaststyle_info).show();

        }else if(!network.getInstance(context).isOnline()){

        }else {
            Show_loader(context);
            StringRequest createLogIn = new StringRequest(Request.Method.POST, login_api,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            cancel_loader();
                            try{

                                JSONObject jsonObject = new JSONObject(response);

                                String success = jsonObject.getString("code");
                                String message = jsonObject.getString("message");

                                if(success.equals("200")){
                                    String data = jsonObject.getString("data");
                                    JSONObject jsonDataObject = new JSONObject(data);

                                    StyleableToast.makeText(context,message, Toast.LENGTH_LONG,R.style.toaststyle_success).show();


                                    SaveInSharePref(context,"verify_password",jsonDataObject.getString("verify_password"));
                                    SaveInSharePref(context,"auth_password",jsonDataObject.getString("auth_password"));
                                    SaveInSharePref(context,"app_status",jsonDataObject.getString("app_status"));
                                    SaveInSharePref(context,"app_packge_name",jsonDataObject.getString("app_packge_name"));
                                    SaveInSharePref(context,"auth_uid",jsonDataObject.getString("auth_uid"));
                                    SaveInSharePref(context,"app_uid",jsonDataObject.getString("app_uid"));
                                    SaveInSharePref(context,"tbs_uid",jsonDataObject.getString("tbs_uid"));
                                    SaveInSharePref(context,"app_passcode",jsonDataObject.getString("app_passcode"));


                                    Intent intent=new Intent();
                                    intent.putExtra("app_passcode",jsonDataObject.getString("app_passcode"));
                                    intent.putExtra("tbs_uid",jsonDataObject.getString("tbs_uid"));
                                    intent.putExtra("app_uid",jsonDataObject.getString("app_uid"));
                                    intent.putExtra("auth_uid",jsonDataObject.getString("auth_uid"));
                                    intent.putExtra("app_packge_name",jsonDataObject.getString("app_packge_name"));
                                    intent.putExtra("app_status",jsonDataObject.getString("app_status"));
                                    intent.putExtra("auth_password",jsonDataObject.getString("auth_password"));
                                    intent.putExtra("verify_password", jsonDataObject.getString("verify_password"));
                                    setResult(RESULT_OK,intent);
                                    finish();//finishing activity

                                }else if(success.equals("201")){
                                    String type = jsonObject.getString("type");
                                    String response_code = jsonObject.getString("response_code");
                                    if(type.equals("user_login_info_status")){
                                        if(response_code.equals("0")){
                                            StyleableToast.makeText(context,message, Toast.LENGTH_LONG,R.style.toaststyle_info).show();
                                        }else if(response_code.equals("2")){
                                            StyleableToast.makeText(context,message, Toast.LENGTH_LONG,R.style.toaststyle_info).show();
                                        }else if(response_code.equals("3")){
                                            StyleableToast.makeText(context,message, Toast.LENGTH_LONG,R.style.toaststyle_error).show();
                                        }else if(response_code.equals("4")){
                                            StyleableToast.makeText(context,message, Toast.LENGTH_LONG,R.style.toaststyle_error).show();
                                        }else if(response_code.equals("5")){

                                            tbs_verify_uid = jsonObject.getString("tbs_verify_uid");
                                            tbs_uid = jsonObject.getString("tbs_uid");

                                            lUserName.setVisibility(View.GONE) ;
                                            lPassword.setVisibility(View.GONE) ;
                                            lLogIn.setVisibility(View.GONE) ;
                                            lOTP.setVisibility(View.VISIBLE) ;
                                            lVerifyOTP.setVisibility(View.VISIBLE) ;
                                            lResendOTP.setVisibility(View.VISIBLE) ;
                                            lForgetPassword.setVisibility(View.GONE) ;

                                            StyleableToast.makeText(context,"OTP send via mail.", Toast.LENGTH_LONG,R.style.toaststyle_success).show();

                                            countDownTimer_status = true;
                                            countDownTimer = new  CountDownTimer(30000,1000) {
                                                @Override
                                                public void onTick(long millisUntilFinished) {
                                                    countDownTimer_statusInt = millisUntilFinished/1000;
                                                }
                                                @Override
                                                public void onFinish() {
                                                    countDownTimer_status = false;
                                                }
                                            }.start();

                                        }else if(response_code.equals("6")){
                                            StyleableToast.makeText(context,message, Toast.LENGTH_LONG,R.style.toaststyle_error).show();
                                        }else{
                                            StyleableToast.makeText(context,"Something else...", Toast.LENGTH_LONG,R.style.toaststyle_error).show();
                                        }
                                    }else if(type.equals("app_auth_status")){
                                        if(response_code.equals("0")){
                                            StyleableToast.makeText(context,message, Toast.LENGTH_LONG,R.style.toaststyle_error).show();
                                        }else if(response_code.equals("2")){
                                            StyleableToast.makeText(context,message, Toast.LENGTH_LONG,R.style.toaststyle_error).show();
                                        }else if(response_code.equals("3")){
                                            StyleableToast.makeText(context,message, Toast.LENGTH_LONG,R.style.toaststyle_error).show();
                                        }else if(response_code.equals("4")){
                                            StyleableToast.makeText(context,message, Toast.LENGTH_LONG,R.style.toaststyle_error).show();
                                        }else{
                                            StyleableToast.makeText(context,"Something else...", Toast.LENGTH_LONG,R.style.toaststyle_error).show();
                                        }
                                    }else{
                                        StyleableToast.makeText(context,"Something else...", Toast.LENGTH_LONG,R.style.toaststyle_error).show();
                                    }



                                }else if(success.equals("202")){
                                    String type = jsonObject.getString("type");
                                    String response_code = jsonObject.getString("response_code");
                                    StyleableToast.makeText(context,message, Toast.LENGTH_LONG,R.style.toaststyle_info).show();
                                }else if(success.equals("404")){
                                    String type = jsonObject.getString("type");
                                    String response_code = jsonObject.getString("response_code");
                                    StyleableToast.makeText(context,message, Toast.LENGTH_LONG,R.style.toaststyle_error).show();

                                }else{
                                    StyleableToast.makeText(context,"Your data is not loaded", Toast.LENGTH_LONG,R.style.toaststyle_error).show();

                                }

                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    cancel_loader();
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null) {
                        StyleableToast.makeText(context,"No interner found...", Toast.LENGTH_LONG,R.style.toaststyle_error).show();
                    }else{
                        StyleableToast.makeText(context,"Internal server error...", Toast.LENGTH_LONG,R.style.toaststyle_error).show();
                    }
                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<String,String>();

                    params.put("app_passcode",LoadSharePref(context, "app_passcode").toString());
                    params.put("app_uid",LoadSharePref(context, "app_uid").toString());
                    params.put("email",email);
                    params.put("password",password);
                    params.put("create_OTP",create_OTP);

                    return params;
                }
            };

            RequestQueue requestQueue_createLogIn = Volley.newRequestQueue(context);
            requestQueue_createLogIn.add(createLogIn);

        }


    }

    public void lVerifyOTP(View view) {

        email = "";
        password = "";
        cpassword = "";
        number = "";
        otp = lOTP.getText().toString();

        if(!otp.isEmpty()){



            SaveInSharePref(context,"app_passcode",app_passcode);
            SaveInSharePref(context,"tbs_uid","tbs_uid 123 234");
            SaveInSharePref(context,"app_uid","app_uid 234");
            SaveInSharePref(context,"auth_uid","auth_uid 234");
            SaveInSharePref(context,"app_packge_name","app_packge_name 234");
            SaveInSharePref(context,"app_status","app_status 234");
            SaveInSharePref(context,"auth_password","auth_password 234");
            SaveInSharePref(context,"verify_password","verify_password 234");

            Intent intent=new Intent();
            intent.putExtra("app_passcode",app_passcode);
            intent.putExtra("tbs_uid","tbs_uid 123");
            intent.putExtra("app_uid",app_uid);
            intent.putExtra("auth_uid","auth_uid 234");
            intent.putExtra("app_packge_name","app_packge_name 345");
            intent.putExtra("app_status","app_status 456");
            intent.putExtra("auth_password","auth_password 567");
            intent.putExtra("verify_password","verify_password 678");
            setResult(RESULT_OK,intent);
            finish();//finishing activity



        }else{
            StyleableToast.makeText(context,"Enter OTP. If not get OTP via mail, Resend again.", Toast.LENGTH_LONG,R.style.toaststyle_info).show();
        }

    }

    public void lResendOTP(View view) {
        if(countDownTimer_status){
            StyleableToast.makeText(context,"Mail send after "+countDownTimer_statusInt+" seconds...", Toast.LENGTH_LONG,R.style.toaststyle_info).show();
        }else{
            Show_loader(context);
            StringRequest createLogIn = new StringRequest(Request.Method.POST, login_resend_otp_api,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            cancel_loader();
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("code");
                                String message = jsonObject.getString("message");

                                if(success.equals("200")){
                                    StyleableToast.makeText(context,"OTP send via mail.", Toast.LENGTH_LONG,R.style.toaststyle_success).show();

                                    countDownTimer_status = true;
                                    countDownTimer = new  CountDownTimer(30000,1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            countDownTimer_statusInt = millisUntilFinished/1000;
                                        }
                                        @Override
                                        public void onFinish() {
                                            countDownTimer_status = false;
                                        }
                                    }.start();

                                }else if(success.equals("201")){
                                    StyleableToast.makeText(context,message, Toast.LENGTH_LONG,R.style.toaststyle_error).show();

                                }else if(success.equals("404")){
                                    StyleableToast.makeText(context,message, Toast.LENGTH_LONG,R.style.toaststyle_error).show();

                                }else{

                                    StyleableToast.makeText(context,"Internal Server Error...", Toast.LENGTH_LONG,R.style.toaststyle_error).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    cancel_loader();
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null) {
                        StyleableToast.makeText(context,"No interner found...", Toast.LENGTH_LONG,R.style.toaststyle_error).show();
                    }else{
                        StyleableToast.makeText(context,"Internal server error...", Toast.LENGTH_LONG,R.style.toaststyle_error).show();
                    }
                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<String,String>();

                    params.put("app_passcode",LoadSharePref(context, "app_passcode").toString());
                    params.put("app_uid",LoadSharePref(context, "app_uid").toString());
                    params.put("tbs_uid",tbs_uid);
                    params.put("tbs_verify_uid",tbs_verify_uid);

                    return params;
                }
            };

            RequestQueue requestQueue_createLogIn = Volley.newRequestQueue(context);
            requestQueue_createLogIn.add(createLogIn);
        }
    }
    public void lForgetPassword(View view) {
        logInPanel.setVisibility(View.GONE) ;
        signUpPanel.setVisibility(View.GONE) ;
        forgetPasswordPanel.setVisibility(View.VISIBLE) ;


        lUserName.setVisibility(View.VISIBLE) ;
        lPassword.setVisibility(View.VISIBLE) ;
        lLogIn.setVisibility(View.VISIBLE) ;
        lOTP.setVisibility(View.GONE) ;
        lVerifyOTP.setVisibility(View.GONE) ;
        lResendOTP.setVisibility(View.GONE) ;
        lForgetPassword.setVisibility(View.VISIBLE) ;


        fUserName.setVisibility(View.VISIBLE) ;
        fSendOTP.setVisibility(View.VISIBLE) ;
        fOTP.setVisibility(View.GONE) ;
        fVerifyOTP.setVisibility(View.GONE) ;
        fResendOTP.setVisibility(View.GONE) ;
        fChangeUsername.setVisibility(View.GONE) ;
        fPassword.setVisibility(View.GONE) ;
        fConfirmPassword.setVisibility(View.GONE) ;
        fSetPassword.setVisibility(View.GONE) ;


        sEmail.setVisibility(View.VISIBLE) ;
        sPassword.setVisibility(View.VISIBLE) ;
        sConfirmPassword.setVisibility(View.VISIBLE) ;
        sPhoneNumber.setVisibility(View.VISIBLE) ;
        sSignUp.setVisibility(View.VISIBLE) ;
        sOTP.setVisibility(View.GONE) ;
        sVerifyOTP.setVisibility(View.GONE) ;
        sNewEmail.setVisibility(View.GONE) ;
        sUpdateNewEmail.setVisibility(View.GONE) ;
        sResendOTP.setVisibility(View.GONE) ;
        sChangeEmail.setVisibility(View.GONE) ;


    }

    public void lSignUp(View view) {
        logInPanel.setVisibility(View.GONE) ;
        signUpPanel.setVisibility(View.VISIBLE) ;
        forgetPasswordPanel.setVisibility(View.GONE) ;

        lUserName.setVisibility(View.VISIBLE) ;
        lPassword.setVisibility(View.VISIBLE) ;
        lLogIn.setVisibility(View.VISIBLE) ;
        lOTP.setVisibility(View.GONE) ;
        lVerifyOTP.setVisibility(View.GONE) ;
        lResendOTP.setVisibility(View.GONE) ;
        lForgetPassword.setVisibility(View.VISIBLE) ;


        fUserName.setVisibility(View.VISIBLE) ;
        fSendOTP.setVisibility(View.VISIBLE) ;
        fOTP.setVisibility(View.GONE) ;
        fVerifyOTP.setVisibility(View.GONE) ;
        fResendOTP.setVisibility(View.GONE) ;
        fChangeUsername.setVisibility(View.GONE) ;
        fPassword.setVisibility(View.GONE) ;
        fConfirmPassword.setVisibility(View.GONE) ;
        fSetPassword.setVisibility(View.GONE) ;

        sEmail.setVisibility(View.VISIBLE) ;
        sPassword.setVisibility(View.VISIBLE) ;
        sConfirmPassword.setVisibility(View.VISIBLE) ;
        sPhoneNumber.setVisibility(View.VISIBLE) ;
        sSignUp.setVisibility(View.VISIBLE) ;
        sOTP.setVisibility(View.GONE) ;
        sVerifyOTP.setVisibility(View.GONE) ;
        sNewEmail.setVisibility(View.GONE) ;
        sUpdateNewEmail.setVisibility(View.GONE) ;
        sResendOTP.setVisibility(View.GONE) ;
        sChangeEmail.setVisibility(View.GONE) ;
    }

    public void fSendOTP(View view) {

        create_OTP = UNIC_OTP(6);

        email = fUserName.getText().toString();
        password = "";
        cpassword = "";
        number = "";
        otp = "";

        if(!email.isEmpty()){
            fUserName.setVisibility(View.GONE) ;
            fSendOTP.setVisibility(View.GONE) ;
            fOTP.setVisibility(View.VISIBLE) ;
            fVerifyOTP.setVisibility(View.VISIBLE) ;
            fResendOTP.setVisibility(View.VISIBLE) ;
            fChangeUsername.setVisibility(View.VISIBLE) ;
            fPassword.setVisibility(View.GONE) ;
            fConfirmPassword.setVisibility(View.GONE) ;
            fSetPassword.setVisibility(View.GONE) ;
        }else{
            StyleableToast.makeText(context,"Enter Username or Email Id", Toast.LENGTH_LONG,R.style.toaststyle_info).show();
        }

    }

    public void fVerifyOTP(View view) {

        email = "";
        password = "";
        cpassword = "";
        number = "";
        otp = fOTP.getText().toString();

        if(!otp.isEmpty()){
            fUserName.setVisibility(View.GONE) ;
            fSendOTP.setVisibility(View.GONE) ;
            fOTP.setVisibility(View.GONE) ;
            fVerifyOTP.setVisibility(View.GONE) ;
            fResendOTP.setVisibility(View.GONE) ;
            fChangeUsername.setVisibility(View.GONE) ;
            fPassword.setVisibility(View.VISIBLE) ;
            fConfirmPassword.setVisibility(View.VISIBLE) ;
            fSetPassword.setVisibility(View.VISIBLE) ;
        }else{
            StyleableToast.makeText(context,"Enter OTP. If not get OTP via mail, Resend again.", Toast.LENGTH_LONG,R.style.toaststyle_info).show();
        }

    }

    public void fResendOTP(View view) {

    }

    public void fChangeUsername(View view) {
        email = "";
        password = "";
        cpassword = "";
        number = "";
        otp = "";

        fUserName.setText("");
        fOTP.setText("");
        fPassword.setText("");
        fConfirmPassword.setText("");

        fUserName.setVisibility(View.VISIBLE) ;
        fSendOTP.setVisibility(View.VISIBLE) ;
        fOTP.setVisibility(View.GONE) ;
        fVerifyOTP.setVisibility(View.GONE) ;
        fResendOTP.setVisibility(View.GONE) ;
        fChangeUsername.setVisibility(View.GONE) ;
        fPassword.setVisibility(View.GONE) ;
        fConfirmPassword.setVisibility(View.GONE) ;
        fSetPassword.setVisibility(View.GONE) ;
    }

    public void fSetPassword(View view) {
        email = "";
        password = fPassword.getText().toString();
        cpassword = fConfirmPassword.getText().toString();
        number = "";
        otp = "";

        if(password.isEmpty()){
            StyleableToast.makeText(context,"Enter new password.", Toast.LENGTH_LONG,R.style.toaststyle_info).show();
        }else if(cpassword.isEmpty()){
            StyleableToast.makeText(context,"Enter confirm password.", Toast.LENGTH_LONG,R.style.toaststyle_info).show();
        }else if(!password.equals(cpassword)){
            StyleableToast.makeText(context,"Both password not matched..", Toast.LENGTH_LONG,R.style.toaststyle_error).show();
        }else {

        }


    }

    public void fLogIn(View view) {
        logInPanel.setVisibility(View.VISIBLE) ;
        signUpPanel.setVisibility(View.GONE) ;
        forgetPasswordPanel.setVisibility(View.GONE) ;


        lUserName.setVisibility(View.VISIBLE) ;
        lPassword.setVisibility(View.VISIBLE) ;
        lLogIn.setVisibility(View.VISIBLE) ;
        lOTP.setVisibility(View.GONE) ;
        lVerifyOTP.setVisibility(View.GONE) ;
        lResendOTP.setVisibility(View.GONE) ;
        lForgetPassword.setVisibility(View.VISIBLE) ;


        fUserName.setVisibility(View.VISIBLE) ;
        fSendOTP.setVisibility(View.VISIBLE) ;
        fOTP.setVisibility(View.GONE) ;
        fVerifyOTP.setVisibility(View.GONE) ;
        fResendOTP.setVisibility(View.GONE) ;
        fChangeUsername.setVisibility(View.GONE) ;
        fPassword.setVisibility(View.GONE) ;
        fConfirmPassword.setVisibility(View.GONE) ;
        fSetPassword.setVisibility(View.GONE) ;

        sEmail.setVisibility(View.VISIBLE) ;
        sPassword.setVisibility(View.VISIBLE) ;
        sConfirmPassword.setVisibility(View.VISIBLE) ;
        sPhoneNumber.setVisibility(View.VISIBLE) ;
        sSignUp.setVisibility(View.VISIBLE) ;
        sOTP.setVisibility(View.GONE) ;
        sVerifyOTP.setVisibility(View.GONE) ;
        sNewEmail.setVisibility(View.GONE) ;
        sUpdateNewEmail.setVisibility(View.GONE) ;
        sResendOTP.setVisibility(View.GONE) ;
        sChangeEmail.setVisibility(View.GONE) ;
    }

    public void fSignUp(View view) {
        logInPanel.setVisibility(View.GONE) ;
        signUpPanel.setVisibility(View.VISIBLE) ;
        forgetPasswordPanel.setVisibility(View.GONE) ;


        lUserName.setVisibility(View.VISIBLE) ;
        lPassword.setVisibility(View.VISIBLE) ;
        lLogIn.setVisibility(View.VISIBLE) ;
        lOTP.setVisibility(View.GONE) ;
        lVerifyOTP.setVisibility(View.GONE) ;
        lResendOTP.setVisibility(View.GONE) ;
        lForgetPassword.setVisibility(View.VISIBLE) ;


        fUserName.setVisibility(View.VISIBLE) ;
        fSendOTP.setVisibility(View.VISIBLE) ;
        fOTP.setVisibility(View.GONE) ;
        fVerifyOTP.setVisibility(View.GONE) ;
        fResendOTP.setVisibility(View.GONE) ;
        fChangeUsername.setVisibility(View.GONE) ;
        fPassword.setVisibility(View.GONE) ;
        fConfirmPassword.setVisibility(View.GONE) ;
        fSetPassword.setVisibility(View.GONE) ;

        sEmail.setVisibility(View.VISIBLE) ;
        sPassword.setVisibility(View.VISIBLE) ;
        sConfirmPassword.setVisibility(View.VISIBLE) ;
        sPhoneNumber.setVisibility(View.VISIBLE) ;
        sSignUp.setVisibility(View.VISIBLE) ;
        sOTP.setVisibility(View.GONE) ;
        sVerifyOTP.setVisibility(View.GONE) ;
        sNewEmail.setVisibility(View.GONE) ;
        sUpdateNewEmail.setVisibility(View.GONE) ;
        sResendOTP.setVisibility(View.GONE) ;
        sChangeEmail.setVisibility(View.GONE) ;
    }

    public void sSignUp(View view) {

        email = sEmail.getText().toString();
        password = sPassword.getText().toString();
        cpassword = sConfirmPassword.getText().toString();
        number = sPhoneNumber.getText().toString();
        otp = "";

        create_OTP = UNIC_OTP(6);


        if(email.isEmpty()) {
            StyleableToast.makeText(context,"Enter Email Id.", Toast.LENGTH_LONG,R.style.toaststyle_info).show();
        }else if(password.isEmpty()){
            StyleableToast.makeText(context,"Enter password.", Toast.LENGTH_LONG,R.style.toaststyle_info).show();
        }else if(cpassword.isEmpty()){
            StyleableToast.makeText(context,"Enter confirm password.", Toast.LENGTH_LONG,R.style.toaststyle_info).show();
        }else if(!password.equals(cpassword)){
            StyleableToast.makeText(context,"Both password not matched..", Toast.LENGTH_LONG,R.style.toaststyle_error).show();
        }else if(number.isEmpty()){
            StyleableToast.makeText(context,"Enter Phone Number.", Toast.LENGTH_LONG,R.style.toaststyle_info).show();
        }else{

            sEmail.setVisibility(View.GONE) ;
            sPassword.setVisibility(View.GONE) ;
            sConfirmPassword.setVisibility(View.GONE) ;
            sPhoneNumber.setVisibility(View.GONE) ;
            sSignUp.setVisibility(View.GONE) ;
            sOTP.setVisibility(View.VISIBLE) ;
            sVerifyOTP.setVisibility(View.VISIBLE) ;
            sNewEmail.setVisibility(View.GONE) ;
            sUpdateNewEmail.setVisibility(View.GONE) ;
            sResendOTP.setVisibility(View.VISIBLE) ;
            sChangeEmail.setVisibility(View.VISIBLE) ;

        }

    }

    public void sVerifyOTP(View view) {
        email = "";
        password = "";
        cpassword = "";
        number = "";
        otp = sOTP.getText().toString();

        if(!otp.isEmpty()){
            sEmail.setVisibility(View.GONE) ;
            sPassword.setVisibility(View.GONE) ;
            sConfirmPassword.setVisibility(View.GONE) ;
            sPhoneNumber.setVisibility(View.GONE) ;
            sSignUp.setVisibility(View.GONE) ;
            sOTP.setVisibility(View.VISIBLE) ;
            sVerifyOTP.setVisibility(View.VISIBLE) ;
            sNewEmail.setVisibility(View.GONE) ;
            sUpdateNewEmail.setVisibility(View.GONE) ;
            sResendOTP.setVisibility(View.GONE) ;
            sChangeEmail.setVisibility(View.GONE) ;
        }else{
            StyleableToast.makeText(context,"Enter OTP. If not get OTP via mail, Resend again.", Toast.LENGTH_LONG,R.style.toaststyle_info).show();
        }
    }

    public void sUpdateNewEmail(View view) {
        email = sNewEmail.getText().toString();
        password = "";
        cpassword = "";
        number = "";
        otp = "";

        create_OTP = UNIC_OTP(6);

        if(!email.isEmpty()){
            sEmail.setVisibility(View.GONE) ;
            sPassword.setVisibility(View.GONE) ;
            sConfirmPassword.setVisibility(View.GONE) ;
            sPhoneNumber.setVisibility(View.GONE) ;
            sSignUp.setVisibility(View.GONE) ;
            sOTP.setVisibility(View.VISIBLE) ;
            sVerifyOTP.setVisibility(View.VISIBLE) ;
            sNewEmail.setVisibility(View.GONE) ;
            sUpdateNewEmail.setVisibility(View.GONE) ;
            sResendOTP.setVisibility(View.VISIBLE) ;
            sChangeEmail.setVisibility(View.VISIBLE) ;
        }else{
            StyleableToast.makeText(context,"Enter New Email Id", Toast.LENGTH_LONG,R.style.toaststyle_info).show();
        }
    }

    public void sResendOTP(View view) {
    }

    public void sChangeEmail(View view) {
        email = "";
        password = "";
        cpassword = "";
        number = "";
        otp = "";

        sNewEmail.setText("");

        sEmail.setVisibility(View.GONE) ;
        sPassword.setVisibility(View.GONE) ;
        sConfirmPassword.setVisibility(View.GONE) ;
        sPhoneNumber.setVisibility(View.GONE) ;
        sSignUp.setVisibility(View.GONE) ;
        sOTP.setVisibility(View.GONE) ;
        sVerifyOTP.setVisibility(View.GONE) ;
        sNewEmail.setVisibility(View.VISIBLE) ;
        sUpdateNewEmail.setVisibility(View.VISIBLE) ;
        sResendOTP.setVisibility(View.GONE) ;
        sChangeEmail.setVisibility(View.GONE) ;
    }

    public void sLogIn(View view) {
        logInPanel.setVisibility(View.VISIBLE) ;
        signUpPanel.setVisibility(View.GONE) ;
        forgetPasswordPanel.setVisibility(View.GONE) ;


        lUserName.setVisibility(View.VISIBLE) ;
        lPassword.setVisibility(View.VISIBLE) ;
        lLogIn.setVisibility(View.VISIBLE) ;
        lOTP.setVisibility(View.GONE) ;
        lVerifyOTP.setVisibility(View.GONE) ;
        lResendOTP.setVisibility(View.GONE) ;
        lForgetPassword.setVisibility(View.VISIBLE) ;


        fUserName.setVisibility(View.VISIBLE) ;
        fSendOTP.setVisibility(View.VISIBLE) ;
        fOTP.setVisibility(View.GONE) ;
        fVerifyOTP.setVisibility(View.GONE) ;
        fResendOTP.setVisibility(View.GONE) ;
        fChangeUsername.setVisibility(View.GONE) ;
        fPassword.setVisibility(View.GONE) ;
        fConfirmPassword.setVisibility(View.GONE) ;
        fSetPassword.setVisibility(View.GONE) ;

        sEmail.setVisibility(View.VISIBLE) ;
        sPassword.setVisibility(View.VISIBLE) ;
        sConfirmPassword.setVisibility(View.VISIBLE) ;
        sPhoneNumber.setVisibility(View.VISIBLE) ;
        sSignUp.setVisibility(View.VISIBLE) ;
        sOTP.setVisibility(View.GONE) ;
        sVerifyOTP.setVisibility(View.GONE) ;
        sNewEmail.setVisibility(View.GONE) ;
        sUpdateNewEmail.setVisibility(View.GONE) ;
        sResendOTP.setVisibility(View.GONE) ;
        sChangeEmail.setVisibility(View.GONE) ;
    }


}