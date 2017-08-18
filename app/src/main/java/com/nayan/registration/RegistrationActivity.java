package com.nayan.registration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegistrationActivity extends AppCompatActivity {
    private String regisApi = "http://localhost/mytest/index.php/test2/regist_api";
    private EditText edtUser, edtPass, edtConfirmpass, edtEmail;
    private String user, pass, conpass, email;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
    }

    private void init() {
        edtUser = (EditText) findViewById(R.id.edtUsername);
        edtPass = (EditText) findViewById(R.id.edtPass);
        edtConfirmpass = (EditText) findViewById(R.id.edtConfirmPass);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        button = (Button) findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = edtUser.getText().toString().trim();
                pass = edtPass.getText().toString().trim();
                conpass = edtConfirmpass.getText().toString().trim();
                email = edtEmail.getText().toString().trim();
                if (user.isEmpty() && pass.isEmpty() && conpass.isEmpty() && email.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "any filed is empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.equals(conpass)) {
//                        Toast.makeText(RegistrationActivity.this, "success", Toast.LENGTH_SHORT).show();
                        calApi();
                    } else {
                        Toast.makeText(RegistrationActivity.this, "pass did not match", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


    private void calApi() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        JSONObject object = new JSONObject();
        try {
            object.put("username", user);
            object.put("password", pass);
            object.put("confirmpassword", conpass);
            object.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("reg", object.toString());
        client.post(regisApi, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (response.has("status")) {
                    try {
                        int userId = response.getInt("user_id");
                        if (userId > 0) {
                            Toast.makeText(RegistrationActivity.this, "status", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
