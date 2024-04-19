package com.example.taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TaskDatabase extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "tasks";

    private static final String DB_NAME = "TaskDatabase.db";

    public TaskDatabase(Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sqlDB = "CREATE TABLE tasks (id TEXT PRIMARY KEY, title TEXT, description TEXT, dueDate TEXT)";
        db.execSQL(sqlDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Boolean addTask(Task task)
    {
        SQLiteDatabase sql_DB = getWritableDatabase();
        ContentValues cal = new ContentValues();
        cal.put("id", task.getId());
        cal.put("title", task.getTitle());
        cal.put("description", task.getDescription());
        cal.put("dueDate", task.getDueDateString());

        long rowId = sql_DB.insert(TABLE_NAME, null, cal);
        sql_DB.close();

        if (rowId > -1)
        {
            System.out.println("Task Added" + rowId);
            return true;
        }
        else
        {
            System.out.println("Insert Failed | Error");
            return false;
        }
    }

    public Boolean updateTask(Task task)
    {
        SQLiteDatabase sql_DB = getWritableDatabase();
        ContentValues cal = new ContentValues();
        cal.put("title", task.getTitle());
        cal.put("description", task.getDescription());
        cal.put("dueDate", task.getDueDateString());

        long rowId = sql_DB.update(TABLE_NAME, cal, "id=?", new String[]{task.getId()});
        sql_DB.close();

        if (rowId > -1)
        {
            System.out.println("Task Updated" + rowId);
            return true;
        }
        else
        {
            System.out.println("Update Failed | Error");
            return false;
        }
    }

    public Boolean deleteTask(String id) {
        SQLiteDatabase sql_DB = getWritableDatabase();
        long rowId = sql_DB.delete(TABLE_NAME, "id=?", new String[]{id});
        sql_DB.close();

        if (rowId > -1)
        {
            System.out.println("Task Deleted" + rowId);
            return true;
        }
        else
        {
            System.out.println("Delete Failed | Error");
            return false;
        }
    }

    public Task getTask(String id)
    {
        SQLiteDatabase sql_DB = this.getReadableDatabase();
        Cursor query = sql_DB.query(TABLE_NAME, new String[] {"id", "title", "description", "dueDate"},
                "id=?", new String[]{id}, null, null, null, null);
        if (query != null)
        {
            query.moveToFirst();
        }
        Task task = new Task(query.getString(0), query.getString(1), query.getString(2), query.getString(3));
        query.close();
        sql_DB.close();
        return task;
    }
    public List<Task> getAllTask()
    {
        SQLiteDatabase sql_DB = this.getReadableDatabase();
        Cursor query = sql_DB.query(TABLE_NAME, null, null, null, null, null, null);
        List<Task> result = new ArrayList<>();

        while(query.moveToNext())
        {
            result.add(new Task(query.getString(0), query.getString(1), query.getString(2), query.getString(3) ));
        }
        query.close();
        sql_DB.close();
        return result;
    }
}
