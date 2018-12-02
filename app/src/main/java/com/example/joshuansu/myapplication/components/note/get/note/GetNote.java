package com.example.joshuansu.myapplication.components.note.get.note;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetNote implements Serializable {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("response")
    @Expose
    private List<NoteMin> notes = null;

    public GetNote() {
    }

    public GetNote(int count, List<NoteMin> response) {
        this.count = count;
        this.notes = response;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<NoteMin> getResponse() {
        return notes;
    }

    public void setResponse(List<NoteMin> response) {
        this.notes = response;
    }

}
