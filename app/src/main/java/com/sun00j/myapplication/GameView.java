package com.sun00j.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by lewa on 10/26/15.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback,View.OnTouchListener {
    private final Desk desk;
    private Context mContext;
    boolean threadFlag = true;
    SurfaceHolder holder;
    Canvas canvas;
    public GameView(Context context) {
        super(context);
        mContext = context;
        desk = new Desk(context);
        this.getHolder().addCallback(this);
        this.setOnTouchListener(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        new Thread(new TcpUtils()).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
    /*Thread gameThread = new Thread() {
        @SuppressLint("WrongCall")
        @Override
        public void run() {
            holder = getHolder();
            while (threadFlag) {
                //desk.gameLogic();
                try {
                    canvas = holder.lockCanvas();
                    onDraw(canvas);
                } finally {
                    holder.unlockCanvasAndPost(canvas);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    };*/
}
