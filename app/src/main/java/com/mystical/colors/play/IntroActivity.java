package com.mystical.colors.play;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;

public class IntroActivity extends AppCompatActivity {

    TextView tv;
    ImageView im1,im2,im3,im4,image,hand;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    tts.setLanguage(new Locale("en", "IND"));
                    tts.setSpeechRate(0.75f);
                    tts.setPitch(1.25f);
                    speak("Welcome to Color Play");
                }
            }
        });

        tv = (TextView) findViewById(R.id.textView);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.activity_vertical_margin));
        im1 = (ImageView) findViewById(R.id.im1);
        im2 = (ImageView) findViewById(R.id.im2);
        im3 = (ImageView) findViewById(R.id.im3);
        im4 = (ImageView) findViewById(R.id.im4);
        image = (ImageView) findViewById(R.id.image);
        hand = (ImageView) findViewById(R.id.hand);
        hand.setVisibility(View.INVISIBLE);

        ViewGroup.MarginLayoutParams margin;
        margin = (ViewGroup.MarginLayoutParams) im1.getLayoutParams();
        margin.leftMargin = 20; margin.bottomMargin = 6;
        margin = (ViewGroup.MarginLayoutParams) im2.getLayoutParams();
        margin.leftMargin = 10; margin.bottomMargin = 6;
        margin = (ViewGroup.MarginLayoutParams) im3.getLayoutParams();
        margin.leftMargin = 10; margin.bottomMargin = 6;
        margin = (ViewGroup.MarginLayoutParams) im4.getLayoutParams();
        margin.leftMargin = 10; margin.bottomMargin = 6;

    }

    private void speak( String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(text);
        } else {
            ttsUnder20(text);
        }
    }
    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        String utteranceId=this.hashCode() + "";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
    }
    boolean transfer = false;
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            speak("Match the image with its corresponding color");
            if(!transfer) {
                anim();
                transfer = true;
                Log.i("IntroActivity","Line 87");
            }
        }
    }

    private void anim() {
        final int[] coordinates = new int[2];
        im2.getLocationOnScreen(coordinates);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hand.setVisibility(View.VISIBLE);
                hand.animate()
                        .x(coordinates[0])
                        .y(coordinates[1])
                        .setDuration(4000)
                        .start();

                image.animate()
                        .x(coordinates[0])
                        .y(coordinates[1])
                        .setDuration(4000)
                        .start();
            }
        }, 3500);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("change","it");
                /*SharedPreferences preferences = getSharedPreferences("prefs", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("first", false);
                editor.commit();*/
                startActivity(new Intent(getApplicationContext(), homepage.class));
                finish();
            }
        }, 10000);


    }
}
