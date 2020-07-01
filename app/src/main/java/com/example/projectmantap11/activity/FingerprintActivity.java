package com.example.projectmantap11.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.projectmantap11.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FingerprintActivity extends AppCompatActivity {

    private String BIOMETRIC_TAG = "BIOMETRICTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);


        //Create a thread pool with a single thread//

        Executor newExecutor = Executors.newSingleThreadExecutor();

        FragmentActivity activity = this;

        //Start listening for authentication events//

        final BiometricPrompt myBiometricPrompt = new BiometricPrompt(activity, newExecutor
                , new BiometricPrompt.AuthenticationCallback() {

            @Override
            //onAuthenticationError is called when a fatal error occurrs//
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                } else {

                    //Print a message to Logcat//
                    Log.d(BIOMETRIC_TAG, "An unrecoverable error occurred");
                }
            }

            //onAuthenticationSucceeded is called when a fingerprint is matched successfully//
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                //Print a message to Logcat//
                Log.d(BIOMETRIC_TAG, "Fingerprint recognised successfully");
            }


            //onAuthenticationFailed is called when the fingerprint doesn’t match//
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

                //Print a message to Logcat//
                Log.d(BIOMETRIC_TAG, "Fingerprint not recognised");
            }
        });

        //Create the BiometricPrompt instance//
        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                //Add some text to the dialog//
                .setTitle("Title text goes here")
                .setSubtitle("Subtitle goes here")
                .setDescription("This is the description")
                .setNegativeButtonText("Cancel")
                //Build the dialog//
                .build();

        /* USE THIS IF YOU NEED A BUTTON

        findViewById(R.id.launchAuthentication).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

         */

        //Assign an onClickListener to the app’s “Authentication” button//
        myBiometricPrompt.authenticate(promptInfo);
    }
}