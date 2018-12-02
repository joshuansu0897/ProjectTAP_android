package com.example.joshuansu.myapplication.components.note;

import com.example.joshuansu.myapplication.components.note.get.note.GetNote;
import com.example.joshuansu.myapplication.components.utils.Msg;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NoteService {

    @POST("note")
    Call<Note> create(@Body Note note);

    @GET("note")
    Call<GetNote> getAll();

    @GET("note")
    Call<GetNote> getAll(@Query("offset") int offset, @Query("limit") int limit);

    @GET("note/{id}")
    Call<Note> get(@Path("id") int id);

    @DELETE("note/{id}")
    Call<Msg> delete(@Path("id") int id);

    @PUT("note/{id}")
    Call<Note> update(@Path("id") int id, @Body Note note);

}
