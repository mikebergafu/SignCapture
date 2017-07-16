package com.bitlogictechnologies.signcapture;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bitlogictechnologies.Map.SignageMap;

import java.sql.Array;

/**
 * Created by Mike-berg on 12-07-2017.
 */

public class Page1 extends AppCompatActivity {
    private Button btn_previous, btn_next;
    private AutoCompleteTextView txt_company_name, txt_phone;
    private RadioGroup rbn_pages, rbn_group;
    private RadioButton rbn_page1, rbn_page2, rbn_page3,  rbn_sign_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page1_ui);

        //Declares sign type selectors
        rbn_group=(RadioGroup) findViewById(R.id.rbn_sign_type);


        //Create referenced txtboxes and buttons from layout
        txt_company_name=(AutoCompleteTextView)findViewById(R.id.txt_comp_name);
        txt_phone=(AutoCompleteTextView)findViewById(R.id.txt_phone);

        btn_previous=(Button) findViewById(R.id.btn_previous);
        btn_next=(Button) findViewById(R.id.btn_next);


        //Declares the page indicators
        rbn_pages=(RadioGroup) findViewById(R.id.rbn_pages);
        rbn_page1=(RadioButton) findViewById(R.id.rbn_page1);
        rbn_page2=(RadioButton) findViewById(R.id.rbn_page2);
        rbn_page3=(RadioButton) findViewById(R.id.rbn_page3);
        //sets page indicators to color based on current data entry status
        rbn_page1.setBackgroundColor(Color.LTGRAY);
        rbn_page2.setBackgroundColor(Color.YELLOW);
        rbn_page3.setBackgroundColor(Color.YELLOW);




        //Prevents the page indicators from being selected
        rbn_pages.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
              if(checkedId==R.id.rbn_page1){
                  rbn_page1.setChecked(true);
              }else if(checkedId==R.id.rbn_page2){
                  rbn_page1.setChecked(true);
              }else rbn_page1.setChecked(true);
            }
        });
        //Enable the Previous button to load dashboard of this application
        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Enable the Next button to load location and Map of this application
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMap();
            }
        });

    }
/*
//Enables and Disable Next Button
    private void enableNext() {
        int id = rbn_group.getCheckedRadioButtonId();
        String company_name = txt_company_name.getText().toString();
        String phone = txt_phone.getText().toString();
        //Enables and Disable Next Button
        if (id > 0) {
            btn_next.setTextColor(Color.MAGENTA);
            btn_next.setEnabled(true);
        } else {
            btn_next.setTextColor(Color.LTGRAY);
            btn_next.setEnabled(false);
        }
    }
*/
    private void loadMap() {
        int id = rbn_group.getCheckedRadioButtonId();

        String sign_type="";
        //Validate data from User Textboxes and Radio Buttons
        if(id<1){
            Toast.makeText(this, "Kindly Select Signage Type to Proceed",Toast.LENGTH_SHORT).show();
        }else {
            rbn_sign_type = (RadioButton) findViewById(id);
            sign_type =rbn_sign_type.getText().toString();
            String company_name=txt_company_name.getText().toString();
            String phone=txt_phone.getText().toString();

            if(company_name.trim().length()>1 && phone.trim().length()>1 ){
                Toast.makeText(this, "Proceeding... to the next Stage",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SignageMap.class);
                Bundle page1_data=new Bundle();
                //Receives and puts the data into the bundle object for transmission
                page1_data.putString("company_name",company_name);
                page1_data.putString("phone",phone);
                page1_data.putString("sign_type", sign_type);
                intent.putExtras(page1_data);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }else Toast.makeText(this, "Sorry! Check Your Fields",Toast.LENGTH_SHORT).show();
        }

    }
    private void loadDashboard() {
        Intent intent = new Intent(this, Main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
