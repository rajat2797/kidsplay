package com.kidminks.xxx.image2to5;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.CalendarContract;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.locks.ReentrantLock;

public class homepage extends AppCompatActivity implements colorexamples.OnFragmentInteractionListener {

    collection[] data;
    colourdata[] colourdatas;
    String[] letsdance;
    private boolean flag;
    int wincount = 0;
    private final int dataavailable = 5;

    ImageView[] star;
    float x,y;

    DisplayMetrics displayMetrics;

    TextToSpeech tts;
    String cname,i1cname,i2cname,i3cname,i4cname;

//    private ImageView back1image,back2image;
//    AnimationDrawable animationDrawable1,animationDrawable2;

    private ImageView imageView,im1,im2,im3,im4,mainimage;
    private ViewGroup viewGroup;
    private int marleft,marright,martop,marbottom;
    private int xcor;
    private int ycor;
    FrameLayout frame_container;
    private colorexamples fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        data = new collection[100];
        colourdatas = new colourdata[20];
        letsdance = new String[7];
        initializecolor();
        initializedances();
        initialize();
        initialisestars();
        frame_container = (FrameLayout) findViewById(R.id.frame_container);
        fragment = new colorexamples();

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if( i==TextToSpeech.SUCCESS ){
                    tts.setLanguage(new Locale("en","IN"));
                }else{
                    Toast.makeText(homepage.this,"Sorry Bro",Toast.LENGTH_SHORT).show();
                }
            }
        });
        frame_container.setVisibility(View.INVISIBLE);

//        back1image = (ImageView)findViewById(R.id.back1image);
//        back2image = (ImageView)findViewById(R.id.back2image);
//        back1image.setBackgroundResource(R.drawable.mybackanimation1);
//        back2image.setBackgroundResource(R.drawable.mybackanimation2);
//        animationDrawable1 = (AnimationDrawable) back1image.getBackground();
//        animationDrawable2 = (AnimationDrawable) back2image.getBackground();
//        animationDrawable1.start();
//        animationDrawable2.start();

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        viewGroup = (ViewGroup)findViewById(R.id.view_root);
        imageView = (ImageView)findViewById(R.id.imageView);
        im1 = (ImageView)findViewById(R.id.ic1);
        im2 = (ImageView)findViewById(R.id.ic2);
        im3 = (ImageView)findViewById(R.id.ic3);
        im4 = (ImageView)findViewById(R.id.ic4);
        findrand();

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(250,250);
        layoutParams.setMarginStart(displayMetrics.widthPixels/2-200);
        imageView.setLayoutParams(layoutParams);
        imageView.setOnTouchListener(new ChoiceTouchListner() );
    }


                                              /* for moving object */

    private final class ChoiceTouchListner implements View.OnTouchListener{
        public boolean onTouch(View view, MotionEvent event){
            FrameLayout dancefrag;
            dancefrag = (FrameLayout)findViewById(R.id.dancefrag);
            if(dancefrag.getVisibility()==View.VISIBLE){
                return false;
            }
            final int X = (int)event.getRawX();
            final int Y = (int)event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
//                    animationDrawable1.stop();
//                    animationDrawable2.stop();
                    tts.speak(cname,TextToSpeech.QUEUE_FLUSH,null,null);
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)view.getLayoutParams();
                    xcor = X-lp.leftMargin;
                    ycor = Y-lp.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
//                    animationDrawable1.start();
//                    animationDrawable2.start();
                    RelativeLayout.LayoutParams checkpar = (RelativeLayout.LayoutParams)view.getLayoutParams();
                    checklocation(checkpar);
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
                    layoutParams.leftMargin = X - xcor;
                    layoutParams.topMargin = Y - ycor;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            viewGroup.invalidate();
            return true;
        }
    }
                                    /*............................................*/




                                            /* Check for win or loose */
    /* checking win */

    private void checklocation(RelativeLayout.LayoutParams checkpar){
        if( flag==false ){
            setMarginOfViews(mainimage);
            flag = true;
        }
        if( (checkpar.topMargin+250>=martop&&checkpar.topMargin+250<=marbottom)
                &&(checkpar.leftMargin+125>=marleft&&checkpar.leftMargin+125<=marright)
                ){
            flag=false;
            wincount+=1;
            appreciate();
            if( wincount==5 ) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showdance();
                        wincount = 0;
                    }
                }, 2500);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                }, 7000);

            }
            changeimage();
            changeposition();
        }
        else{
            /*on two wrong call rohit's function*/
            if(checkpar.topMargin+250<martop){
                tts.speak("Drag carefully",TextToSpeech.QUEUE_FLUSH,null,null);
            }
            else{
                tryitagain();
            }
            changeposition();
        }
    }

    /* changing things when won */
    private void changeimage(){
        findrand();
    }

    /* reverting to initial position */
    private void changeposition(){
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(250,250);
        layoutParams.setMarginStart(displayMetrics.widthPixels/2-200);
        imageView.setLayoutParams(layoutParams);
    }
    /* to show dance*/
    private void showdance(){
        FrameLayout dancefrag;
        dancefrag = (FrameLayout)findViewById(R.id.dancefrag);
        if(dancefrag.getVisibility()==View.VISIBLE){
            return;
        }
        final MediaPlayer clap = MediaPlayer.create(this,R.raw.applause);
        clap.start();
        dancefrag.setVisibility(View.VISIBLE);
        dancefrag.setZ(1000);
        ImageView imageView = (ImageView)findViewById(R.id.artimage);
        String name = letsdance[(int)(Math.random()*6)+1];
        int id = getResources().getIdentifier(name,"drawable",getPackageName());
        imageView.setBackgroundResource(id);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                FrameLayout dancefrag;
                dancefrag = (FrameLayout)findViewById(R.id.dancefrag);
                if(dancefrag.getVisibility()==View.VISIBLE){
                    dancefrag.setVisibility(View.GONE);
                    clap.stop();
                    return;
                }
            }
        };
        Handler h =new Handler();
        h.postDelayed(r,6000);

        star[1].setVisibility(View.INVISIBLE);
        star[2].setVisibility(View.INVISIBLE);
        star[3].setVisibility(View.INVISIBLE);
        star[4].setVisibility(View.INVISIBLE);
        star[5].setVisibility(View.INVISIBLE);

    }

                                /*.....................................................*/



                                                  /* onclick functions*/
    public void callim1(View v) {

        FrameLayout dancefrag;
        dancefrag = (FrameLayout)findViewById(R.id.dancefrag);
        if(dancefrag.getVisibility()==View.VISIBLE){
            return;
        }
        if(frame_container.getVisibility() == View.VISIBLE) {
            frame_container.setVisibility(View.INVISIBLE);
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        else {
            tts.speak(i1cname,TextToSpeech.QUEUE_FLUSH,null,null);
//            Toast.makeText(homepage.this, "Landed Here", Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("color_name", i1cname);

            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_container, fragment).commit();
            frame_container.setVisibility(View.VISIBLE);
        }
    }
    public void callim2(View v){
        FrameLayout dancefrag;
        dancefrag = (FrameLayout)findViewById(R.id.dancefrag);
        if(dancefrag.getVisibility()==View.VISIBLE){
            return;
        }
        if(frame_container.getVisibility() == View.VISIBLE) {
            frame_container.setVisibility(View.INVISIBLE);
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        else {
            tts.speak(i2cname,TextToSpeech.QUEUE_FLUSH,null,null);
//            Toast.makeText(homepage.this, "Landed Here", Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("color_name", i2cname);
//            colorexamples fragment = new colorexamples();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_container, fragment).commit();
            frame_container.setVisibility(View.VISIBLE);
        }
    }
    public void callim3(View v){
        FrameLayout dancefrag;
        dancefrag = (FrameLayout)findViewById(R.id.dancefrag);
        if(dancefrag.getVisibility()==View.VISIBLE){
            return;
        }
        if(frame_container.getVisibility() == View.VISIBLE) {
            frame_container.setVisibility(View.INVISIBLE);
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        else {
            tts.speak(i3cname,TextToSpeech.QUEUE_FLUSH,null,null);
//            Toast.makeText(homepage.this, "Landed Here", Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("color_name", i3cname);
//            colorexamples fragment = new colorexamples();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_container, fragment).commit();
            frame_container.setVisibility(View.VISIBLE);
        }
    }
    public void callim4(View v){
        FrameLayout dancefrag;
        dancefrag = (FrameLayout)findViewById(R.id.dancefrag);
        if(dancefrag.getVisibility()==View.VISIBLE){
            return;
        }
        if(frame_container.getVisibility() == View.VISIBLE) {
            frame_container.setVisibility(View.INVISIBLE);
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        else {
            tts.speak(i4cname,TextToSpeech.QUEUE_FLUSH,null,null);
//            Toast.makeText(homepage.this, "Landed Here", Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("color_name", i4cname);
//            colorexamples fragment = new colorexamples();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_container, fragment).commit();
            frame_container.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /*......................................................*/



                                                  /* data initialization */
    /*getting layout margin of correct colour */

    private void setMarginOfViews(ImageView mainimage){
        marleft = (int)mainimage.getX();
        martop = (int)mainimage.getY();
        marright = marleft + mainimage.getWidth();
        marbottom = martop + mainimage.getHeight();
    }

    /*initializing image data .................... only once */
    private void initialize(){
        for( int i=1;i<=dataavailable;i++ ){
            data[i] = new collection();
        }
        flag = false;
        data[1].name="a1";data[1].color="#32cd32";data[1].colorname="Green";
        data[2].name="a2";data[2].color="#ff0000";data[2].colorname="Red";
        data[3].name="a3";data[3].color="#1e90ff";data[3].colorname="Blue";
        data[4].name="a4";data[4].color="#fa7305";data[4].colorname="Orange";
        data[5].name="a5";data[5].color="#ffff00";data[5].colorname="Yellow";
    }
    /*initialization colour .................... only once */
    private void initializecolor(){
        for( int i=1;i<=10;i++ ){
            colourdatas[i] = new colourdata();
        }
        colourdatas[1].color="#32cd32";colourdatas[1].colorname="Green";
        colourdatas[2].color="#ff0000";colourdatas[2].colorname="Red";
        colourdatas[3].color="#1e90ff";colourdatas[3].colorname="Blue";
        colourdatas[4].color="#fa7305";colourdatas[4].colorname="Orange";
        colourdatas[5].color="#ffff00";colourdatas[5].colorname="Yellow";
        colourdatas[6].color="#553426";colourdatas[6].colorname="Brown";
        colourdatas[7].color="#030303";colourdatas[7].colorname="Black";
        colourdatas[8].color="#fa05ac";colourdatas[8].colorname="Pink";
        colourdatas[9].color="#a709f6";colourdatas[9].colorname="Purple";
        colourdatas[10].color="#555555";colourdatas[10].colorname="Grey";
    }
    /*initialize dance ........................... only once*/
    private void initializedances(){
        letsdance[1]="mycartoonanimation1";
        letsdance[2]="mycartoonanimation2";
        letsdance[3]="mycartoonanimation3";
        letsdance[4]="mycartoonanimation4";
        letsdance[5]="mycartoonanimation5";
        letsdance[6]="mycartoonanimation6";
    }

//    initialising stars
    private void initialisestars() {
        star = new ImageView[6];
        star[1] = (ImageView) findViewById(R.id.star1);
        star[2] = (ImageView) findViewById(R.id.star2);
        star[3] = (ImageView) findViewById(R.id.star3);
        star[4] = (ImageView) findViewById(R.id.star4);
        star[5] = (ImageView) findViewById(R.id.star5);

        star[1].setVisibility(View.INVISIBLE);
        star[2].setVisibility(View.INVISIBLE);
        star[3].setVisibility(View.INVISIBLE);
        star[4].setVisibility(View.INVISIBLE);
        star[5].setVisibility(View.INVISIBLE);

    }


                                        /*.................................................*/


                                                /* Random text generatin */
    private void appreciate() {

        int rand = (int)(Math.random()*5)+1;
        switch (rand){
            case 1:tts.speak("Incredible",TextToSpeech.QUEUE_FLUSH,null,null);break;
            case 2:tts.speak("Good",TextToSpeech.QUEUE_FLUSH,null,null);break;
            case 3:tts.speak("Nice",TextToSpeech.QUEUE_FLUSH,null,null);break;
            case 4:tts.speak("Awesome",TextToSpeech.QUEUE_FLUSH,null,null);break;
            case 5:tts.speak("Outstanding",TextToSpeech.QUEUE_FLUSH,null,null);break;
        }

        showstar();

    }
    private void tryitagain(){
        int rand = (int)(Math.random()*5)+1;
        switch (rand){
            case 1:tts.speak("Not correct",TextToSpeech.QUEUE_FLUSH,null,null);break;
            case 2:tts.speak("Wrong",TextToSpeech.QUEUE_FLUSH,null,null);break;
            case 3:tts.speak("Try Again",TextToSpeech.QUEUE_FLUSH,null,null);break;
            case 4:tts.speak("Nice Try",TextToSpeech.QUEUE_FLUSH,null,null);break;
            case 5:tts.speak("sorry it is "+cname,TextToSpeech.QUEUE_FLUSH,null,null);break;
        }
    }

                                        /*..................................................*/


                                                      /*setting the layout*/

    public void setColor(ImageView i,int id,int v){
        i.setBackgroundColor(Color.parseColor(colourdatas[id].color));
        switch (v){
            case 1:
                i1cname = colourdatas[id].colorname;
                break;
            case 2:
                i2cname = colourdatas[id].colorname;
                break;
            case 3:
                i3cname = colourdatas[id].colorname;
                break;
            case 4:
                i4cname = colourdatas[id].colorname;
        }
    }



    /* randomizing all, image and box colours */

    private void findrand(){
        int id = (int)(Math.random()*dataavailable)+1;
        String name = data[id].name;
        String color = data[id].color;
        cname = data[id].colorname;
        int imageid = getResources().getIdentifier(name,"drawable",getPackageName());
        imageView.setImageResource(imageid);

        int boxid = (int)(Math.random()*4)+1;
        switch (boxid){
            case 1:
                mainimage = (ImageView)findViewById(R.id.ic1);
                i1cname = cname;
                break;
            case 2:
                mainimage = (ImageView)findViewById(R.id.ic2);
                i2cname = cname;
                break;
            case 3:
                mainimage = (ImageView)findViewById(R.id.ic3);
                i3cname = cname;
                break;
            case 4:
                mainimage = (ImageView)findViewById(R.id.ic4);
                i4cname = cname;
                break;
        }
        mainimage.setBackgroundColor(Color.parseColor(color));

        int colorid = 1;
        while( !colourdatas[colorid].colorname.equals(data[id].colorname) ){
            colorid+=1;
        }
        for( int i=1;i<=4;i++ ){
            if( i==boxid ){
                continue;
            }
            switch (i){
                case 1:
                    setColor(im1,(colorid+1)%10+1,1);
                    break;
                case 2:
                    setColor(im2,(colorid+2)%10+1,2);
                    break;
                case 3:
                    setColor(im3,(colorid+3)%10+1,3);
                    break;
                case 4:
                    setColor(im4,(colorid+4)%10+1,4);
                    break;
            }
        }
    }

//    Animatioins

    public void fadein(View view) {

        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        view.setAnimation(fade_in);
    }

    public void moveImageView(View view, float toX, float toY, int duration) {

        Animation rotate_anim = AnimationUtils.loadAnimation(this, R.anim.rotation);
        view.setAnimation(rotate_anim);

        view.animate()
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .translationX(toX)
                .translationY(toY)
                .setDuration(duration);
    }

    public void setstarsize() {

        ViewGroup.LayoutParams sizerules = star[wincount].getLayoutParams();
        sizerules.width = 200;
        sizerules.height = 220;
        star[wincount].setLayoutParams(sizerules);

    }

    public void r() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                moveImageView(star[wincount], x, y, 1000);
                setstarsize();
            }
        }, 1000);
    }

    public void fade_anim() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=wincount;i++)
                    fadein(star[i]);

                handler.postDelayed(this, 1000);
            }
        }, 10000);
    }

    private void showstar() {

        y = -750;
        switch(wincount) {

            case 1:

                star[1].setVisibility(View.VISIBLE);
                fadein(star[1]);
                x = -410;
                r();
                break;

            case 2:

                star[2].setVisibility(View.VISIBLE);
                fadein(star[2]);
                x = x+200;
                r();
                break;

            case 3:

                star[3].setVisibility(View.VISIBLE);
                fadein(star[3]);
                x = x+200;
                r();
                break;

            case 4:

                star[4].setVisibility(View.VISIBLE);
                fadein(star[4]);
                x = x+200;
                r();
                break;

            case 5:

                star[5].setVisibility(View.VISIBLE);
                fadein(star[5]);
                x = x+200;
                r();
                break;

        }

    }



                                    /*..................................................*/
}
