package com.delaroystudios.roomdatabaseview;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.delaroystudios.roomdatabaseview.database.Word;
import com.delaroystudios.roomdatabaseview.database.WordRoomDatabase;

/**
 * Activity for entering a word.
 */

public class NewWordActivity extends AppCompatActivity {

    public static final String FIRST_NAME = "first-name";
    public static final String LAST_NAME = "last-name";
    public static final String TITLE = "title";
    public static final String DEPARTMENT = "department";

    public static final String EXTRA_TASK_ID = "extraTaskId";

    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;
    // Constant for logging
    private static final String TAG = NewWordActivity.class.getSimpleName();
    private int mTaskId = DEFAULT_TASK_ID;
    private WordRoomDatabase mDb;
    private Button button;

    private  EditText edit_employee_firstname, edit_employee_lastname, edit_employee_title, edit_employee_department;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        edit_employee_firstname = findViewById(R.id.edit_employee_firstname);
        edit_employee_lastname = findViewById(R.id.edit_employee_lastname);
        edit_employee_title = findViewById(R.id.edit_employee_title);
        edit_employee_department = findViewById(R.id.edit_employee_department);
        button = findViewById(R.id.button_save);

        mDb = WordRoomDatabase.getDatabase(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            button.setText("UPDATE");
            if (mTaskId == DEFAULT_TASK_ID) {
                // populate the UI
                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);

                // COMPLETED (9) Remove the logging and the call to loadTaskById, this is done in the ViewModel now
                // COMPLETED (10) Declare a AddTaskViewModelFactory using mDb and mTaskId
                AddTaskViewModelFactory factory = new AddTaskViewModelFactory(mDb, mTaskId);
                // COMPLETED (11) Declare a AddTaskViewModel variable and initialize it by calling ViewModelProviders.of
                // for that use the factory created above AddTaskViewModel
                final AddTaskViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddTaskViewModel.class);

                // COMPLETED (12) Observe the LiveData object in the ViewModel. Use it also when removing the observer
                viewModel.getTask().observe(this, new Observer<Word>() {
                    @Override
                    public void onChanged(@Nullable Word taskEntry) {
                        viewModel.getTask().removeObserver(this);
                        populateUI(taskEntry);
                    }
                });
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                    onSaveButtonClicked();
            }
        });
    }

    private void populateUI(Word task) {
        if (task == null) {
            return;
        }

        edit_employee_firstname.setText(task.getFirst());
        edit_employee_lastname.setText(task.getLast());
        edit_employee_title.setText(task.getTitle());
        edit_employee_department.setText(task.getDepartment());
    }

    public void onSaveButtonClicked() {
        if (TextUtils.isEmpty(edit_employee_firstname.getText()) || TextUtils.isEmpty(edit_employee_lastname.getText())
                || TextUtils.isEmpty(edit_employee_title.getText()) || TextUtils.isEmpty(edit_employee_department.getText())) {
            Toast.makeText(getApplicationContext(), "please fill all fields", Toast.LENGTH_LONG).show();
        } else {
            String first_name = edit_employee_firstname.getText().toString();
            String last_name = edit_employee_lastname.getText().toString();
            String title = edit_employee_title.getText().toString();
            String department = edit_employee_department.getText().toString();

            final Word task = new Word(first_name, last_name, title, department);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if (mTaskId == DEFAULT_TASK_ID) {
                        // insert new task
                        mDb.wordDao().insert(task);
                    } else {
                        //update task
                        task.setId(mTaskId);
                        mDb.wordDao().updateTask(task);
                    }
                    finish();
                }
            });
        }
    }
}

