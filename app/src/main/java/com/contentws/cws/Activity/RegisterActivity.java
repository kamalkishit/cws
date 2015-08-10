package com.contentws.cws.Activity;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.Header;

import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import com.contentws.cws.R;

public class RegisterActivity extends Activity {

    ProgressDialog progressDialog;
    TextView errorMsg;
    EditText usernameET;
    EditText passwordET;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        errorMsg = (TextView) findViewById(R.id.register_error);
        usernameET = (EditText) findViewById(R.id.registerName);
        passwordET = (EditText) findViewById(R.id.registerPassword);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
    }

    public void registerUser(View view) {
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);

        invokeWS(params);
    }

    public void invokeWS(RequestParams params) {
        progressDialog.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://contentws.com/signup", params, new AsyncHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                progressDialog.hide();
                try {
                    JSONObject object = new JSONObject(new String(response));

                    Toast.makeText(getApplicationContext(), object.getString("msg"), Toast.LENGTH_LONG).show();
                    setDefaultValues();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error occured", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, byte[] response, Throwable error) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void navigatetoLoginActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void setDefaultValues() {
        usernameET.setText("");
        passwordET.setText("");
    }
}
