package com.example.joshuansu.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.joshuansu.myapplication.adapter.DetailActivity;
import com.example.joshuansu.myapplication.adapter.ToDoListAdapter;
import com.example.joshuansu.myapplication.components.note.NoteService;
import com.example.joshuansu.myapplication.components.note.get.note.GetNote;
import com.example.joshuansu.myapplication.remote.APIUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ToDoListAdapter adapter;
    private NoteService noteService;

    @BindView(R.id.todo_list)
    RecyclerView toDoRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton addFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteService = APIUtils.getNoteService();
        adapter = new ToDoListAdapter();

        ButterKnife.bind(this);

        toDoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        toDoRecyclerView.setAdapter(adapter);

        addFab.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            view.getContext().startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        Call<GetNote> call = noteService.getAll();
        call.enqueue(new Callback<GetNote>() {
            @Override
            public void onResponse(Call<GetNote> call, Response<GetNote> response) {
                if (response.isSuccessful()) {
                    adapter.updateToDos(response.body().getResponse());
                    System.out.println(response.body().getCount());
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(MainActivity.this, jObjError.getString("error"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetNote> call, Throwable t) {
                Log.e("ERROR ", t.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
