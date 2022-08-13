package com.thebengalstudio.authdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.thebengalstudio.mylibrary1.SignUpProcess.LogIn;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Auth01 extends AppCompatActivity {

    Context context = Auth01.this;
    Activity activity = Auth01.this;

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;

    private FirebaseAuth mAuth;

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private ViewGroup mPhoneNumberViews;

    private TextView mTextViewProfile;

    private EditText mPhoneNumberField;
    private EditText mVerificationField;

    private Button mStartButton;
    private Button mVerifyButton;
    private Button mResendButton;
    private Button mSignOutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth01);


        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        mTextViewProfile = findViewById(R.id.profile);
        mPhoneNumberViews = findViewById(R.id.phone_auth_fields);

        mPhoneNumberField = findViewById(R.id.field_phone_number);
        mVerificationField = findViewById(R.id.field_verification_code);

        mStartButton = findViewById(R.id.button_start_verification);
        mVerifyButton = findViewById(R.id.button_verify_phone);
        mResendButton = findViewById(R.id.button_resend);
        mSignOutButton = findViewById(R.id.sign_out_button);



        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!validatePhoneNumber()) {
//                    return;
//                }
//                startPhoneNumberVerification(mPhoneNumberField.getText().toString());

                Intent intent = new Intent(context, LogIn.class);
                startActivity(intent);
            }
        });

        mVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String code = mVerificationField.getText().toString();
//                if (TextUtils.isEmpty(code)) {
//                    mVerificationField.setError("Cannot be empty.");
//                    return;
//                }
//                verifyPhoneNumberWithCode(mVerificationId, code);



                StringRequest get_card_details = new StringRequest(Request.Method.POST, "https://thebengalstudio.com",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "(CharSequence) error", Toast.LENGTH_SHORT).show();
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null) {
                            Log.e("Status code", String.valueOf(networkResponse.statusCode));
//                            Toast.makeText(MetroCardFillUp.this, "Error code: "+ String.valueOf(networkResponse.statusCode), Toast.LENGTH_SHORT).show();
//                            if(get_card_details_ads_con == 0){
//                                call_metro_in_video_Listener();
//                                get_card_details_ads_con++;
//                            }else if(get_card_details_ads_con < 4){
//                                get_card_details_ads_con++;
//                            }else{
//                                call_metro_in_video_Listener();
//                                get_card_details_ads_con=0;
//                            }
//                            alertDialog_for_open_browser();
                        }else{
//                            Toast.makeText(MetroCardFillUp.this, "No Internet or Wrong Link", Toast.LENGTH_SHORT).show();

                        }
                    }
                }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> params = new HashMap<String,String>();

//                        params.put("reCardNumber",str_cardnumber);
//                        params.put("cardNumber",str_cardnumber);


                        return params;
                    }
                };

                RequestQueue requestQueue_get_card_details = Volley.newRequestQueue(context);
                requestQueue_get_card_details.add(get_card_details);



            }
        });
        mResendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendVerificationCode(mPhoneNumberField.getText().toString(), mResendToken);
            }
        });
        mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });


        mAuth = FirebaseAuth.getInstance();

        // Initialize phone auth callbacks
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without user action.
                mTextViewProfile.setText("onVerificationCompleted: " + credential);
                mVerificationInProgress = false;

                updateUI(STATE_VERIFY_SUCCESS, credential);
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                mTextViewProfile.setText("onVerificationFailed: " + e.getMessage());
                mVerificationInProgress = false;

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    mPhoneNumberField.setError("Invalid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(findViewById(android.R.id.content), "The SMS quota for the project has been exceeded", Snackbar.LENGTH_SHORT).show();
                }
                updateUI(STATE_VERIFY_FAILED);
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                mTextViewProfile.setText("onCodeSent: " + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                updateUI(STATE_CODE_SENT);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(mPhoneNumberField.getText().toString());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,              // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,       // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,              // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,       // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = task.getResult().getUser();
                    updateUI(STATE_SIGNIN_SUCCESS, user);
                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        mVerificationField.setError(task.getException().getMessage());
                    }
                    updateUI(STATE_SIGNIN_FAILED);
                }
            }
        });
    }

    private void signOut() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("logout");
        alert.setCancelable(false);
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAuth.signOut();
                updateUI(STATE_INITIALIZED);
            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                enableViews(mStartButton, mPhoneNumberField);
//				disableViews(mVerifyButton, mResendButton, mVerificationField);
                mTextViewProfile.setText(null);
                break;
            case STATE_CODE_SENT:
                enableViews(mVerifyButton, mResendButton, mPhoneNumberField, mVerificationField);
//				disableViews(mStartButton);
                break;
            case STATE_VERIFY_FAILED:
                enableViews(mStartButton, mVerifyButton, mResendButton, mPhoneNumberField, mVerificationField);
                break;
            case STATE_VERIFY_SUCCESS:
//				disableViews(mStartButton, mVerifyButton, mResendButton, mPhoneNumberField, mVerificationField);
                mTextViewProfile.append("\n" + "status_verification_succeeded");

                // Set the verification text based on the credential
                if (cred != null) {
                    if (cred.getSmsCode() != null) {
                        mVerificationField.setText(cred.getSmsCode());
                    } else {
                        mVerificationField.setText("instant_validation");
                    }
                }

                break;
            case STATE_SIGNIN_FAILED:
                mTextViewProfile.setText("status_sign_in_failed");
                break;
            case STATE_SIGNIN_SUCCESS:
                // Np-op, handled by sign-in check
                break;
        }

        if (user == null) {
            mPhoneNumberViews.setVisibility(View.VISIBLE);
            mSignOutButton.setVisibility(View.GONE);
        } else {
            mPhoneNumberViews.setVisibility(View.GONE);
            mSignOutButton.setVisibility(View.VISIBLE);

            enableViews(mPhoneNumberField, mVerificationField);
            mPhoneNumberField.setText(null);
            mVerificationField.setText(null);

            mTextViewProfile.append(
                    "\n\n" + "firebase_status_fmt" + user.getUid()
                            + "\nDisplay: " + user.getDisplayName()
                            + "\nEmail: " + user.getEmail()
                            + "\nPhoneNumber: " + user.getPhoneNumber()
                            + "\nPhotoUrl: " + user.getPhotoUrl()
                            + "\nProviderId: " + user.getProviderId()
            );
        }
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = mPhoneNumberField.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberField.setError("Invalid phone number.");
            return false;
        }
        return true;
    }

    private void enableViews(View... views) {
        for (View v : views) {
            v.setEnabled(true);
        }
    }

    private void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.button_start_verification:
//
//                break;
//            case R.id.button_verify_phone:
//
//                break;
//            case R.id.button_resend:
//
//                break;
//            case R.id.sign_out_button:
//
//                break;
//        }
//    }

}