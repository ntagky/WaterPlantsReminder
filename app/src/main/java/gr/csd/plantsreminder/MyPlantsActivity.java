package gr.csd.plantsreminder;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyPlantsActivity extends AppCompatActivity{

    private boolean doubleBackToExitPressedOnce = false;
    private SQLiteDatabase sqLiteDatabase;
    private PlantAdapter plantAdapter;
    private RecyclerView recyclerView;
    private SharedPreferences.Editor editor;
    private int sortIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myplants);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
        sortIndex = sharedPreferences.getInt("sort", 0);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        plantAdapter = new PlantAdapter(this, getItems());
        recyclerView.setAdapter(plantAdapter);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPlantsActivity.this, AddPlantActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (getPlantCount()==0)
            findViewById(R.id.noPlantTextView).setVisibility(View.VISIBLE);

    }

    private Cursor getItems(){
        switch (sortIndex){
            case 0:
                return  sqLiteDatabase.query(
                        PlantsContract.PlantEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        PlantsContract.PlantEntry.COLUMN_WATERING_DIFFERENCE + " ASC"
                );
            case 1:
                return  sqLiteDatabase.query(
                        PlantsContract.PlantEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        PlantsContract.PlantEntry.COLUMN_LAST_TIMESTAMP + " ASC"
                );
            case 2:
                return  sqLiteDatabase.query(
                        PlantsContract.PlantEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        PlantsContract.PlantEntry.COLUMN_LAST_TIMESTAMP + " DESC"
                );
            case 3:
                return  sqLiteDatabase.query(
                        PlantsContract.PlantEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        PlantsContract.PlantEntry.COLUMN_NAME + " ASC"
                );
            case 4:
                return  sqLiteDatabase.query(
                        PlantsContract.PlantEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        PlantsContract.PlantEntry.COLUMN_NAME + " DESC"
                );
        }
        return null;
    }

    public long getPlantCount() {
        return DatabaseUtils.queryNumEntries(sqLiteDatabase, PlantsContract.PlantEntry.TABLE_NAME);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                plantAdapter.getFilter().filter(s);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sort) {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_sort);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.findViewById(R.id.closeImageView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.findViewById(R.id.needsWaterTextView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sortIndex = 0;
                    editor.putInt("sort", sortIndex);
                    editor.apply();
                    plantAdapter = new PlantAdapter(MyPlantsActivity.this, getItems());
                    recyclerView.setAdapter(plantAdapter);
                    dialog.dismiss();
                }
            });

            dialog.findViewById(R.id.mostRecentTextView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sortIndex = 1;
                    editor.putInt("sort", sortIndex);
                    editor.apply();
                    plantAdapter = new PlantAdapter(MyPlantsActivity.this, getItems());
                    recyclerView.setAdapter(plantAdapter);
                    dialog.dismiss();
                }
            });

            dialog.findViewById(R.id.leastRecentTextView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sortIndex = 2;
                    editor.putInt("sort", sortIndex);
                    editor.apply();
                    plantAdapter = new PlantAdapter(MyPlantsActivity.this, getItems());
                    recyclerView.setAdapter(plantAdapter);
                    dialog.dismiss();
                }
            });

            dialog.findViewById(R.id.nameAscendTextView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sortIndex = 3;
                    editor.putInt("sort", sortIndex);
                    editor.apply();
                    plantAdapter = new PlantAdapter(MyPlantsActivity.this, getItems());
                    recyclerView.setAdapter(plantAdapter);
                    dialog.dismiss();
                }
            });

            dialog.findViewById(R.id.nameDescentTextView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sortIndex = 4;
                    editor.putInt("sort", sortIndex);
                    editor.apply();
                    plantAdapter = new PlantAdapter(MyPlantsActivity.this, getItems());
                    recyclerView.setAdapter(plantAdapter);
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click back again to exit.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}