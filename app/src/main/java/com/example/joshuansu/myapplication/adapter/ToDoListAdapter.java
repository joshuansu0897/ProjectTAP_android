package com.example.joshuansu.myapplication.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joshuansu.myapplication.R;
import com.example.joshuansu.myapplication.components.note.Note;
import com.example.joshuansu.myapplication.components.note.NoteService;
import com.example.joshuansu.myapplication.components.note.get.note.NoteMin;
import com.example.joshuansu.myapplication.remote.APIUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {

    private List<NoteMin> toDoList;
    private static NoteService noteService;

    public ToDoListAdapter() {
        toDoList = new ArrayList<>();
        noteService = APIUtils.getNoteService();
    }

    @NonNull
    @Override
    public ToDoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoListAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bindData(toDoList.get(position));
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public void updateToDos(List<NoteMin> todos) {
        toDoList.clear();
        toDoList.addAll(todos);
        notifyDataSetChanged();
    }

    public void addToDos(List<NoteMin> todos) {
        toDoList.addAll(todos);
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        NoteMin noteMin;
        @BindView(R.id.toDoTitle)
        TextView toDoTitleTextBox;
        @BindView(R.id.toDoDate)
        TextView toDoDateTextBox;
        @BindView(R.id.cardViewContainer)
        CardView container;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(NoteMin toDoItem) {
            noteMin = toDoItem;
            toDoTitleTextBox.setText(noteMin.getTitle());
            toDoDateTextBox.setText(noteMin.getUpdatedAt().toString());
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.cardViewContainer) {

                Call<Note> call = noteService.get(noteMin.getId());
                call.enqueue(new Callback<Note>() {
                    @Override
                    public void onResponse(Call<Note> call, Response<Note> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(v.getContext(), DetailActivity.class);
                            intent.putExtra(DetailActivity.EXTRA_TODO_KEY, response.body());
                            v.getContext().startActivity(intent);
                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(v.getContext(), jObjError.getString("error"), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
    }
}
