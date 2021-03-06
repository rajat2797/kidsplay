package com.mystical.colors.play;


import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class homepage extends AppCompatActivity{

    private static final int INSTRUCTIONS = 500;
    collection[] data;
    colourdata[] colourdatas;
    String[] letsdance;
    private boolean reflag;
    int wincount = 0;
    private final int dataavailable = 50;
    private int try_count = 0;
    ImageView[] star;
    float x,y;
    private float starheight,starwidth;
    private boolean f=false;
    private List<Integer> idList = new ArrayList<Integer>();


    DisplayMetrics displayMetrics;
    int width;

    TextToSpeech tts;
    String cname,i1cname,i2cname,i3cname,i4cname;

    private ImageView imageView,im1,im2,im3,im4,mainimage,hand;
    private ViewGroup viewGroup;
    private int marleft,marright,martop,marbottom;
    private int xcor;
    private int ycor;
    private boolean flag;

    int initialx,initialy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        /*
        SharedPreferences preferences = getSharedPreferences("prefs", 0);
        boolean first = preferences.getBoolean("first", true);
        if(first){
            startActivityForResult(new Intent(this, IntroActivity.class),INSTRUCTIONS);
        }
        */
        data = new collection[100];
        colourdatas = new colourdata[20];
        letsdance = new String[7];
        initializecolor();
        initializedances();
        initialize();
        initialisestars();

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if( i==TextToSpeech.SUCCESS ){
                    tts.setLanguage(new Locale("en","IN"));
                    tts.setPitch(1.30f);
                    tts.setSpeechRate(0.70f);
                }else{
                    Toast.makeText(homepage.this,"Sorry Bro",Toast.LENGTH_SHORT).show();
                }
            }
        });
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        viewGroup = (ViewGroup)findViewById(R.id.view_root);
        imageView = (ImageView)findViewById(R.id.imageView);
        im1 = (ImageView)findViewById(R.id.ic1);
        im2 = (ImageView)findViewById(R.id.ic2);
        im3 = (ImageView)findViewById(R.id.ic3);
        im4 = (ImageView)findViewById(R.id.ic4);
        reflag = false;
        hand = (ImageView) findViewById(R.id.hand);
        adjustlayout();
        findrand();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width+50,width+50);
        layoutParams.leftMargin = displayMetrics.widthPixels/2-(width/2)-50;
        layoutParams.topMargin = displayMetrics.heightPixels/2-width*2;
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
            Log.d("Line 119 x = ", "" + X);
            Log.d("Line 120 y = ", "" + Y);
//            if(X>=mainimage.getX() &&
//                    Y>=mainimage.getY() && Y<=mainimage.getY() + mainimage.getHeight()){
//                Toast.makeText(getApplicationContext(), "Correct Answer", Toast.LENGTH_SHORT).show();
//                correct_while_dragging();
//            }
            switch (event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    speak(cname);
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)view.getLayoutParams();
                    xcor = X-lp.leftMargin;
                    ycor = Y-lp.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
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

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == INSTRUCTIONS){
            SharedPreferences settings = getSharedPreferences("prefs", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("first", false);
            editor.commit();
            Toast.makeText(this ,"" + settings.getBoolean("first", true), Toast.LENGTH_SHORT).show();
        }
    }
    */
//    private void correct_while_dragging(){
//        flag=false;
//        try_count = 0;
//        wincount+=1;
//        appreciate();
//        if( wincount==5 ) {
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    showdance();
//                    wincount = 0;
//                    changeimage();
//                    changeposition();
//                }
//            }, 2300);
//        }
//        else{
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    changeimage();
//                    changeposition();
//                }
//            }, 2300);
//        }
//    }


                                            /* Check for win or loose */
    /* checking win */

    private void checklocation(final RelativeLayout.LayoutParams checkpar){
        if( flag==false ){
            setMarginOfViews(mainimage);
            flag = true;
        }
        if( checkpar.topMargin+(width/2)>=martop
                &&(checkpar.leftMargin+(width/2)>=marleft&&checkpar.leftMargin+(width/2)<=marright)
                ){
            flag=false;
            try_count = 0;
            wincount+=1;
            appreciate();
            if( wincount==5 ) {
                idList.clear();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showdance();
                        wincount = 0;
                    }
                }, 2800);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeimage();
                        changeposition();
                    }
                }, 8800);

            }
            else{
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        changeimage();
                        changeposition();
                    }
                }, 2300);
            }
        }
        else{

            if(checkpar.topMargin+250<martop){
                speak("Drag carefully");
            }
            else{
                try_count = try_count + 1;
                if(try_count >= 2) {
                    guide();
                }
                tryitagain();
            }
            changeposition();

        }
    }

    private void guide() {

        final FrameLayout dancefrag;
        dancefrag = (FrameLayout)findViewById(R.id.dancefrag);
        dancefrag.setVisibility(View.VISIBLE);
        dancefrag.setBackgroundColor(Color.TRANSPARENT);
        ImageView danceimage = (ImageView)findViewById(R.id.artimage);
        danceimage.setBackgroundColor(Color.TRANSPARENT);

        Handler handler = new Handler();
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                fadein(hand);
                hand.setVisibility(View.VISIBLE);
                hand.setX(imageView.getX());
                hand.setY(imageView.getY());
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                hand.animate()
                        .setDuration(3000)
                        .translationX(mainimage.getX())
                        .translationY(mainimage.getY());
            }
        };
        Runnable r3 = new Runnable() {
            @Override
            public void run() {
                fadeout(hand);
                hand.setVisibility(View.INVISIBLE);
                dancefrag.setVisibility(View.INVISIBLE);
                dancefrag.setBackgroundColor(Color.parseColor("#ca2e2c2c"));
            }
        };
        handler.postDelayed(r1, 1000);
        handler.postDelayed(r2, 2000);
        handler.postDelayed(r3, 4800);

    }

    /* changing things when won */
    private void changeimage(){
        findrand();
    }

    /* reverting to initial position */
    private void changeposition(){
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width+50,width+50);
        layoutParams.leftMargin = displayMetrics.widthPixels/2-(width/2)-50;
        layoutParams.topMargin = displayMetrics.heightPixels/2-width*2;
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
        repositionstarts();
    }
    void repositionstarts(){
        star[1].getLayoutParams().height = (int)starheight;
        star[1].getLayoutParams().width = (int)starwidth;
        star[1].requestLayout();
        star[1].setX(initialx);
        star[1].setY(initialy);
        star[1].setVisibility(View.INVISIBLE);

        star[2].getLayoutParams().height = (int)starheight;
        star[2].getLayoutParams().width = (int)starwidth;
        star[2].requestLayout();
        star[2].setX(initialx);
        star[2].setY(initialy);
        star[2].setVisibility(View.INVISIBLE);

        star[3].getLayoutParams().height = (int)starheight;
        star[3].getLayoutParams().width = (int)starwidth;
        star[3].requestLayout();
        star[3].setX(initialx);
        star[3].setY(initialy);
        star[3].setVisibility(View.INVISIBLE);

        star[4].getLayoutParams().height = (int)starheight;
        star[4].getLayoutParams().width = (int)starwidth;
        star[4].requestLayout();
        star[4].setX(initialx);
        star[4].setY(initialy);
        star[4].setVisibility(View.INVISIBLE);

        star[5].getLayoutParams().height = (int)starheight;
        star[5].getLayoutParams().width = (int)starwidth;
        star[5].requestLayout();
        star[5].setX(initialx);
        star[5].setY(initialy);
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
        else {
            speak(i1cname);
        }
    }
    public void callim2(View v){
        FrameLayout dancefrag;
        dancefrag = (FrameLayout)findViewById(R.id.dancefrag);
        if(dancefrag.getVisibility()==View.VISIBLE){
            return;
        }
        else {
            speak(i2cname);
        }
    }
    public void callim3(View v){
        FrameLayout dancefrag;
        dancefrag = (FrameLayout)findViewById(R.id.dancefrag);
        if(dancefrag.getVisibility()==View.VISIBLE){
            return;
        }
        else {
            speak(i3cname);
        }
    }
    public void callim4(View v){
        FrameLayout dancefrag;
        dancefrag = (FrameLayout)findViewById(R.id.dancefrag);
        if(dancefrag.getVisibility()==View.VISIBLE){
            return;
        }
        else {
            speak(i4cname);
        }
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
        data[1].name="a01";data[1].color="#32cd32";data[1].colorname="Green";data[1].objectName="Chair";
        data[27].name="a27";data[27].color="#5F9B23";data[27].colorname="Green";data[27].objectName="Leaf";
        data[34].name="a34";data[34].color="#788D27";data[34].colorname="Green";data[34].objectName="Scissor";
        data[40].name="a40";data[40].color="#32cd32";data[40].colorname="Green";data[40].objectName="Cauliflower";
        data[45].name="a45";data[45].color="#32cd32";data[45].colorname="Green";data[45].objectName="Hat";

        data[2].name="a02";data[2].color="#ff0000";data[2].colorname="Red";data[2].objectName="Sofa";
        data[23].name="a23";data[23].color="#ff0000";data[23].colorname="Red";data[23].objectName="Strawberry";
        data[33].name="a33";data[33].color="#ff0000";data[33].colorname="Red";data[33].objectName="An Apple";
        data[38].name="a38";data[38].color="#ff0000";data[38].colorname="Red";data[38].objectName="An Umbrella";
        data[49].name="a49";data[49].color="#ff0000";data[49].colorname= "Red";data[49].objectName="Shoe";

        data[3].name="a03";data[3].color="#1e90ff";data[3].colorname="Blue";data[3].objectName="Balloon";
        data[36].name="a36";data[36].color="#1E45CD";data[36].colorname="Blue";data[36].objectName="Sofa";
        data[41].name="a41";data[41].color="#1e90ff";data[41].colorname="Blue";data[41].objectName="An Antique Bottle";
        data[47].name="a47";data[47].color="#1e90ff";data[47].colorname="Blue";data[47].objectName="Pair of Shoes";
        data[48].name="a48";data[48].color="#1e90ff";data[48].colorname="Blue";data[48].objectName="Car";

        data[4].name="a04";data[4].color="#fa7305";data[4].colorname="Orange";data[4].objectName="Orange";
        data[37].name="a37";data[37].color="#fa7305";data[37].colorname="Orange";data[37].objectName="Scissor";
        data[42].name="a42";data[42].color="#fa7305";data[42].colorname="Orange";data[42].objectName="Fish";
        data[44].name="a44";data[44].color="#D96937";data[44].colorname="Orange";data[44].objectName="Man";
        data[50].name="a50";data[50].color="#fa7305";data[50].colorname="Orange";data[50].objectName="Cold Drink";

        data[5].name="a05";data[5].color="#ffff00";data[5].colorname="Yellow";data[5].objectName="Banana";
        data[18].name="a18";data[18].color="#ffff00";data[18].colorname="Yellow";data[18].objectName="Sunflower";
        data[39].name="a39";data[39].color="#F8D41F";data[39].colorname="Yellow";data[39].objectName="Pikachu";
        data[43].name="a43";data[43].color="#FDFD33";data[43].colorname="Yellow";data[43].objectName="Cheese";
        data[46].name="a46";data[46].color="#ffff00";data[46].colorname="Yellow";data[46].objectName="Board Pin";

        data[6].name="a06";data[6].color="#553426";data[6].colorname="Brown";data[6].objectName="Hat";
        data[7].name="a07";data[7].color="#553426";data[7].colorname="Brown";data[7].objectName="Snail";
        data[10].name="a10";data[10].color="#AC4701";data[10].colorname="Brown";data[10].objectName="Rugby Ball";
        data[12].name="a12";data[12].color="#A06121";data[12].colorname="Brown";data[12].objectName="Teddy Bear";
        data[14].name="a14";data[14].color="#691C02";data[14].colorname="Brown";data[14].objectName="Violin";

        data[8].name="a08";data[8].color="#020204";data[8].colorname="Black";data[8].objectName="Ball";
        data[11].name="a11";data[11].color="#010101";data[11].colorname="Black";data[11].objectName="T-Shirt";
        data[13].name="a13";data[13].color="#010101";data[13].colorname="Black";data[13].objectName="Vase";
        data[15].name="a15";data[15].color="#010101";data[15].colorname="Black";data[15].objectName="Bird";
        data[20].name="a20";data[20].color="#010101";data[20].colorname="Black";data[20].objectName="Crow";

        data[9].name="a09";data[9].color="#FB87BB";data[9].colorname="Pink";data[9].objectName="Flamingo";
        data[17].name="a17";data[17].color="#F6A5D5";data[17].colorname="Pink";data[17].objectName="Baby Elephant";
        data[21].name="a21";data[21].color="#FCAFE2";data[21].colorname="Pink";data[21].objectName="Kirby";
        data[24].name="a24";data[24].color="#DA788D";data[24].colorname="Pink";data[24].objectName="Pink Rose";
        data[30].name="a30";data[30].color="#D74C8F";data[30].colorname="Pink";data[30].objectName="Mario";

        data[16].name="a16";data[16].color="#691D7E";data[16].colorname="Purple";data[16].objectName="An Eggplant";
        data[19].name="a19";data[19].color="#a709f6";data[19].colorname="Purple";data[19].objectName="Butterfly";
        data[25].name="a25";data[25].color="#69349D";data[25].colorname="Purple";data[25].objectName="Spectacles";
        data[28].name="a28";data[28].color="#753078";data[28].colorname="Purple";data[28].objectName="Sandal";
        data[32].name="a32";data[32].color="#331940";data[32].colorname="Purple";data[32].objectName="Kettle";

        data[22].name="a22";data[22].color="#8E8C8D";data[22].colorname="Grey";data[22].objectName="T-Shirt";
        data[26].name="a26";data[26].color="#7F7F7F";data[26].colorname="Grey";data[26].objectName="An Elephant";
        data[29].name="a29";data[29].color="#51566A";data[29].colorname="Grey";data[29].objectName="Grey Parrot";
        data[31].name="a31";data[31].color="#949494";data[31].colorname="Grey";data[31].objectName="Racoon";
        data[35].name="a35";data[35].color="#AEAEAE";data[35].colorname="Grey";data[35].objectName="Shark";
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
   /*initialising stars .......................... only once */
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
                                                    /* Layout Adjusting */
    private void adjustlayout(){
        width = (displayMetrics.widthPixels-110)/4;
        /* taking a square box ==> width = height */
        ViewGroup.MarginLayoutParams marginLayoutParams;
        marginLayoutParams = (ViewGroup.MarginLayoutParams) im1.getLayoutParams();
        marginLayoutParams.leftMargin = 20;
        im1.getLayoutParams().width=width;im1.getLayoutParams().height=width;
        marginLayoutParams = (ViewGroup.MarginLayoutParams) im2.getLayoutParams();
        marginLayoutParams.leftMargin = 10;
        im2.getLayoutParams().width=width;im2.getLayoutParams().height=width;
        marginLayoutParams = (ViewGroup.MarginLayoutParams) im3.getLayoutParams();
        marginLayoutParams.leftMargin = 10;
        im3.getLayoutParams().width=width;im3.getLayoutParams().height=width;
        marginLayoutParams = (ViewGroup.MarginLayoutParams) im4.getLayoutParams();
        marginLayoutParams.leftMargin = 10;
        im4.getLayoutParams().width=width;im4.getLayoutParams().height=width;

        ImageView rating = (ImageView)findViewById(R.id.ratingbg);
        rating.getLayoutParams().height = displayMetrics.widthPixels/5;
    }
                                        /*.................................................*/


                                                /* Random text generatin */
    private void appreciate() {

        int rand = (int)(Math.random()*5)+1;
        switch (rand){
            case 1:speak("Incredible");break;
            case 2:speak("Good Work");break;
            case 3:speak("Nice");break;
            case 4:speak("Awesome");break;
            case 5:speak("Outstanding");break;
        }

        showstar();

    }
    private void tryitagain(){
        int rand = (int)(Math.random()*5)+1;
        switch (rand){
            case 1:speak("Incorrect");break;
            case 2:speak("Wrong");break;
            case 3:speak("Try Again");break;
            case 4:speak("Nice Try");break;
            case 5:speak("sorry it is "+ cname);break;
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
        int id = -1, j;
        boolean f=false;
        while(!f){
            f = true;
            id = (int)(Math.random()*dataavailable)+1;
            if(idList!=null){
                for(j=0;j<idList.size();j++){
                    if(id == idList.get(j)){
                        f = false;
                        break;
                    }
                }
            }
        }

        idList.add(id);
        String name = data[id].name;
        String color = data[id].color;
        String objectName = data[id].objectName;
        speak(objectName);
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
                                /*....................................*/

                                            /* Animatioins */

    public void fadein(View view) {
        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        view.setAnimation(fade_in);
    }

    private void fadeout(View view) {
        Animation fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        view.setAnimation(fade_out);
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
        sizerules.width = (displayMetrics.widthPixels-20)/5;
        sizerules.height = (displayMetrics.widthPixels-20)/5;
        star[wincount].setLayoutParams(sizerules);
    }

    private void showstar() {
        ImageView imageView;
        Handler handler = new Handler();
        switch(wincount) {
            case 1:
                imageView = (ImageView)findViewById(R.id.star1);
                if (reflag)
                    repositionstarts();
                else {
                    starheight = star[1].getHeight();
                    starwidth = star[1].getWidth();
                    reflag = true;
                }
                initialx = (int) imageView.getX();
                initialy = (int) imageView.getY();
                y = -imageView.getY();
                x = -imageView.getX()-displayMetrics.widthPixels/10;
                star[1].setVisibility(View.VISIBLE);
                fadein(star[1]);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moveImageView(star[wincount], x, y, 1000);
                        setstarsize();
                    }
                }, 1500);

                break;

            case 2:
                imageView = (ImageView)findViewById(R.id.star2);
                y = -imageView.getY();
                x = x+displayMetrics.widthPixels/5-5;
                star[2].setVisibility(View.VISIBLE);
                fadein(star[2]);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moveImageView(star[wincount], x, y, 1000);
                        setstarsize();
                    }
                }, 1500);
                break;

            case 3:
                star[3].setVisibility(View.VISIBLE);
                fadein(star[3]);
                imageView = (ImageView)findViewById(R.id.star3);
                y = -imageView.getY();
                x = x+displayMetrics.widthPixels/5-5;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moveImageView(star[wincount], x, y, 1000);
                        setstarsize();
                    }
                }, 1500);
                break;

            case 4:
                star[4].setVisibility(View.VISIBLE);
                fadein(star[4]);
                imageView = (ImageView)findViewById(R.id.star4);
                y = -imageView.getY();
                x = x+displayMetrics.widthPixels/5-5;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moveImageView(star[wincount], x, y, 1000);
                        setstarsize();
                    }
                }, 1500);
                break;

            case 5:
                star[5].setVisibility(View.VISIBLE);
                fadein(star[5]);
                imageView = (ImageView)findViewById(R.id.star5);
                y = -imageView.getY();
                x = x+displayMetrics.widthPixels/5-5;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moveImageView(star[wincount], x, y, 1000);
                        setstarsize();
                    }
                }, 1500);
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        hand.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void speak( String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(text);
        } else {
            ttsUnder20(text);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
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
}
