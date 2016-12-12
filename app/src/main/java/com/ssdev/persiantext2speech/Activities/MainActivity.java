package com.ssdev.persiantext2speech.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ssdev.persiantext2speech.ApplicationClasses.*;
import com.ssdev.persiantext2speech.Interfaces.onApiRes;
import com.ssdev.persiantext2speech.ModelClasses.*;
import com.ssdev.persiantext2speech.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private Button btnSend;
    private EditText txtMatn;
    ApiCommunicator mCommunicator;
    private Spinner spnPunc,spnRate;
    private String Punc,Rate,Vol,Pitch;
    private NumberPicker npv,npr,npp;
    private ProgressDialog ringProgressDialog;
    private String enteredText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spnPunc=(Spinner)findViewById(R.id.spnPunc);
        SettingNps();
        Pitch="10";
        Rate="10";
        Vol="100";
        btnSend = (Button) findViewById(R.id.btnRead);
        txtMatn = (EditText) findViewById(R.id.txtMatn);
        mCommunicator = PersianText2Speech.getInstance().getApiCommunicator();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtMatn.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "لطفا متنی را وارد کنید", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isNetworkAvailable()) {
                    Toast.makeText(MainActivity.this, "لطفا اینترنت را چک کنید", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isPersian(txtMatn.getText().toString())) {
                    Toast.makeText(MainActivity.this, "لطفا متن فارسی وارد کنید", Toast.LENGTH_SHORT).show();
                    return;
                }
                ringProgressDialog = ProgressDialog.show(MainActivity.this, "لطفا منتظر بمانید", "درحال تبدیل متن", true);
                ringProgressDialog.setCancelable(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Insert ob = new Insert(txtMatn.getText().toString(), "Eloquence", Vol, Pitch, Rate, Punc, SampleRate._16KHz_16bps.name(), "saeedek", "1478963");
                        mCommunicator.doRequest(InsertResult.class, "Insert", ob, new onApiRes() {
                            @Override
                            public void onSuccess(Object result) {
                                InsertResult res = (InsertResult) result;
                                if (!res.haveError.equalsIgnoreCase("true")) {
                                    PersianText2Speech.getInstance().getApiCommunicator().CurrentIndex = res.getClientIndex();
                                    enteredText=txtMatn.getText().toString();
                                    CallRead();
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this, "اشکال در تبدیل", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    });
                                    ringProgressDialog.dismiss();
                                    return;
                                }
                            }

                            @Override
                            public void onFailed(final String err) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "خطایی رخ داد دوباره تلاش کنید", Toast.LENGTH_LONG).show();
                                        ringProgressDialog.dismiss();
                                        return;
                                    }
                                });

                            }
                        });
                    }
                }).start();
            }
        });
        spnPunc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Punc = "None";
                        break;
                    case 1:
                        Punc = "Some";
                        break;
                    case 2:
                        Punc = "Most";
                        break;
                    case 3:
                        Punc = "All";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void SettingNps() {
        npp=(NumberPicker)findViewById(R.id.npp);
        npr=(NumberPicker)findViewById(R.id.npr);
        npv=(NumberPicker)findViewById(R.id.npv);
        npp.setMinValue(0);
        npp.setMaxValue(20);
        npp.setValue(10);
        npp.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Pitch=String.valueOf(newVal);
            }
        });
        npr.setMinValue(0);
        npr.setMaxValue(20);
        npr.setValue(10);
        npr.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Rate=String.valueOf(newVal);
            }
        });
        npv.setMinValue(0);
        npv.setMaxValue(100);
        npv.setValue(100);
        npv.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Vol=String.valueOf(newVal);
            }
        });


    }

    private boolean isPersian(String s) {
        final Pattern sPattern
                = Pattern.compile("^[\\u0600-\\u06FF\\uFB8A\\u067E\\u0686\\u06AF ]+$");
        return sPattern.matcher(s).matches();
    }

    private void CallRead() {
        final ReadResult[] r = new ReadResult[1];
        Read ob = new Read(String.valueOf(mCommunicator.CurrentIndex), "saeedek", "1478963");
        mCommunicator.doRequest(ReadResult.class, "Read", ob, new onApiRes() {
            @Override
            public void onSuccess(Object res) {
                r[0] = (ReadResult) res;
                if(r[0].getIsSynthesizeFinished().equalsIgnoreCase("false")){
                    CallRead();
                }
                else{
                    if (r[0].haveError.contains("false")) {
                        if (!r[0].haveError.equalsIgnoreCase("true")) {
                            byte[] Bytes = Base64.decode(r[0].Result, Base64.CRLF);
                            try {
                                File f = new File("/sdcard/a2s.wav");
                                if(f.exists())
                                    f.delete();
                                FileOutputStream fos = new FileOutputStream(f);
                                fos.write(Bytes);
                                fos.close();
                                if (f.exists()) {
                                    Intent i=new Intent(MainActivity.this,Step2Activity.class);
                                    i.putExtra("text",enteredText);
                                    startActivity(i);
                                    ringProgressDialog.dismiss();
                                    finish();
                                }
                            } catch (IOException e) {
                                Toast.makeText(MainActivity.this, "اشکال داخلی", Toast.LENGTH_SHORT).show();
                                ringProgressDialog.dismiss();
                                return;
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            ringProgressDialog.dismiss();
                            return;
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        ringProgressDialog.dismiss();
                        return;
                    }
                }
            }
            @Override
            public void onFailed(String err) {
                Toast.makeText(MainActivity.this, "اشکال در سرور دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
