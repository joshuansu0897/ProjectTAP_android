package com.example.joshuansu.myapplication.remote;

import com.example.joshuansu.myapplication.components.note.NoteService;
import com.example.joshuansu.myapplication.components.user.UserService;

public class APIUtils {

    private static final String API_URL = "http://138.197.208.68/";
    private APIUtils(){
    }

    public static UserService getUserService(){
        return RetrofitClient.getClient(API_URL).create(UserService.class);
    }

    public static NoteService getNoteService(){
        return RetrofitClient.getClient(API_URL).create(NoteService.class);
    }

    public static void setToken(String token){
        RetrofitClient.setToken(API_URL, token);

    }


}