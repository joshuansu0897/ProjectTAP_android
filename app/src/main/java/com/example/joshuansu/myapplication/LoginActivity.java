package com.example.joshuansu.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joshuansu.myapplication.components.user.User;
import com.example.joshuansu.myapplication.components.user.UserService;
import com.example.joshuansu.myapplication.components.utils.Msg;
import com.example.joshuansu.myapplication.components.utils.Token;
import com.example.joshuansu.myapplication.remote.APIUtils;

import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.username) TextView username;
    @BindView(R.id.password) TextView password;

    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        userService = APIUtils.getUserService();
    }


    public void signUp(View view){
        User u = new User();
        u.setUsername(username.getText().toString());
        u.setPassword(password.getText().toString());

        Call<Msg> call = userService.signup(u);
        call.enqueue(new Callback<Msg>() {
            @Override
            public void onResponse(Call<Msg> call, Response<Msg> response) {
                if(response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(LoginActivity.this, jObjError.getString("error"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Msg> call, Throwable t) {
                Log.e("ERROR ", t.getMessage());
            }
        });
    }

    public void signIn(View view){
        User u = new User();
        u.setUsername(username.getText().toString());
        u.setPassword(password.getText().toString());

        Call<Token> call = userService.signin(u);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()){
                    APIUtils.setToken(response.body().getToken());

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(LoginActivity.this, jObjError.getString("error"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e("ERROR ", t.getMessage());
            }
        });
    }

}
