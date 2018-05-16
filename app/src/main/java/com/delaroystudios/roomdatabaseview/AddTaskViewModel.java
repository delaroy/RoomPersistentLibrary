package com.delaroystudios.roomdatabaseview;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.delaroystudios.roomdatabaseview.database.Word;
import com.delaroystudios.roomdatabaseview.database.WordRoomDatabase;


public class AddTaskViewModel extends ViewModel {

    // COMPLETED (6) Add a task member variable for the TaskEntry object wrapped in a LiveData
    private LiveData<Word> task;

    // COMPLETED (8) Create a constructor where you call loadTaskById of the taskDao to initialize the tasks variable
    // Note: The constructor should receive the database and the taskId
    public AddTaskViewModel(WordRoomDatabase database, int taskId) {
        task = database.wordDao().loadTaskById(taskId);
    }

    // COMPLETED (7) Create a getter for the task variable
    public LiveData<Word> getTask() {
        return task;
    }
}
