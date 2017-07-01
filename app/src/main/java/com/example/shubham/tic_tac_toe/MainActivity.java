package com.example.shubham.tic_tac_toe;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private SoundPool mSoundPool;
    private int clickSound,winSound;
    private AudioManager mAudioManager;
    ImageButton  box1,box2,box3,box4,box5,box6,box7,box8,box9;
    ImageButton b[]={box1,box2,box3,box4,box5,box6,box7,box8,box9};
    TextView winnerText,xwin,owin,xleft,oleft;
    ImageView grid;
    Button newGame,reset;
    boolean turn=true,turn2=false;
    int countx=0,counto=0,lastClicked=-1,winner=0,xwinc=0,owinc=0;
    int a[]={0,0,0,0,0,0,0,0,0};
    String playerx,playero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle data =getIntent().getExtras();
        if(data==null)
            return;
        playerx=data.getString("playerx");
        if(playerx.matches(""))
            playerx="PlayerX";
        playero=data.getString("playero");
        if(playero.matches(""))
            playero="PlayerO";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        clickSound = mSoundPool.load(this, R.raw.click,1);
        winSound = mSoundPool.load(this,R.raw.win,1);

        b[0] =(ImageButton)findViewById(R.id.box1);
        b[1] =(ImageButton)findViewById(R.id.box2);
        b[2] =(ImageButton)findViewById(R.id.box3);
        b[3] =(ImageButton)findViewById(R.id.box4);
        b[4] =(ImageButton)findViewById(R.id.box5);
        b[5] =(ImageButton)findViewById(R.id.box6);
        b[6] =(ImageButton)findViewById(R.id.box7);
        b[7] =(ImageButton)findViewById(R.id.box8);
        b[8] =(ImageButton)findViewById(R.id.box9);
        newGame = (Button)findViewById(R.id.newGame);
        reset = (Button)findViewById(R.id.reset);
        winnerText = (TextView) findViewById(R.id.winnerText);
        xwin = (TextView) findViewById(R.id.xwin);
        owin = (TextView) findViewById(R.id.owin);
        xleft = (TextView) findViewById(R.id.xleft);
        oleft = (TextView) findViewById(R.id.oleft);
        grid = (ImageView) findViewById(R.id.grid);
        clickedReset(reset);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSoundPool.release();
        mSoundPool=null;
    }

    public void clickedReset(View view)
    {
        clickedNewGame(newGame);
        xwinc=0;
        owinc=0;
        xwin.setText(playerx +" : " + xwinc);
        owin.setText(playero +" : " + owinc);

    }
    public void clickedNewGame(View view)
    {
        countx=0;
        counto=0;
        winner=0;
        if(turn==true)
            winnerText.setText(playerx+"'s turn");
        else
            winnerText.setText(playero+"'s turn");
        xleft.setText("left: " + (3-countx));
        oleft.setText("left: " + (3-counto));
        grid.setImageResource(R.drawable.grid);
        for(int i=0;i<9;i++)
        {
            b[i].setClickable(true);
            b[i].setImageResource(R.drawable.transparent);
            a[i]=0;
        }
    }
    public void clickedBox1(View view) { clickedBox(0); }
    public void clickedBox2(View view)
    {
        clickedBox(1);
    }
    public void clickedBox3(View view)
    {
        clickedBox(2);
    }
    public void clickedBox4(View view)
    {
        clickedBox(3);
    }
    public void clickedBox5(View view)
    {
        clickedBox(4);
    }
    public void clickedBox6(View view)
    {
        clickedBox(5);
    }
    public void clickedBox7(View view)
    {
        clickedBox(6);
    }
    public void clickedBox8(View view)
    {
        clickedBox(7);
    }
    public void clickedBox9(View view)
    {
        clickedBox(8);
    }

    public void clickedBox(int n){
        if( (counto<3||countx<3) && a[n]==0 && winner==0)
        {
            if(turn==true)
            {
                b[n].setImageResource(R.drawable.xcoin);
                a[n]=2;
                countx++;
                winnerText.setText(playero+"'s turn");
                xleft.setText("left: " + (3-countx));
                oleft.setText("left: " + (3-counto));
            }
            else
            {
                b[n].setImageResource(R.drawable.ocoin);
                a[n]=1;
                counto++;
                winnerText.setText(playerx+"'s turn");
                xleft.setText("left: " + (3-countx));
                oleft.setText("left: " + (3-counto));
            }
            playSound(clickSound);
            turn=!turn;
        }
        else if(counto==3 && countx==3 && winner==0)
        {
            if(!turn2)
            {
                if(a[n]==2 && turn==true)
                {
                    lastClicked=n;
                    turn2=true;
                    makeXfaded();
                    playSound(clickSound);
                }
                else if(a[n]==1 && turn==false)
                {
                    lastClicked=n;
                    turn2=true;
                    makeOfaded();
                    playSound(clickSound);
                }
            }
            else
            {
                if(a[n]==1 || a[n]==2)
                {
                    if(a[n]==2 && turn==true)
                    {
                        removeFaded();
                        lastClicked=n;
                        makeXfaded();
                        playSound(clickSound);
                    }
                    else if(a[n]==1 && turn==false)
                    {
                        removeFaded();
                        lastClicked=n;
                        makeOfaded();
                        playSound(clickSound);
                    }
                }
                else
                {
                    int c[]={lastClicked+1,lastClicked-1,lastClicked-3,lastClicked+3};
                    for(int i=0;i<4;i++)
                    {
                        if(n==c[i] && a[n]==3)
                        {
                            removeFaded();
                            a[lastClicked]=0;
                            b[lastClicked].setImageResource(R.drawable.transparent);
                            if(turn==true)
                            {
                                b[n].setImageResource(R.drawable.xcoin);
                                a[n]=2;
                                winnerText.setText(playero+"'s turn");
                            }
                            else
                            {
                                b[n].setImageResource(R.drawable.ocoin);
                                a[n]=1;
                                winnerText.setText(playerx+"'s turn");
                            }
                            turn=!turn;
                            playSound(clickSound);
                            turn2=false;
                            break;
                        }
                    }
                }
            }
        }

        if(a[1]==a[2] && a[1]==a[0] && a[1]!=0)
        {
            winner=a[1];
            grid.setImageResource(R.drawable.line1h);
        }
        if(a[3]==a[4] && a[5]==a[3] && a[4]!=0)
        {
            winner=a[4];
            grid.setImageResource(R.drawable.line2h);
        }
        if(a[6]==a[7] && a[7]==a[8] && a[6]!=0)
        {
            winner=a[6];
            grid.setImageResource(R.drawable.line3h);
        }
        if(a[0]==a[3] && a[0]==a[6] && a[0]!=0)
        {
            winner=a[0];
            grid.setImageResource(R.drawable.line1v);
        }
        if(a[1]==a[4] && a[1]==a[7] && a[1]!=0)
        {
            winner=a[1];
            grid.setImageResource(R.drawable.line2v);
        }
        if(a[5]==a[2] && a[2]==a[8] && a[2]!=0)
        {
            winner=a[2];
            grid.setImageResource(R.drawable.line3v);
        }
        if(a[0]==a[4] && a[0]==a[8] && a[4]!=0)
        {
            winner=a[4];
            grid.setImageResource(R.drawable.line1d);
        }
        if(a[2]==a[4] && a[2]==a[6] && a[4]!=0)
        {
            winner =a[4];
            grid.setImageResource(R.drawable.line2d);
        }
        if(winner>0)
        {
            if(winner==1)
            {
                owinc++;
                winnerText.setText(playero+" WINS");
            }
            else
            {
                xwinc++;
                winnerText.setText(playerx+" WINS");
            }
            playSound(winSound);
            xwin.setText(playerx +" : " + xwinc);
            owin.setText(playero +" : " + owinc);
            for(int i=0;i<9;i++)
                b[i].setClickable(false);
        }
    }
    public void makeXfaded()
    {
        int c[]={lastClicked+1,lastClicked-1,lastClicked-3,lastClicked+3};
        if(lastClicked==3 || lastClicked==6)
            c[1]=c[0];
        else if(lastClicked==2 || lastClicked==5)
            c[0]=c[1];
        for(int i:c)
        {
            if(i>=0 && i<9 && a[i]==0)
            {
                a[i]=3;
                b[i].setImageResource(R.drawable.xfaded);
            }
        }
    }
    public void makeOfaded()
    {
        int c[]={lastClicked+1,lastClicked-1,lastClicked-3,lastClicked+3};
        if(lastClicked==3 || lastClicked==6)
            c[1]=c[0];
        else if(lastClicked==2 || lastClicked==5)
            c[0]=c[1];
        for(int i:c)
        {
            if(i>=0 && i<9 && a[i]==0)
            {
                a[i]=3;
                b[i].setImageResource(R.drawable.ofaded);
            }
        }
    }
    public void removeFaded()
    {
        for(int i=0;i<9;i++)
        {
            if(a[i]==3)
            {
                a[i]=0;
                b[i].setImageResource(R.drawable.transparent);
            }
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createNewSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    protected void createOldSoundPool(){
        mSoundPool = new SoundPool(5,AudioManager.STREAM_MUSIC,0);
    }
    public void playSound(int soundId)
    {
        float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        streamVolume = streamVolume/ mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mSoundPool.play(soundId,streamVolume,streamVolume,1,0,1f);
    }
}