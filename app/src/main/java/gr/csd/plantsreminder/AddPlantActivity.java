package gr.csd.plantsreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPlantActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button Need_Fertilizer;
    private RadioButton F1;
    private RadioButton F2;
    private RadioButton F3;
    private RadioButton F4;
    private RadioButton F5;
    private boolean enabled_F;
    private Button Need_Pruning;
    private RadioButton P1;
    private RadioButton P2;
    private RadioButton P3;
    private RadioButton P4;
    private RadioButton P5;
    private boolean enabled_P;
    private Button saveButton;
    private int days_W;
    private int Pruning;
    private int Fertilize;
    private EditText nameEditText;
    private ImageView plantImageView;
    private RadioGroup kind;
    private int selectedType;
    private String typeOfPlant;
    private RadioGroup fertilizer;
    private RadioGroup pruning;
    private Spinner spinner;
    private TextView titleTextView;

    private SQLiteDatabase sqLiteDatabase;
    private Bundle extras;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_plant);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        extras = getIntent().getExtras();

        nameEditText =findViewById(R.id.editText);
        saveButton =findViewById(R.id.SaveButton);
        saveButton.setEnabled(false);

        titleTextView = findViewById(R.id.titleTextView);

        ImageView backImageView = findViewById(R.id.backImageView);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        nameEditText.addTextChangedListener(SaveTextWatcher);
        fertilizer = findViewById(R.id.When);
        pruning = findViewById(R.id.When_P);

        spinner = findViewById(R.id.Date);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.WateringEvery, R.layout.spinner_text_view);
        adapter.setDropDownViewResource(R.layout.spinner_text_view);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Need_Fertilizer=findViewById(R.id.F_Yes);
        F1=findViewById(R.id.F1);
        F2=findViewById(R.id.F2);
        F3=findViewById(R.id.F3);
        F4=findViewById(R.id.F4);
        F5=findViewById(R.id.F5);
        enabled_F=true;
        Need_Fertilizer.setBackgroundColor(Color.rgb(102,102,120));
        Need_Fertilizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enabled_F) {
                    Need_Fertilizer.setBackgroundColor(0);
                    enabled_F=false;
                } else {
                    Need_Fertilizer.setBackgroundColor(Color.rgb(102,102,120));
                    enabled_F=true;
                }
                F_enabled();
            }
        });

        Need_Pruning=findViewById(R.id.P_Yes);
        P1=findViewById(R.id.P1);
        P2=findViewById(R.id.P2);
        P3=findViewById(R.id.P3);
        P4=findViewById(R.id.P4);
        P5=findViewById(R.id.P5);
        enabled_P=true;
        Need_Pruning.setBackgroundColor(Color.rgb(102,102,120));
        Need_Pruning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enabled_P) {
                    Need_Pruning.setBackgroundColor(0);
                    enabled_P=false;
                } else {
                    Need_Pruning.setBackgroundColor(Color.rgb(102,102,120));
                    enabled_P=true;
                }
                P_enabled();
            }
        });

        plantImageView = findViewById(R.id.plantImageView);
        kind = findViewById(R.id.kind);
        kind.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radio0:
                        plantImageView.setBackgroundResource(R.drawable.img_fern);
                        selectedType = 0;
                        typeOfPlant = "Fern";
                        break;
                    case R.id.radio1:
                        plantImageView.setBackgroundResource(R.drawable.img_moss);
                        selectedType = 1;
                        typeOfPlant = "Moss";
                        break;
                    case R.id.radio2:
                        plantImageView.setBackgroundResource(R.drawable.img_conifer);
                        selectedType = 2;
                        typeOfPlant = "Conifer";
                        break;
                    case R.id.radio3:
                        plantImageView.setBackgroundResource(R.drawable.img_flowering);
                        selectedType = 3;
                        typeOfPlant = "Flowering";
                        break;
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavePlant();
            }
        });

        Button deleteButton = findViewById(R.id.DeleteButton);


        if (extras != null){
            saveButton.setText("Update");

            titleTextView.setText("Edit your plant.");

            nameEditText.setText(extras.getString("name"));
            String type = extras.getString("type");
            if (type.equals("0")){
                kind.check(R.id.radio0);
                typeOfPlant = "Fern";
            }else if (type.equals("1")){
                kind.check(R.id.radio1);
                typeOfPlant = "Moss";
            }else if (type.equals("2")){
                kind.check(R.id.radio2);
                typeOfPlant = "Conifer";
            }else{
                kind.check(R.id.radio3);
                typeOfPlant = "Flowering";
            }

            days_W = extras.getInt("water");
            spinner.setSelection(findSpinnerPosition(extras.getInt("water")));

            F_enabled();
            String fert = extras.getString("fert");
            if (!fert.equals("/")){
                Fertilize = Integer.parseInt(fert);
                Need_Fertilizer.setBackgroundColor(0);
                enabled_F=false;

                if (fert.equals("7")){
                    fertilizer.check(R.id.F1);
                }else if (fert.equals("30")){
                    fertilizer.check(R.id.F2);
                }else if (fert.equals("90")){
                    fertilizer.check(R.id.F3);
                }else if (fert.equals("180"))
                    fertilizer.check(R.id.F4);
                else
                    fertilizer.check(R.id.F5);
            }else
                Fertilize = -1;

            P_enabled();
            String prun = extras.getString("prun");
            if (!prun.equals("/")){
                Pruning = Integer.parseInt(prun);
                Need_Pruning.setBackgroundColor(0);
                enabled_P=false;

                if (prun.equals("7")){
                    pruning.check(R.id.P1);
                }else if (prun.equals("30")){
                    pruning.check(R.id.P2);
                }else if (prun.equals("90")){
                    pruning.check(R.id.P3);
                }else if (prun.equals("180"))
                    pruning.check(R.id.P4);
                else
                    pruning.check(R.id.P5);
            }else
                Pruning = -1;

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(AddPlantActivity.this);
                    dialog.setContentView(R.layout.deletion_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    dialog.findViewById(R.id.closeImageView).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.findViewById(R.id.yesButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            removeItem(extras.getLong("id"));
                            dialog.dismiss();
                            onBackPressed();
                        }
                    });

                    dialog.show();
                }
            });
        }else
            deleteButton.setVisibility(View.INVISIBLE);

    }

    private void removeItem(long id) {
        sqLiteDatabase.delete(PlantsContract.PlantEntry.TABLE_NAME, PlantsContract.PlantEntry._ID + "=" + id, null);
    }

    private int findSpinnerPosition(int value){
        if(value==21)
            return 15;

        if(value==28)
            return 16;
        if(value==30)
            return 17;
        if(value==35)
            return 18;
        if(value==60)
            return 19;
        if(value==90)
            return 20;
        else
            return value-1;
    }


    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    public void SavePlant(){
        // In P_name is the name of the plant (first column of the database table)
        String P_name= nameEditText.getText().toString();
        if (P_name.length() == 0)
            return;

        //In Type_of_plant is the Selection in the first Radio Group of the activity
        if(selectedType==-1) {
            Toast.makeText(AddPlantActivity.this, "Kind can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        //In Fertilizer_When is the Selection in the second Radio Group of the activity
        //It can be empty
        int selectedKind = fertilizer.getCheckedRadioButtonId();
        RadioButton Fertilizer_Checked;
        String Fertilizer_When;
        if(selectedKind !=-1) {
            Fertilizer_Checked = findViewById(selectedKind);
            Fertilizer_When = Fertilizer_Checked.getText().toString();

            if(Fertilizer_When.equals("Weekly"))
                Fertilize=7;
            else if(Fertilizer_When.equals("Monthly"))
                Fertilize=30;
            else if(Fertilizer_When.equals("3 Months"))
                Fertilize=90;
            else if(Fertilizer_When.equals("6 Months"))
                Fertilize=180;
            else
                Fertilize=365;
        }

        if(enabled_F)
            Fertilize=-1;

        //In Pruning_when is the Selection in the third Radio Group of the activity
        //It can be empty

        selectedKind = pruning.getCheckedRadioButtonId();
        RadioButton Pruning_Checked;
        String Pruning_when;
        if (selectedKind !=-1) {
            Pruning_Checked = findViewById(selectedKind);
            Pruning_when=Pruning_Checked.getText().toString();

            if(Pruning_when.equals("Weekly"))
                Pruning=7;
            else if(Pruning_when.equals("Monthly"))
                Pruning=30;
            else if(Pruning_when.equals("3 Months"))
                Pruning=90;
            else if(Pruning_when.equals("6 Months"))
                Pruning=180;
            else
                Pruning=365;
        }

        if(enabled_P)
            Pruning=-1;


        //Add a new line in the table of database
        //String P_name is the name (can't be empty)
        //String Type_of_plant to type (" " if none is checked)
        //int days_w The time it takes to water it again (default = 1 )
        //int Fertilize The time it takes to fertilize it again (-1 if it doesn't need Fert)
        //int Pruning The time it takes to pruning it again (-1 if it doesn't need Prun)

        ContentValues contentValues = new ContentValues();
        contentValues.put(PlantsContract.PlantEntry.COLUMN_NAME, P_name);
        contentValues.put(PlantsContract.PlantEntry.COLUMN_TYPE, typeOfPlant);
        contentValues.put(PlantsContract.PlantEntry.COLUMN_WATERING, days_W);
        contentValues.put(PlantsContract.PlantEntry.COLUMN_FERTILIZER, Fertilize);
        contentValues.put(PlantsContract.PlantEntry.COLUMN_PRUNING, Pruning);
        contentValues.put(PlantsContract.PlantEntry.COLUMN_LAST_TIMESTAMP, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        if (saveButton.getText().toString().equals("Save")){
            sqLiteDatabase.insert(PlantsContract.PlantEntry.TABLE_NAME, null, contentValues);
            nameEditText.setText("");
            kind.clearCheck();
            plantImageView.setBackgroundResource(0);
            spinner.setSelection(0);
            Need_Fertilizer.setBackgroundColor(Color.rgb(102,102,120));
            F1.setEnabled(true);
            enabled_F=true;
            F_enabled();
            Fertilize = -1;

            Need_Pruning.setBackgroundColor(Color.rgb(102,102,120));
            P1.setEnabled(true);
            enabled_P=true;
            P_enabled();
            Pruning = -1;
        }
        else
            sqLiteDatabase.update(PlantsContract.PlantEntry.TABLE_NAME, contentValues, "_id="+ extras.getLong("id"), null);


        Toast.makeText(this, P_name + " " + (saveButton.getText().toString().equals("Save")?"saved":"updated") + " successfully!", Toast.LENGTH_LONG).show();

    }


    @SuppressLint("SetTextI18n")
    private void F_enabled(){
        if(!F1.isEnabled()) {
            Need_Fertilizer.setText("Yes");
            F1.setEnabled(true);
            F2.setEnabled(true);
            F3.setEnabled(true);
            F4.setEnabled(true);
            F5.setEnabled(true);
        }else{
            Need_Fertilizer.setText("No");
            fertilizer.clearCheck();
            F1.setEnabled(false);
            F2.setEnabled(false);
            F3.setEnabled(false);
            F4.setEnabled(false);
            F5.setEnabled(false);
        }
    }

    @SuppressLint("SetTextI18n")
    private void P_enabled(){
        if(!P1.isEnabled()) {
            Need_Pruning.setText("Yes");
            P1.setEnabled(true);
            P2.setEnabled(true);
            P3.setEnabled(true);
            P4.setEnabled(true);
            P5.setEnabled(true);
        }else{
            Need_Pruning.setText("No");
            pruning.clearCheck();
            P1.setEnabled(false);
            P2.setEnabled(false);
            P3.setEnabled(false);
            P4.setEnabled(false);
            P5.setEnabled(false);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        if(position==15)
            days_W=21;
        else if(position==16)
            days_W=28;
        else if(position==17)
            days_W=30;
        else if(position==18)
            days_W=35;
        else if(position==19)
            days_W=60;
        else if(position==20)
            days_W=90;
        else
            days_W=position+1;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private TextWatcher SaveTextWatcher= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String nameInput= nameEditText.getText().toString().trim();
            saveButton.setEnabled(!nameInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddPlantActivity.this, MyPlantsActivity.class);
        startActivity(intent);
        finish();
    }
}
