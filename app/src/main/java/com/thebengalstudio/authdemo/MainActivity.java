package com.thebengalstudio.authdemo;

import static com.thebengalstudio.mylibrary1.Service.LogInCheck.LogInCheck;
import static com.thebengalstudio.mylibrary1.Service.StoreValue.app_uid;
import static com.thebengalstudio.mylibrary1.Service.StoreValue.app_passcode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.thebengalstudio.mylibrary1.SignUpProcess.LogIn;

public class MainActivity extends AppCompatActivity {

    Context context = MainActivity.this;
    Activity activity = MainActivity.this;

    int loginReqCode = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!LogInCheck(context)){
            Intent intent = new Intent(context, LogIn.class);
            intent.putExtra("app_uid", "app_uid0123" );
            intent.putExtra("passcode", "app_passcode2352" );
            startActivityForResult(intent,loginReqCode); // Activity is started with requestCode 2
        }else {
            Toast.makeText(context, "sign in", Toast.LENGTH_SHORT).show();
        }


    }


    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {

            if (requestCode == loginReqCode) {


                String passcode = data.getStringExtra("passcode");
                String tbs_uid = data.getStringExtra("tbs_uid");
                String app_uid = data.getStringExtra("app_uid");
                String auth_uid = data.getStringExtra("auth_uid");
                String passapp_packge_namecode = data.getStringExtra("app_packge_name");
                String app_status = data.getStringExtra("app_status");
                String auth_password = data.getStringExtra("auth_password");
                String verify_password = data.getStringExtra("verify_password");

                String tooo = "passcode: "+passcode+"\ntbs_uid: "+tbs_uid+"app_uid: "+app_uid+"\nauth_uid: "+auth_uid+
                        "passapp_packge_namecode: "+passapp_packge_namecode+"\napp_status: "+app_status+
                        "auth_password: "+auth_password+"\nverify_password: "+verify_password;
                Toast.makeText(context, tooo, Toast.LENGTH_SHORT).show();


            }
        }



    }


}