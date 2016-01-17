package com.bbcivil.toss.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.bbcivil.toss.TossImageView;
import com.bbcivil.toss.animation.TossAnimation;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String[] mInterpolatorNames = new String[]{
            "Decelerate",
            "Accelerate",
            "AccelerateDecelerate",
            "Bounce",
            "Overshoot",
            "AnticipateOvershoot"
    };
    private static final String[] mDirectionNames = new String[]{
            "none",
            "clockwise",
            "abtucclockwise"
    };

    private static final Interpolator[] mInterpolators = new Interpolator[]{
            new DecelerateInterpolator(),
            new AccelerateInterpolator(),
            new AccelerateDecelerateInterpolator(),
            new BounceInterpolator(),
            new OvershootInterpolator(),
            new AnticipateOvershootInterpolator()
    };
    private static final int[] mDirections = new int[]{
            TossAnimation.DIRECTION_NONE,
            TossAnimation.DIRECTION_CLOCKWISE,
            TossAnimation.DIRECTION_ABTUCCLOCKWISE
    };

    private Spinner mSpinner;
    private SeekBar mSeekBar;
    private EditText mEditText;
    private Spinner xSpinner,ySpinner,zSpinner;

    private Button start, complex;
    private TossImageView mTossImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        String ss;

        mSpinner = (Spinner) findViewById(R.id.spinner);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mEditText = (EditText) findViewById(R.id.edittext);
        xSpinner = (Spinner) findViewById(R.id.xdirection);
        ySpinner = (Spinner) findViewById(R.id.ydirection);
        zSpinner = (Spinner) findViewById(R.id.zdirection);

        mSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, mInterpolatorNames));
        xSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, mDirectionNames));
        ySpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, mDirectionNames));
        zSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, mDirectionNames));
        mTossImageView = (TossImageView) findViewById(R.id.tiv);

        xSpinner.setSelection(1);

        start = (Button) findViewById(R.id.start);
        complex = (Button) findViewById(R.id.complex);

        start.setOnClickListener(this);
        complex.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                int circleCount = 10;
                try {
                    circleCount = Integer.parseInt(mEditText.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mTossImageView.setInterpolator(mInterpolators[mSpinner.getSelectedItemPosition()])
                        .setDuration(mSeekBar.getProgress())
                        .setCircleCount(circleCount)
                        .setXAxisDirection(mDirections[xSpinner.getSelectedItemPosition()])
                        .setYAxisDirection(mDirections[ySpinner.getSelectedItemPosition()])
                        .setZAxisDirection(mDirections[zSpinner.getSelectedItemPosition()])
                        .startToss();
                break;
            case R.id.complex:
                startActivity(new Intent(this, ComplexTossActivity.class));
                break;
        }
    }
}
