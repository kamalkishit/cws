package com.contentws.cws.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.contentws.cws.Config;
import com.contentws.cws.Data.User;
import com.contentws.cws.HttpResponseCallback;
import com.contentws.cws.HttpUtil;
import com.contentws.cws.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0; // ??
    private EditText email;
    private EditText password;
    private Button loginButton;
    private TextView signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login_button);
        signupLink = (TextView) findViewById(R.id.signup_link);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        if (!validate()) {
            loginFailure();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        HttpUtil httpUtil = HttpUtil.getInstance();
        httpUtil.login(createUrl(), createParams(), new HttpResponseCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("success");
                loginSuccess(response);
            }

            @Override
            public void onFailure(String errorMsg) {
                System.out.println("failure");
                loginFailure();
            }
        });
    }

    private String createUrl() {
        String url = Config.SERVER_URL + "/login";
        return url;
    }

    private Map createParams() {
        Map params = new HashMap<String, String>();
        params.put("emailId", email.getText().toString());
        params.put("password", password.getText().toString());
        return params;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void loginSuccess(String response) {
        loginButton.setEnabled(true);
        try {
            JSONObject jsonObject = new JSONObject(response);
            User user = User.getInstance();
            user.updateUser(jsonObject);
            navigatetoMainActivity();
        } catch (JSONException jsonException) {
            Toast.makeText(getApplicationContext(), "Error occured", Toast.LENGTH_LONG).show();
            jsonException.printStackTrace();
        }
    }

    public void loginFailure() {
        loginButton.setEnabled(true);
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();

        if (emailStr.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (passwordStr.isEmpty() || passwordStr.length() < 4 || password.length() > 10) {
            password.setError("between 4 and 10 characters");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }

    public void navigatetoMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

