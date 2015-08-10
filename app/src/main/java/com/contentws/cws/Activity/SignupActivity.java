package com.contentws.cws.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.contentws.cws.Config;
import com.contentws.cws.HttpResponseCallback;
import com.contentws.cws.HttpUtil;
import com.contentws.cws.R;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private EditText email;
    private EditText password;
    private Button signupButton;
    private TextView loginLink;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signupButton = (Button) findViewById(R.id.signup_button);
        loginLink = (TextView) findViewById(R.id.login_link);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signup() {
        if (!validate()) {
            signupFailure();
            return;
        }

        signupButton.setEnabled(false);

        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        HttpUtil asyncHttpWrapper = HttpUtil.getInstance();
        asyncHttpWrapper.signup(createUrl(), createParams(), new HttpResponseCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("success");
                signupSuccess();
            }

            @Override
            public void onFailure(String errorMsg) {
                System.out.println("failure");
                signupFailure();
            }
        });
    }

    private String createUrl() {
        String url = Config.SERVER_URL + "/signup";
        return url;
    }

    private Map createParams() {
        Map params = new HashMap<String, String>();
        params.put("emailId", email.getText().toString());
        params.put("password", password.getText().toString());
        return params;
    }

    public void signupSuccess() {
        progressDialog.dismiss();
        signupButton.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Signup success", Toast.LENGTH_LONG).show();
        navigateToLoginActivity();
    }

    public void signupFailure() {
        progressDialog.dismiss();
        signupButton.setEnabled(true);
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();

        if (emailStr.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (passwordStr.isEmpty() || passwordStr.length() < 4 || passwordStr.length() > 10) {
            password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }

    public void navigateToLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}