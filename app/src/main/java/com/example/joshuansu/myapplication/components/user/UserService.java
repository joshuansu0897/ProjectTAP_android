package com.example.joshuansu.myapplication.components.user;

import com.example.joshuansu.myapplication.components.utils.Msg;
import com.example.joshuansu.myapplication.components.utils.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("signup")
    Call<Msg> signup(@Body User user);

    @POST("signin")
    Call<Token> signin(@Body User user);

}
