package com.example.application.testserver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Main2Activity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText name,rollno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        name=(EditText)findViewById(R.id.editText2);
        rollno=(EditText)findViewById(R.id.editText3);





    }


    public void setRollno(){






                     sharedPreferences=getSharedPreferences("ROLLNO_NAME", Context.MODE_PRIVATE);
                     SharedPreferences.Editor editor = sharedPreferences.edit();

                     editor.putString("spname",name.getText().toString());
                     editor.putString("sproll",rollno.getText().toString());

                      editor.putBoolean("flag",true   );
                         editor.apply();
    }







    public void onclick(View view){










        if(name.getText().toString().equals("")&&rollno.getText().toString().equals(""))
        { Toast.makeText(this,"ENTER ALL DETAILS",Toast.LENGTH_SHORT).show();}
        else if(name.getText().toString().equals("")){

            Toast.makeText(this,"ENTER ALL DETAILS",Toast.LENGTH_SHORT).show();

        }
        else if(rollno.getText().toString().equals("")){Toast.makeText(this,"ENTER ALL DETAILS ",Toast.LENGTH_SHORT).show();}

        else{setRollno();


            Intent intent = new Intent(this, MainActivity.class);
            Toast.makeText(this,"DETAILS SAVED",Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();}




    }


}
