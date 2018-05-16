package com.delaroystudios.roomdatabaseview.database;



import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A basic class representing an entity that is a row in a one-column database table.
 *
 * @ Entity - You must annotate the class as an entity and supply a table name if not class name.
 * @ PrimaryKey - You must identify the primary key.
 * @ ColumnInfo - You must supply the column name if it is different from the variable name.
 *
 * See the documentation for the full rich set of annotations.
 * https://developer.android.com/topic/libraries/architecture/room.html
 */

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "firstname")
    private String first;

    @Nullable
    @ColumnInfo(name = "lastname")
    private String last;

    @Nullable
    @ColumnInfo(name = "title")
    private String title;

    @Nullable
    @ColumnInfo(name = "department")
    private String department;

    @Ignore
    public Word(@NonNull String first, @Nullable String last, @Nullable String title, @Nullable String department) {
        this.first = first;
        this.last = last;
        this.title = title;
        this.department = department;
    }

    public Word(int id, @NonNull String first, @Nullable String last, @Nullable String title, @Nullable String department) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.title = title;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst(){return this.first;}

    public String getLast(){return this.last;}

    public String getTitle(){return this.title;}

    public String getDepartment(){return this.department;}
}