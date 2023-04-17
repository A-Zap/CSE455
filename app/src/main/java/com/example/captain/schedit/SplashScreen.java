package com.example.captain.schedit;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
        .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(5000)
                .withBackgroundColor(Color.parseColor("#ECF0F1"))
                .withLogo(R.drawable.sch)
                .withHeaderText("Welcome to Schedit")
                .withFooterText("2017")
                .withBeforeLogoText("")
                .withAfterLogoText("Project Manager: Eric Villanueva \n Lead Developer: Nestor Saavedra \n Support Developer: Andres Zapata\nDocumentation Support: Patrick k.");
                //text color

        config.getHeaderTextView().setTextColor(Color.BLACK);
        config.getFooterTextView().setTextColor(Color.BLACK);
        config.getAfterLogoTextView().setTextColor(Color.BLACK);
        config.getBeforeLogoTextView().setTextColor(Color.BLACK);
        //set view
        View view = config.create();
        //set view to content
        setContentView(view);
    }
}
