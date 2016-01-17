package com.bbcivil.toss.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bbcivil.toss.TossImageView;
import com.bbcivil.toss.animation.TossAnimation;

public class ComplexTossActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private static final String[] mStrings = new String[]{
            "up",
            "rhombus"
    };

    private Spinner mSpinner;
    private TossImageView mTossImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_complex);

        mSpinner = (Spinner) findViewById(R.id.spinner);
        mTossImageView = (TossImageView) findViewById(R.id.tiv);

        mSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, mStrings));
        mSpinner.setSelection(1);

        mSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:

                mTossImageView.cleareOtherAnimation();

                TranslateAnimation translateAnimation0 = new TranslateAnimation(0, 0, 0, -400);
                translateAnimation0.setDuration(3000);
                TranslateAnimation translateAnimation1 = new TranslateAnimation(0, 0, 0, 400);
                translateAnimation1.setDuration(3000);
                translateAnimation1.setStartOffset(3000);

                mTossImageView.setInterpolator(new DecelerateInterpolator())
                        .setDuration(6000)
                        .setCircleCount(40)
                        .setXAxisDirection(TossAnimation.DIRECTION_CLOCKWISE)
                        .setYAxisDirection(TossAnimation.DIRECTION_NONE)
                        .setZAxisDirection(TossAnimation.DIRECTION_NONE);

                mTossImageView.addOtherAnimation(translateAnimation0);
                mTossImageView.addOtherAnimation(translateAnimation1);

                mTossImageView.startToss();
                break;
            case 1:

                mTossImageView.cleareOtherAnimation();

                TranslateAnimation translateAnimation10 = new TranslateAnimation(0, 200, 0, -200);
                translateAnimation10.setDuration(2000);
                TranslateAnimation translateAnimation11 = new TranslateAnimation(0, -200, 0, -200);
                translateAnimation11.setDuration(2000);
                translateAnimation11.setStartOffset(2000);
                TranslateAnimation translateAnimation12 = new TranslateAnimation(0, -200, 0, 200);
                translateAnimation12.setDuration(2000);
                translateAnimation12.setStartOffset(4000);
                TranslateAnimation translateAnimation13 = new TranslateAnimation(0, 200, 0, 200);
                translateAnimation13.setDuration(2000);
                translateAnimation13.setStartOffset(6000);

                mTossImageView.setInterpolator(new LinearInterpolator())
                        .setDuration(8000)
                        .setCircleCount(40)
                        .setXAxisDirection(TossAnimation.DIRECTION_CLOCKWISE)
                        .setYAxisDirection(TossAnimation.DIRECTION_CLOCKWISE)
                        .setZAxisDirection(TossAnimation.DIRECTION_CLOCKWISE);

                mTossImageView.addOtherAnimation(translateAnimation10);
                mTossImageView.addOtherAnimation(translateAnimation11);
                mTossImageView.addOtherAnimation(translateAnimation12);
                mTossImageView.addOtherAnimation(translateAnimation13);

                mTossImageView.startToss();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
