package com.company.animation;

import com.unity3d.player.*;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class UnityPlayerActivity extends Activity
{
    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code

    /*********************************************
     * written by team 4C4C
     * lastest update: 19-08-20
     * command info; CICT: College of ICT, CE: College of Economics, LAW: School of Law
     * Under this line, they are initial variables.
     *********************************************/

    // ------------------- Common variables ------------------- //
    FrameLayout fl_forUnity;
    ImageView cloud;
    ImageButton mBackButton;
    TextToSpeech tts;
    final Handler handler = new Handler();

    // ------------------- College of ICT ------------------- //
    TextView cText;
    ImageButton cLeftButton, cRightButton;
    LinearLayout cButtons;
    Button cBackButton, cInfoButton, cVisionButton, cCourseButton;
    int cictIdx = 0, cInfoIdx = 0, cVisionIdx = 0, cCourseIdx = 0;

    // ------------------- College of Economics ------------------- //
    TextView eText;
    ImageButton eLeftButton, eRightButton;
    LinearLayout eButtons;
    Button eBackButton, eInfoButton, eVisionButton, eCourseButton;
    int eIdx = 0, eInfoIdx = 0, eVisionIdx = 0, eCourseIdx = 0;

    // ------------------- School of Law ------------------- //
    TextView lText;
    ImageButton lLeftButton, lRightButton;
    LinearLayout lButtons;
    Button lBackButton, lInfoButton, lMissionButton, lCourseButton;
    int lIdx = 0, lInfoIdx = 0, lMissionIdx = 0, lCourseIdx = 0;


    // Setup activity layout
    @Override protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        /***********************************************************************************
         // ------------------- Under This line, Setting findViewById -------------------- //
         ***********************************************************************************/
        mUnityPlayer = new UnityPlayer(this);
        setContentView(R.layout.layout_main);

        Intent intent = getIntent();
        Toast.makeText(this, intent.getStringExtra("code"), Toast.LENGTH_SHORT).show();
        String buildingCode = intent.getStringExtra("code");

        this.fl_forUnity = (FrameLayout) findViewById(R.id.fl_forUnity);
        this.fl_forUnity.addView(mUnityPlayer.getView(), FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        this.cloud = (ImageView) findViewById(R.id.cloud);
        this.mBackButton = (ImageButton) findViewById(R.id.mBackButton);

        // ------------------------ CICT findViewById ----------------------- //
        this.cText = (TextView) findViewById(R.id.cText);
        this.cLeftButton = (ImageButton) findViewById(R.id.cLeftButton);
        this.cRightButton = (ImageButton) findViewById(R.id.cRightButton);
        this.cButtons = (LinearLayout) findViewById(R.id.cButtons);
        this.cInfoButton = (Button) findViewById(R.id.cInfoButton);
        this.cVisionButton = (Button) findViewById(R.id.cVisionButton);
        this.cCourseButton = (Button) findViewById(R.id.cCourseButton);
        this.cBackButton = (Button) findViewById(R.id.cBackButton);

        // ------------------------ CE findViewById ----------------------- //
        this.eText = (TextView) findViewById(R.id.eText);
        this.eLeftButton = (ImageButton) findViewById(R.id.eLeftButton);
        this.eRightButton = (ImageButton) findViewById(R.id.eRightButton);
        this.eButtons = (LinearLayout) findViewById(R.id.eButtons);
        this.eInfoButton = (Button) findViewById(R.id.eInfoButton);
        this.eVisionButton = (Button) findViewById(R.id.eVisionButton);
        this.eCourseButton = (Button) findViewById(R.id.eCourseButton);
        this.eBackButton = (Button) findViewById(R.id.eBackButton);

        // ------------------------ CL findViewById ----------------------- //
        this.lText = (TextView) findViewById(R.id.lText);
        this.lLeftButton = (ImageButton) findViewById(R.id.lLeftButton);
        this.lRightButton = (ImageButton) findViewById(R.id.lRightButton);
        this.lButtons = (LinearLayout) findViewById(R.id.lButtons);
        this.lInfoButton = (Button) findViewById(R.id.lInfoButton);
        this.lMissionButton = (Button) findViewById(R.id.lMissionButton);
        this.lCourseButton = (Button) findViewById(R.id.lCourseButton);
        this.lBackButton = (Button) findViewById(R.id.lBackButton);

        // --------------- text To speech initial setting ----------------- //
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                    } else {
                        tts.setPitch(0.5f);
                    }
                }
                else {
                    Log.e("error", "Initialization Failed!");
                }
            }
        });

        // ------------- CICT String relative variables ------------- //

        String CICT1 = getString(R.string.CICT1);
        String[] cSplit = CICT1.split(";");
        // final int cSize = cSplit.length;

        String CICT_info = getString(R.string.CICT_info);
        String[] cInfoSplit = CICT_info.split(";");
        // final int cInfoSize = cInfoSplit.length;

        String CICT_vision = getString(R.string.CICT_vision);
        String[] cVisionSplit = CICT_vision.split(";");
        // int cVisionSize = cVisionSplit.length;

        String CICT_course = getString(R.string.CICT_course);
        String[] cCourseSplit = CICT_course.split(";");
        // int cCourseSize = cCourseSplit.length;

        // ------------- Economics String relative variables ------------- //
        String CE1 = getString(R.string.CE1);
        String[] eSplit = CE1.split(";");

        String CE_info = getString(R.string.CE_info);
        String[] eInfoSplit = CE_info.split(";");

        String CE_vision = getString(R.string.CE_vision);
        String[] eVisionSplit = CE_vision.split(";");

        String CE_course = getString(R.string.CE_course);
        String[] eCourseSplit = CE_course.split(";");

        // ------------- Law String relative variables ------------- //
        String CL1 = getString(R.string.CL1);
        String[] lSplit = CL1.split(";");

        String CL_info = getString(R.string.CL_info);
        String[] lInfoSplit = CL_info.split(";");

        String CL_mission = getString(R.string.CL_mission);
        String[] lMissionSplit = CL_mission.split(";");

        String CL_course = getString(R.string.CL_course);
        String[] lCourseSplit = CL_course.split(";");

        /***********************************************************************************
         // ---------------- Under This line, Setting temporary Buttons ------------------ //
         ***********************************************************************************/

        if (buildingCode.equals("CICT")) {
            cLeftButton.setVisibility(View.VISIBLE);
            cRightButton.setVisibility(View.VISIBLE);

            cText.setVisibility(View.VISIBLE);
            cText.setText(cSplit[0]);

            cSpeech();
        }

        else if (buildingCode.equals("CE")) {
            eLeftButton.setVisibility(View.VISIBLE);
            eRightButton.setVisibility(View.VISIBLE);

            eText.setVisibility(View.VISIBLE);
            eText.setText(eSplit[0]);

            eSpeech();
        }

         else if (buildingCode.equals("LAW")) {
            lLeftButton.setVisibility(View.VISIBLE);
            lRightButton.setVisibility(View.VISIBLE);

            lText.setVisibility(View.VISIBLE);
            lText.setText(lSplit[0]);

            lSpeech();
        }

        /***********************************************************************************
         // ---------------------- Under This line, Setting Buttons ---------------------- //
         // ------------------------------------- CICT ----------------------------------- //
         ***********************************************************************************/
        cRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cictIdx == 0 || cictIdx == 1) {
                    if (cictIdx == 1) cButtons.setVisibility(View.VISIBLE);

                    cictIdx++;
                    cText.setText(cSplit[cictIdx]);

                    cSpeech();
                }
                // Info Mode
                else if (cictIdx == 3) {
                    if (cInfoIdx >= 0 && cInfoIdx < 2) {
                        if (cInfoIdx == 1) cBackButton.setVisibility(View.VISIBLE);

                        cInfoIdx++;
                        cText.setText(cInfoSplit[cInfoIdx]);

                        cSpeech();
                    }
                    else {

                    }
                }

                // vision mode
                else if (cictIdx == 4) {
                    if (cVisionIdx >= 0 && cVisionIdx < 5) {
                        if (cVisionIdx == 4) cBackButton.setVisibility(View.VISIBLE);

                        cVisionIdx++;
                        cText.setText(cVisionSplit[cVisionIdx]);

                        cSpeech();
                    }
                    else {

                    }
                }

                // course mode
                else if (cictIdx == 5) {
                    if (cCourseIdx >= 0 && cCourseIdx < 6) {
                        if (cCourseIdx == 5) cBackButton.setVisibility(View.VISIBLE);

                        cCourseIdx++;
                        cText.setText(cCourseSplit[cCourseIdx]);

                        cSpeech();
                    }
                    else {

                    }
                }
                else {

                }
            }
        });

        cLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cictIdx > 0 && cictIdx <= 2) {
                    if (cictIdx == 2) cButtons.setVisibility(View.INVISIBLE);

                    cictIdx--;
                    cText.setText(cSplit[cictIdx]);
                }

                // info mode
                else if (cictIdx == 3) {
                    if ( cInfoIdx > 0 && cInfoIdx <= 2) {
                        if (cInfoIdx == 2) cBackButton.setVisibility(View.INVISIBLE);

                        cInfoIdx--;
                        cText.setText(cInfoSplit[cInfoIdx]);
                    }
                    else if (cInfoIdx == 0) {
                        cictIdx = 2;
                        cText.setText(cSplit[cictIdx]);
                        cButtons.setVisibility(View.VISIBLE);
                    }
                    else {

                    }
                }

                // mission mode
                else if (cictIdx == 4) {
                    if (cVisionIdx > 0 && cVisionIdx <=5 ) {
                        if (cVisionIdx == 5) cBackButton.setVisibility(View.INVISIBLE);

                        cVisionIdx--;
                        cText.setText(cVisionSplit[cVisionIdx]);
                    }
                    else if (cVisionIdx == 0) {
                        cictIdx = 2;
                        cText.setText(cSplit[cictIdx]);
                        cButtons.setVisibility(View.VISIBLE);
                    }
                    else {

                    }
                }

                // course mode
                else if (cictIdx == 5) {
                    if (cCourseIdx > 0 && cCourseIdx <= 6) {
                        if (cCourseIdx == 6) cBackButton.setVisibility(View.INVISIBLE);

                        cCourseIdx--;
                        cText.setText(cCourseSplit[cCourseIdx]);
                    }
                    else if (cCourseIdx == 0) {
                        cictIdx = 2;
                        cText.setText(cSplit[cictIdx]);
                        cButtons.setVisibility(View.VISIBLE);
                    }
                    else {

                    }
                }
                else {

                }
            }
        });

        // ------------- specific button onClick method ------------- //
        cInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cictIdx = 3;
                cButtons.setVisibility(View.INVISIBLE);
                cText.setText(cInfoSplit[0]);

                cSpeech();
            }
        });

        cVisionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cictIdx = 4;
                cButtons.setVisibility(View.INVISIBLE);
                cText.setText(cVisionSplit[0]);

                cSpeech();
            }
        });

        cCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cictIdx = 5;
                cButtons.setVisibility(View.INVISIBLE);
                cText.setText(cCourseSplit[0]);

                cSpeech();
            }
        });

        cBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cictIdx = 2;
//                cInfoIdx = 0; cVisionIdx = 0; cCourseIdx = 0;
//
//                cText.setText(cSplit[cictIdx]);
//                cBackButton.setVisibility(View.INVISIBLE);
//                cButtons.setVisibility(View.VISIBLE);
                UnityPlayerActivity.this.finish();
            }
        });

        // -------------------- Button Click Events -------------------- //
        // -------------------- for CICT Building -------------------- //
        eRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eIdx == 0 || eIdx == 1) {
                    if (eIdx == 1) eButtons.setVisibility(View.VISIBLE);
                    eIdx++;
                    eText.setText(eSplit[eIdx]);

                    eSpeech();
                }
                // when eIdx == 2, there would be buttons below.

                // info mode
                else if (eIdx == 3) {
                    if (eInfoIdx >= 0 && eInfoIdx < 2) {
                        if (eInfoIdx == 1) eBackButton.setVisibility(View.VISIBLE);

                        eInfoIdx++;
                        eText.setText(eInfoSplit[eInfoIdx]);
                        eSpeech();
                    }
                    else {

                    }
                }
                // vision mode
                else if (eIdx == 4) {
                    if (eVisionIdx >= 0 && eVisionIdx < 3) {
                        if (eVisionIdx == 2) eBackButton.setVisibility(View.VISIBLE);

                        eVisionIdx++;
                        eText.setText(eVisionSplit[eVisionIdx]);
                        eSpeech();
                    }
                    else {

                    }
                }
                // course mode
                else if (eIdx == 5) {
                    if (eCourseIdx >= 0 && eCourseIdx <= 4) {
                        if (eCourseIdx == 4) eBackButton.setVisibility(View.VISIBLE);

                        eCourseIdx++;
                        eText.setText(eCourseSplit[eCourseIdx]);
                        eSpeech();
                    }
                    else {

                    }
                }
                else {

                }
            }
        });

        eLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (eIdx == 1 || eIdx == 2) {
                    if (eIdx == 2) eButtons.setVisibility(View.INVISIBLE);
                    eIdx--;
                    eText.setText(eSplit[eIdx]);

                    eSpeech();
                }

                // info mode
                else if (eIdx == 3) {
                    if (eInfoIdx == 0) {
                        eIdx = 2;
                        eText.setText(eSplit[eIdx]);

                        eButtons.setVisibility(View.VISIBLE);
                        eSpeech();
                    }
                    else if (eInfoIdx == 1 || eInfoIdx == 2) {
                        if (eInfoIdx == 2) eBackButton.setVisibility(View.INVISIBLE);

                        eInfoIdx--;
                        eText.setText(eInfoSplit[eInfoIdx]);
                        eSpeech();
                    }
                    else {

                    }
                }

                // vision mode
                else if (eIdx == 4) {
                    if (eVisionIdx == 0) {
                        eIdx = 2;
                        eText.setText(eSplit[eIdx]);
                        eButtons.setVisibility(View.VISIBLE);
                        eSpeech();
                    }
                    else if (eVisionIdx > 0 && eVisionIdx <= 3) {
                        if (eVisionIdx == 3) eBackButton.setVisibility(View.INVISIBLE);

                        eVisionIdx--;
                        eText.setText(eVisionSplit[eVisionIdx]);

                        eSpeech();
                    }
                    else {

                    }
                }

                // course mode
                else if (eIdx == 5) {
                    if (eCourseIdx == 0) {
                        eIdx = 2;
                        eText.setText(eSplit[eIdx]);
                        eButtons.setVisibility(View.VISIBLE);

                        eSpeech();
                    }
                    else if (eCourseIdx > 0 && eCourseIdx <= 5) {
                        if (eCourseIdx == 5) eBackButton.setVisibility(View.INVISIBLE);
                        eCourseIdx--;
                        eText.setText(eCourseSplit[eCourseIdx]);

                        eSpeech();
                    }
                    else {

                    }
                }
                else {

                }
            }
        });

        eInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eIdx = 3;
                eInfoIdx = 0;
                eText.setText(eInfoSplit[eInfoIdx]);
                eButtons.setVisibility(View.INVISIBLE);

                eSpeech();
            }
        });

        eVisionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eIdx = 4;
                eVisionIdx = 0;
                eText.setText(eVisionSplit[eVisionIdx]);
                eButtons.setVisibility(View.INVISIBLE);

                eSpeech();
            }
        });

        eCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eIdx = 5;
                eCourseIdx = 0;
                eText.setText(eCourseSplit[eCourseIdx]);
                eButtons.setVisibility(View.INVISIBLE);

                eSpeech();
            }
        });

        eBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eIdx = 2;
                eInfoIdx = 0;
                eVisionIdx = 0;
                eCourseIdx = 0;

                eText.setText(eSplit[eIdx]);

                eBackButton.setVisibility(View.INVISIBLE);
                eButtons.setVisibility(View.VISIBLE);
            }
        });

        lRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lIdx == 0 || lIdx == 1) {
                    if (lIdx == 1) lButtons.setVisibility(View.VISIBLE);

                    lIdx++;
                    lText.setText(lSplit[lIdx]);

                    lSpeech();
                }

                // Info Mode
                else if (lIdx == 3) {
                    if (lInfoIdx >= 0 && lInfoIdx < 3) {
                        if (lInfoIdx == 2) lBackButton.setVisibility(View.VISIBLE);

                        lInfoIdx++;
                        lText.setText(lInfoSplit[lInfoIdx]);

                        lSpeech();
                    }
                    else {

                    }
                }

                // Mission mode
                else if (lIdx == 4) {
                    if (lMissionIdx >= 0 && lMissionIdx < 8) {
                        if (lMissionIdx == 7) lBackButton.setVisibility(View.VISIBLE);

                        lMissionIdx++;
                        lText.setText(lMissionSplit[lMissionIdx]);

                        lSpeech();
                    }
                    else {

                    }
                }

                // course mode
                else if (lIdx == 5) {
                    if (lCourseIdx >= 0 && lCourseIdx < 5) {
                        if (lCourseIdx == 4) lBackButton.setVisibility(View.VISIBLE);

                        lCourseIdx++;
                        lText.setText(lCourseSplit[lCourseIdx]);

                        lSpeech();
                    }
                    else {

                    }
                }
                else {

                }
            }
        });

        lLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lIdx == 0) {

                }
                else if (lIdx > 0 && lIdx <= 2) {
                    if (lIdx == 2) lButtons.setVisibility(View.INVISIBLE);

                    lIdx--;
                    lText.setText(lSplit[lIdx]);
                }

                // info mode
                else if (lIdx == 3) {
                    if ( lInfoIdx > 0 && lInfoIdx <= 3) {
                        if (lInfoIdx == 3) lBackButton.setVisibility(View.INVISIBLE);

                        lInfoIdx--;
                        lText.setText(lInfoSplit[lInfoIdx]);
                    }
                    else if (lInfoIdx == 0) {
                        lIdx = 2;
                        lText.setText(lSplit[lIdx]);
                        lButtons.setVisibility(View.VISIBLE);
                    }
                    else {

                    }
                }

                // mission mode
                else if (lIdx == 4) {
                    if (lMissionIdx > 0 && lMissionIdx <= 8 ) {
                        if (lMissionIdx == 8) lBackButton.setVisibility(View.INVISIBLE);

                        lMissionIdx--;
                        lText.setText(lMissionSplit[lMissionIdx]);
                    }
                    else if (lMissionIdx == 0) {
                        lIdx = 2;
                        lText.setText(lSplit[lIdx]);
                        lButtons.setVisibility(View.VISIBLE);
                    }
                    else {

                    }
                }

                // course mode
                else if (lIdx == 5) {
                    if (lCourseIdx > 0 && lCourseIdx <= 5) {
                        if (lCourseIdx == 5) lBackButton.setVisibility(View.INVISIBLE);

                        lCourseIdx--;
                        lText.setText(lCourseSplit[lCourseIdx]);
                    }
                    else if (lCourseIdx == 0) {
                        lIdx = 2;
                        lText.setText(lSplit[lIdx]);
                        lButtons.setVisibility(View.VISIBLE);
                    }
                    else {

                    }
                }
                else {

                }
            }
        });

        // ------------- specific button onClick method ------------- //
        lInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lIdx = 3;
                lInfoIdx = 0;

                lButtons.setVisibility(View.INVISIBLE);
                lText.setText(lInfoSplit[0]);

                lSpeech();
            }
        });

        lMissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lIdx = 4;
                lMissionIdx = 0;
                lButtons.setVisibility(View.INVISIBLE);
                lText.setText(lMissionSplit[0]);

                lSpeech();
            }
        });

        lCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lIdx = 5;
                lCourseIdx = 0;
                lButtons.setVisibility(View.INVISIBLE);
                lText.setText(lCourseSplit[0]);

                lSpeech();
            }
        });

        lBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lIdx = 2;
                lInfoIdx = 0; lMissionIdx = 0; lCourseIdx = 0;

                lText.setText(lSplit[lIdx]);
                lBackButton.setVisibility(View.INVISIBLE);
                lButtons.setVisibility(View.VISIBLE);
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cText.setVisibility(View.INVISIBLE);
                eText.setVisibility(View.INVISIBLE);
                lText.setVisibility(View.INVISIBLE);

                cButtons.setVisibility(View.INVISIBLE);
                eButtons.setVisibility(View.INVISIBLE);
                lButtons.setVisibility(View.INVISIBLE);

                cictIdx = 0;
                cInfoIdx = 0;
                cVisionIdx = 0;
                cCourseIdx = 0;

                eIdx = 0;
                eInfoIdx = 0;
                eVisionIdx = 0;
                eCourseIdx = 0;

                lIdx = 0;
                lInfoIdx = 0;
                lMissionIdx = 0;
                lCourseIdx = 0;
            }
        });

        mUnityPlayer.requestFocus();
    }


    private void cSpeech() {
        tts.speak(cText.getText().toString(), TextToSpeech.QUEUE_FLUSH,null, null);
    }

    private void eSpeech() {
        tts.speak(eText.getText().toString(), TextToSpeech.QUEUE_FLUSH,null, null);
    }

    private void lSpeech() {
        tts.speak(lText.getText().toString(), TextToSpeech.QUEUE_FLUSH,null, null);
    }

    @Override protected void onNewIntent(Intent intent)
    {
        // To support deep linking, we need to make sure that the client can get access to
        // the last sent intent. The clients access this through a JNI api that allows them
        // to get the intent set on launch. To update that after launch we have to manually
        // replace the intent with the one caught here.
        setIntent(intent);
    }

    // Quit Unity
    @Override protected void onDestroy ()
    {
        mUnityPlayer.destroy();
        super.onDestroy();
    }

    // Pause Unity
    @Override protected void onPause()
    {
        super.onPause();
        mUnityPlayer.pause();
    }

    // Resume Unity
    @Override protected void onResume()
    {
        super.onResume();
        mUnityPlayer.resume();
    }

    @Override protected void onStart()
    {
        super.onStart();
        mUnityPlayer.start();
    }

    @Override protected void onStop()
    {
        super.onStop();
        mUnityPlayer.stop();
    }

    // Low Memory Unity
    @Override public void onLowMemory()
    {
        super.onLowMemory();
        mUnityPlayer.lowMemory();
    }

    // Trim Memory Unity
    @Override public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_RUNNING_CRITICAL)
        {
            mUnityPlayer.lowMemory();
        }
    }

    // This ensures the layout will be correct.
    @Override public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override public boolean onKeyUp(int keyCode, KeyEvent event)     { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onKeyDown(int keyCode, KeyEvent event)   { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
    /*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }
}
