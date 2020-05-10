package gr.csd.plantsreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
    private CheckBox Monday;
    private CheckBox Tuesday;
    private CheckBox Wednesday;
    private CheckBox Thursday;
    private CheckBox Friday;
    private CheckBox Saturday;
    private CheckBox Sunday;
    private Button Delete;
    private Button Save;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_plant);

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


        Monday=findViewById(R.id.Monday);
        Tuesday=findViewById(R.id.Tuesday);
        Wednesday=findViewById(R.id.Wednesday);
        Thursday=findViewById(R.id.Thursday);
        Friday=findViewById(R.id.Friday);
        Saturday=findViewById(R.id.Saturday);
        Sunday=findViewById(R.id.Sunday);

        Delete=findViewById(R.id.DeleteButton);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddNewPlant.this,"Παράθυρο αναπήδησης για να τον ρωτάει αν είναι σίγουρος για την διαγραφή",Toast.LENGTH_SHORT).show();
            }
        });

        Save=findViewById(R.id.SaveButton);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddNewPlant.this,"Επιστροφή στο MainActivity, έχοντας άλλο ένα αντικείμενο",Toast.LENGTH_SHORT).show();
            }
        });
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
        if(position==0){
            Days_Enabled(true,false);
        }else if(position==1){
            Days_Enabled(false,true);
        }else
            Days_Enabled(false,false);
    }

    private void Days_Enabled(boolean checked,boolean Enable){
        if(Enable){
                Monday.setEnabled(true);
                Tuesday.setEnabled(true);
                Wednesday.setEnabled(true);
                Thursday.setEnabled(true);
                Friday.setEnabled(true);
                Saturday.setEnabled(true);
                Sunday.setEnabled(true);
                Monday.setChecked(true);
                Tuesday.setChecked(false);
                Wednesday.setChecked(true);
                Thursday.setChecked(false);
                Friday.setChecked(false);
                Saturday.setChecked(true);
                Sunday.setChecked(false);
            } else {
                if(checked){
                    Monday.setChecked(true);
                    Tuesday.setChecked(true);
                    Wednesday.setChecked(true);
                    Thursday.setChecked(true);
                    Friday.setChecked(true);
                    Saturday.setChecked(true);
                    Sunday.setChecked(true);
                }else{
                    Monday.setChecked(false);
                    Tuesday.setChecked(false);
                    Wednesday.setChecked(false);
                    Thursday.setChecked(false);
                    Friday.setChecked(false);
                    Saturday.setChecked(false);
                    Sunday.setChecked(false);
                }
            Monday.setEnabled(false);
            Tuesday.setEnabled(false);
            Wednesday.setEnabled(false);
            Thursday.setEnabled(false);
            Friday.setEnabled(false);
            Saturday.setEnabled(false);
            Sunday.setEnabled(false);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
