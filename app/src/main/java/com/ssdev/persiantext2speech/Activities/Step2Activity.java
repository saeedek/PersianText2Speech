package com.ssdev.persiantext2speech.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ssdev.persiantext2speech.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Step2Activity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private Button btnPlay,btnStop,btnProceed;
    private EditText txtText;
    private TextView txtSize,txtDur;

    private boolean isPlaying;

    private File f;
    private MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step2);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnStop=(Button)findViewById(R.id.btnStop);
        btnProceed=(Button)findViewById(R.id.btnProceed);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Step2Activity.this,Step3Activity.class));
                finish();
            }
        });
        txtText=(EditText)findViewById(R.id.txtText);
        txtSize=(TextView)findViewById(R.id.txtSize);
        txtDur=(TextView)findViewById(R.id.txtDur);
        isPlaying=false;
        txtText.setEnabled(false);
        Intent i=getIntent();
        if(!i.getStringExtra("text").equals("")){
            txtText.setText(i.getStringExtra("text"));
        }
        setButtonState();
        f=new File("/sdcard/a2s.wav");
        boolean t= f.exists();
        if(t){
            txtSize.setText(String.valueOf(f.length() / 1024) + " KB");
            getDuration(f);
        }
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mp=new MediaPlayer();
                        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        try {
                            FileInputStream fos=new FileInputStream(f);
                            mp.setDataSource(f.getPath());
                            fos.close();
                            mp.setOnPreparedListener(Step2Activity.this);
                            mp.setOnCompletionListener(Step2Activity.this);
                            mp.prepare();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying()){
                    mp.stop();
                    isPlaying=false;
                    setButtonState();
                }
            }
        });
    }

    private void getDuration(final File f) {
        String h,m,s;
        final String[] res = {""};
        final String[] secondsString = {""};
        if(f.exists()){
            int b=3;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileInputStream fos=new FileInputStream(f);
                    MediaPlayer tmp=new MediaPlayer();
                    tmp.setDataSource(fos.getFD());
                    tmp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer smp) {
                            long milliseconds = smp.getDuration();
                            int hours = (int) (milliseconds / (1000 * 60 * 60));
                            int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
                            int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
                            // Add hours if there
                            if (hours > 0) {
                                res[0] = hours + ":";
                            }

                            // Prepending 0 to seconds if it is one digit
                            if (seconds < 10) {
                                secondsString[0] = "0" + seconds;
                            } else {
                                secondsString[0] = "" + seconds;
                            }

                            res[0] = res[0] + minutes + ":" + secondsString[0];

                            txtDur.setText(res[0]);
                            smp.release();
                        }
                    });
                    tmp.prepare();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        if(mp!=null){
                            mp.release();
                            mp=null;
                        }
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        startActivity(new Intent(Step2Activity.this, MainActivity.class));
                        if(mp!=null){
                            mp.release();
                            mp=null;
                        }
                        finish();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(Step2Activity.this);
        builder.setMessage("انتخاب کنید").setPositiveButton("خروج", dialogClickListener)
                .setNegativeButton("بازگشت به صفحه اصلی", dialogClickListener).show();
        return;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        isPlaying=true;
        setButtonState();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.release();
        mp=null;
        isPlaying=false;
        setButtonState();
    }

    public void setButtonState(){
        if(isPlaying){
            btnPlay.setEnabled(false);
            btnStop.setEnabled(true);
        }
        else{
            btnPlay.setEnabled(true);
            btnStop.setEnabled(false);
        }
    }
}