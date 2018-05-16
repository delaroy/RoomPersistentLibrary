package com.delaroystudios.roomdatabaseview.database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface WordDao {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * from word_table ORDER BY firstname ASC")
    LiveData<List<Word>> getAlphabetizedWords();

    @Insert
    void insert(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Word word);

    @Delete
    void deleteTask(Word word);

    @Query("SELECT * FROM word_table WHERE id = :id")
    LiveData<Word> loadTaskById(int id);
}
