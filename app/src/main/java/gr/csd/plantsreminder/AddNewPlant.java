package gr.csd.plantsreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import java.lang.String;

public class AddNewPlant extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
    private Button Delete;
    private Button Save;
    private int days_W;
    private int Pruning;
    private int Fertilize;
    private EditText name;
    private RadioGroup kind;
    private int selectedkind;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_plant);

        name=findViewById(R.id.editText);
        Save=findViewById(R.id.SaveButton);
        Save.setEnabled(false);

        name.addTextChangedListener(SaveTextWatcher);

        Spinner spinner = findViewById(R.id.Date);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.WateringEvery, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

        kind=(RadioGroup) findViewById(R.id.kind);




        Delete=findViewById(R.id.DeleteButton);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddNewPlant.this,"Παράθυρο αναπήδησης για να τον ρωτάει αν είναι σίγουρος για την διαγραφή",Toast.LENGTH_SHORT).show();
            }
        });


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavePlant();
            }
        });
    }


    public void SavePlant(){
        // In P_name is the name of the plant (first column of the database table)
        String P_name= name.getText().toString();

        //In Type_of_plant is the Selection in the first Radio Group of the activity
        RadioButton kindchecked;
        String Type_of_plant=" ";
        int selectedId=kind.getCheckedRadioButtonId();
        if(selectedId!=-1) {
            kindchecked = (RadioButton) findViewById(selectedId);
            Type_of_plant = kindchecked.getText().toString();
        }else {
            Toast.makeText(AddNewPlant.this, "Kind can't be empty", Toast.LENGTH_SHORT).show();

        }


        Toast.makeText(AddNewPlant.this, String.valueOf(days_W), Toast.LENGTH_SHORT).show();

        //In Fertilizer_When is the Selection in the second Radio Group of the activity
        //It can be empty
        RadioGroup Fertilizer=(RadioGroup) findViewById(R.id.When);
        selectedId = Fertilizer.getCheckedRadioButtonId();
        RadioButton Fertilizer_Checked;
        String Fertilizer_When;
        if(selectedId!=-1) {
            Fertilizer_Checked = (RadioButton) findViewById(selectedId);
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
        RadioGroup Pr=(RadioGroup) findViewById(R.id.When_P);
        selectedId = Pr.getCheckedRadioButtonId();
        RadioButton Pruning_Checked;
        String Pruning_when;
        if (selectedId!=-1) {
            Pruning_Checked = (RadioButton) findViewById(selectedId);
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

        Toast.makeText(AddNewPlant.this,P_name,Toast.LENGTH_SHORT).show();



    }

    public void DeletePlant(){
        // delete the line from database

    }

    private void F_enabled(){
        if(F1.isEnabled()==false) {
            F1.setEnabled(true);
            F2.setEnabled(true);
            F3.setEnabled(true);
            F4.setEnabled(true);
            F5.setEnabled(true);
        }else{
            F1.setEnabled(false);
            F2.setEnabled(false);
            F3.setEnabled(false);
            F4.setEnabled(false);
            F5.setEnabled(false);
            if(F1.isChecked())
                F1.setChecked(false);
            else if (F2.isChecked())
                F2.setChecked(false);
            else if (F3.isChecked())
                F3.setChecked(false);
            else if (F4.isChecked())
                F4.setChecked(false);
            else
                F5.setChecked(false);
        }
    }
    private void P_enabled(){
        if(P1.isEnabled()==false) {
            P1.setEnabled(true);
            P2.setEnabled(true);
            P3.setEnabled(true);
            P4.setEnabled(true);
            P5.setEnabled(true);
        }else{
            P1.setEnabled(false);
            P2.setEnabled(false);
            P3.setEnabled(false);
            P4.setEnabled(false);
            P5.setEnabled(false);
            if(P1.isChecked())
                P1.setChecked(false);
            else if (P2.isChecked())
                P2.setChecked(false);
            else if (P3.isChecked())
                P3.setChecked(false);
            else if (P4.isChecked())
                P4.setChecked(false);
            else
                P5.setChecked(false);
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
            String nameInput=name.getText().toString().trim();

            Save.setEnabled(!nameInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
