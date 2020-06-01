package gr.csd.plantsreminder;

import android.content.Context;
import gr.csd.plantsreminder.PlantsContract.*;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PlantsDatabase.db";
    private static final int DATABASE_VERSION = 1;

    DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_PLANT_TABLE = "CREATE TABLE " +
                PlantEntry.TABLE_NAME + " (" +
                PlantEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PlantEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                PlantEntry.COLUMN_TYPE + " TEXT NOT NULL, " +
                PlantEntry.COLUMN_WATERING + " INTEGER NOT NULL, " +
                PlantEntry.COLUMN_FERTILIZER + " INTEGER NOT NULL, " +
                PlantEntry.COLUMN_PRUNING + " INTEGER NOT NULL, " +
                PlantEntry.COLUMN_LAST_TIMESTAMP + " TEXT NOT NULL," +
                PlantEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                PlantEntry.COLUMN_WATERING_DIFFERENCE + " FLOAT NOT NULL" +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_PLANT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int newVersion, int oldVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PlantEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
