package com.delaroystudios.roomdatabaseview;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.delaroystudios.roomdatabaseview.database.WordRoomDatabase;


public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final WordRoomDatabase mDb;
    private final int mTaskId;

    public AddTaskViewModelFactory(WordRoomDatabase database, int taskId) {
        mDb = database;
        mTaskId = taskId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddTaskViewModel(mDb, mTaskId);
    }
}
