package com.korchid.msg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ReserveActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        String[] weekOption = getResources().getStringArray(R.array.spinnerWeek);
        String[] numberOption = getResources().getStringArray(R.array.spinner1weekNumber);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, weekOption);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        setSpinner(R.id.spinner, );


    }

    public void setSpinner (int objId, int optionLabelId, int listStyle){
        setSpinner(objId, optionLabelId, -1, listStyle, null);
    }

    public  void setSpinner (int objId, int optionLabelId, int optionId, int listStyle, String defaultVal){
        String[] optionLabel = getResources().getStringArray(optionLabelId);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, optionLabel);

        if(listStyle > -1){
            adapter.setDropDownViewResource(listStyle);

            Spinner obj = (Spinner)findViewById(objId);
            obj.setAdapter(adapter);

            if(defaultVal != null){
                String[] optionA = getResources().getStringArray(optionId);
                int thei = 0;
                for(int a=0; a<optionA.length; a++){
                    if(defaultVal.equals(optionA[a])){
                        thei = a;
                        break;
                    }
                }
                obj.setSelection(adapter.getPosition(optionA[thei]));
            }else{
                obj.setSelection(adapter.getPosition(defaultVal));
            }

        }
    }

    public Spinner getSpinner(int objId){
        return (Spinner) findViewById(objId);
    }

}
