package com.workoutplanner;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.workoutplanner.api.ApiResponse;
import com.workoutplanner.api.LoginSubmissionData;
import com.workoutplanner.api.interfaces.UsersAPI;
import com.workoutplanner.model.User;
import com.workoutplanner.service.JwtTokenProvider;
import com.workoutplanner.service.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {
    private final String LOG_TAG = this.getClass().getSimpleName();

    private RegistrationActivity.UserRegistrationTask mAuthTask = null;

    // UI references.
    private EditText mFullNameView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mRegistrationFormView;

    private UsersAPI usersAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mFullNameView = findViewById(R.id.fullName);
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);

        Button mEmailRegistrationButton = findViewById(R.id.email_registration_button);
        mEmailRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });

        mRegistrationFormView = findViewById(R.id.registration_form);
        mProgressView = findViewById(R.id.registration_progress);
        usersAPI = new ServiceGenerator(new JwtTokenProvider(this)).createService(UsersAPI.class);
    }


    private void attemptRegistration() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        String fullName = mFullNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt registration and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user registration attempt.
            showProgress(true);
            mAuthTask = new RegistrationActivity.UserRegistrationTask(email, fullName, password);
            mAuthTask.execute((Void) null);

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the registration form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegistrationFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Represents an asynchronous registration task used to authenticate
     * the user.
     */
    public class UserRegistrationTask extends AsyncTask<Void, Void, String> {

        private final String mEmail;
        private final String mPassword;
        private final String fullName;

        UserRegistrationTask(String email, String fullName, String password) {
            mEmail = email;
            mPassword = password;
            this.fullName = fullName;
        }

        @Override
        protected String doInBackground(Void... params) {
            User user = new User(mEmail, fullName, mPassword);

            Call<ApiResponse<Void>> registrationRequest = usersAPI.registerUser(user);
            try {
                Response<ApiResponse<Void>> response = registrationRequest.execute();
                if(response.isSuccessful()) {
                    Call<Void> loginRequest = usersAPI.loginUser(new LoginSubmissionData(mEmail, mPassword));
                    Response<Void> loginResponse = loginRequest.execute();
                    String refreshToken = response.headers().get("RefreshToken");
                    String token = loginResponse.headers().get("Authorization");

                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.jwt_token), token);
                    editor.putString(getString(R.string.refresh_token), refreshToken);
                    editor.commit();
                    return null;
                } else {
                    System.out.println(response.errorBody());
                    Gson gson = new Gson();
                    String responseDataString = response.errorBody().string();
                    ApiResponse<Void> responseData = gson.fromJson(responseDataString, ApiResponse.class);
                    if (responseData.getError().equals(ApiErrors.DUPLICATE_EMAIL)) {
                        return "Email already in use";
                    }
                    return "Unknown error";
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, e.getMessage());
                return "Unknown error";
            }
        }

        @Override
        protected void onPostExecute(final String error) {
            mAuthTask = null;
            showProgress(false);

            if (error == null) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            } else {
                mEmailView.setError(error);
                mEmailView.requestFocus();

            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
