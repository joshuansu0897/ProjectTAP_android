package com.example.joshuansu.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joshuansu.myapplication.R;
import com.example.joshuansu.myapplication.components.note.Note;
import com.example.joshuansu.myapplication.components.note.NoteService;
import com.example.joshuansu.myapplication.remote.APIUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_TODO_KEY = "Note";

    @BindView(R.id.title_field)
    EditText titleEditTextView;
    @BindView(R.id.content_field)
    EditText contentEditTextView;
    @BindView(R.id.edit_fab)
    FloatingActionButton editFab;
    @BindView(R.id.save_fab)
    FloatingActionButton saveFab;
    @BindView(R.id.detail_date)
    TextView dateTextView;

    private Note note;
    private NoteService noteService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        noteService = APIUtils.getNoteService();

        ButterKnife.bind(this);

        setSaveListener();
        setEditListener();
        populateToDoObject();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        note = (Note) data.getSerializableExtra(EXTRA_TODO_KEY);
        displayDetail();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setSaveListener() {
        saveFab.setOnClickListener(view -> {
            saveData();
            Snackbar.make(editFab, "Data Saved", Snackbar.LENGTH_SHORT).show();
            enableToDoViewEdition(false);
        });
    }

    private void setEditListener() {
        editFab.setOnClickListener(view -> enableToDoViewEdition(true));
    }

    @SuppressLint("RestrictedApi")
    private void enableToDoViewEdition(boolean isEditClicking) {
        titleEditTextView.setEnabled(isEditClicking);
        contentEditTextView.setEnabled(isEditClicking);
        editFab.setVisibility(isEditClicking ? View.INVISIBLE : View.VISIBLE);
        saveFab.setVisibility(isEditClicking ? View.VISIBLE : View.INVISIBLE);
    }

    private void saveData() {
        note.setTitle(titleEditTextView.getText().toString());
        note.setContent(contentEditTextView.getText().toString());
        if (note.getId() == -1) {
            Call<Note> call = noteService.create(note);
            call.enqueue(new Callback<Note>() {
                @Override
                public void onResponse(Call<Note> call, Response<Note> response) {
                    if (!response.isSuccessful()) {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(DetailActivity.this, jObjError.getString("error"), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(DetailActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Note> call, Throwable t) {
                    Log.e("ERROR ", t.getMessage());
                }
            });
        } else {
            Call<Note> call = noteService.update(note.getId(), note);
            call.enqueue(new Callback<Note>() {
                @Override
                public void onResponse(Call<Note> call, Response<Note> response) {
                    if (!response.isSuccessful()) {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(DetailActivity.this, jObjError.getString("error"), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(DetailActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Note> call, Throwable t) {
                    Log.e("ERROR ", t.getMessage());
                }
            });
        }
    }

    private void populateToDoObject() {
        note = getTodo();
        displayDetail();
    }

    private void displayDetail() {
        titleEditTextView.setText(note.getTitle());
        contentEditTextView.setText(note.getContent());
        if (note.getUpdatedAt() != null) {
            dateTextView.setText(new SimpleDateFormat("HH:mm:ss dd/MM/yy").format(note.getUpdatedAt()));
        }
    }

    private Note getTodo() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(EXTRA_TODO_KEY)) {
            enableToDoViewEdition(false);
            return (Note) extras.getSerializable(EXTRA_TODO_KEY);
        }
        enableToDoViewEdition(true);
        return new Note();
    }

}
