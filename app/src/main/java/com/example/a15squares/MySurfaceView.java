package com.example.a15squares;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.widget.TextView;

public class MySurfaceView extends SurfaceView {

    private TextView userDisplay;
    private MyViewController controller;
    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

}
