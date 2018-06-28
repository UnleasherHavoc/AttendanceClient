package com.example.application.testserver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class MainActivity extends AppCompatActivity {

      EditText ed,ed2;
       TextView tv,acknow;

    Button button;
    String messsage="Student DEVICE ERROR";
    String response="";

     SharedPreferences sharedPreferences;
     private  boolean check_empty_STRING=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         tv=(TextView) findViewById(R.id.tv);
        button=(Button)findViewById(R.id.button);
        ed=(EditText) findViewById(R.id.ed);
        ed2=(EditText) findViewById(R.id.ed1);
        acknow=(TextView)findViewById(R.id.acknow);


         Runnable Sp=new Runnable() {
             @Override
             public void run() {

                 sharedPreferences=getSharedPreferences("ROLLNO_NAME", Context.MODE_PRIVATE);
                    check_empty_STRING=sharedPreferences.getBoolean("flag",false);
                 tv.setText(sharedPreferences.getString("sproll","")+" "+sharedPreferences.getString("spname",""));
             }
         };
        Sp.run();



        messsage =tv.getText().toString();





    }





    public class ClientCONNECT extends Thread{

        String ip="";
        int port=0;
        Socket socket=null;



        public ClientCONNECT(String ip, int port) {
            this.ip = ip;
            this.port = port;

        }

        @Override
        public void run() {



          MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                acknow.setText("WAITING  ");

            }
        });



            try{


                socket=new Socket(ip,port);




                OutputStream outputStream=socket.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(messsage);
                bufferedWriter.newLine();
                bufferedWriter.flush();



                BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(socket.getInputStream()));
                response ="TEACHER HAS MARKED YOU AS PRESENT IN THE RECORD YOUR RECORD NO IS --->"+" "+bufferedReader.read();

                if(response.equals("")) {}  else{

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            acknow.setText(response);
                        }
                    });

                }




            }
            catch (IOException e){   MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"FAILED TO CONNECT SERVER ",Toast.LENGTH_SHORT).show();
                    acknow.setText("CHECK INTERNET CONNECTION" +"\n" +"CHECK INTERNET IP ADDRESS AND PORT NUMBER");

                }
            });
            }

        }




    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {

            sharedPreferences=getSharedPreferences("ROLLNO_NAME", Context.MODE_PRIVATE);
            String a=sharedPreferences.getString("sproll","");
            String b=sharedPreferences.getString("spname","");
            if(a.equals("")&&b.equals("")){
                SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
                boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);


                if(isFirstRun==true){  SharedPreferences.Editor editor1 = wmbPreference.edit();
                    editor1.putBoolean("FIRSTRUN", false);
                    editor1.apply();

                    Intent intent=new Intent(this,Main2Activity.class);
                startActivity(intent);}
                else{Intent intent=new Intent(this,Main2Activity.class);
                    startActivity(intent);}

            }
            else{
                Toast.makeText(this,"ROLLNO ALREADY SET",Toast.LENGTH_SHORT).show(); }




            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onSend(View view) {





                     if(check_empty_STRING)

                     {

                         ClientCONNECT client = new ClientCONNECT(ed.getText().toString(), Integer.parseInt(ed2.getText().toString()));
                         client.start();


                     }
                  else
                     {

                         acknow.setText("SET ROLL NUMBER AND NAME IN MENU");

                         Toast.makeText(this,"SET ROLL NUMBER AND NAME IN MENU",Toast.LENGTH_SHORT).show();

                     }





    }

           }



