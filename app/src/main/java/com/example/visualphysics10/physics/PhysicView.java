package com.example.visualphysics10.physics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.visualphysics10.database.PhysicsData;
import com.example.visualphysics10.engine.PhysicsSprite;
import com.example.visualphysics10.engine.Sprite;
import com.example.visualphysics10.engine.Vector2;
import com.example.visualphysics10.lessonsFragment.L1Fragment;
import com.example.visualphysics10.lessonsFragment.L2Fragment;
import com.example.visualphysics10.lessonsFragment.L3Fragment;
import com.example.visualphysics10.lessonsFragment.L4Fragment;
import com.example.visualphysics10.lessonsFragment.L5Fragment;
import com.example.visualphysics10.objects.PhysicsModel;

import java.util.LinkedList;

public class PhysicView extends SurfaceView implements SurfaceHolder.Callback {
    private final LinkedList<PhysicsModel> sprites = new LinkedList<>();
    private Thread drawThread;

    //TODO: ------------------------------------------------------------------
    // PhysicsData.getElasticImpulse() - метод который отдает true если упругое соударение
    // и false - если неупругое, можешь его использовать где захочешь
    // -----------------------------------------------------------------------

    public PhysicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        initSprites();
    }

    public PhysicView(Context context) {
        this(context, null);
    }


    public void addModelGV(double x0, double y0, double vectorX, double vectorY) {
        synchronized (sprites) {
            sprites.add(new PhysicsModel(getContext(), x0, y0, vectorX, vectorY));
        }
    }


    private void initSprites() {
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        drawThread = new MoveThread();
        drawThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

        Canvas canvas = new Canvas();
        for (Sprite sprite : sprites) sprite.destroy(canvas);
        PhysicsModel.onEarth = false;
        PhysicsModel.isTouchedI = false;
        PhysicsModel.beginning = false;
        L1Fragment.isMoving = false;
        L2Fragment.isMoving = false;
        L3Fragment.isMoving = false;
        L4Fragment.isMoving = false;
        L5Fragment.isMoving = false;
        PhysicsModel.L1 = false;
        PhysicsModel.L2 = false;
        PhysicsModel.L3 = false;
        PhysicsModel.L4 = false;
        PhysicsModel.L5 = false;
        drawThread.interrupt();

    }

    public void updateMoving(double vectorX, double vectorY, int index) {
        synchronized (sprites) {
            sprites.get(index).updateVector(vectorX, vectorY);
        }
    }

    public void updateAA(double aX, double aY, int index) {
        synchronized (sprites) {
            sprites.get(index).updateA(aX, aY);
        }
    }

    public void updateAAC(double radius, double angleV, int index) {
        synchronized (sprites) {
            sprites.get(index).updateAC(radius, angleV);
        }
    }

    public void updateII(int index1,int index2) {
        synchronized (sprites) {
            sprites.get(index1).updateI(Mass(),Mass(),sprites.get(index1).getVectorX(),sprites.get(index2).getVectorX());
            sprites.get(index2).updateI(Mass(),Mass(),sprites.get(index2).getVectorX(),sprites.get(index1).getVectorX());
        }
    }



    public static double Mass() {
        double m = 5;
        return m;
    }
    public void updateGG(double vel, double ang, int index){
        synchronized (sprites){
            sprites.get(index).updateGravity(vel,ang);
        }
    }

    class MoveThread extends Thread {

        @Override
        public void run() {
            Canvas canvas;

            while (!isInterrupted()) {
                canvas = getHolder().lockCanvas();
                if (canvas == null) continue;
                onDrawAsync(canvas);
                PhysicsModel.x0 = canvas.getWidth() / 2;
                PhysicsModel.y0 = canvas.getHeight() / 2;
                PhysicsData.setX0(canvas.getWidth()/2);
                PhysicsData.setY0(canvas.getHeight()/2);
                getHolder().unlockCanvasAndPost(canvas);
            }
        }


        private void onDrawAsync(Canvas canvas) {

            canvas.drawColor(Color.WHITE);
            synchronized (sprites) {
                if (L1Fragment.isMoving) {
                    updateAA(10, 10, 0);
                }
                if (L2Fragment.isMoving) {
                    PhysicsModel.firstDraw = false;
                    updateAAC(PhysicsData.getRadius(), 5, 0);
                    Paint paint = new Paint();
                    paint.setColor(Color.BLACK);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(10);
                    canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, (float) PhysicsData.getRadius(), paint);
                }
                if (L3Fragment.isMoving) {
                    updateAA(0, L3Fragment.l3acc, 0);
                }
                if (L4Fragment.isMoving) {
                    if(PhysicsModel.beginning){
                        updateGG(25,30,0);
                        PhysicsModel.beginning=false;
                    }

                    updateAA(0,1,0);
                }
                if (L5Fragment.isMoving) {
                    updateAA(0, 0, 0);
                    updateAA(0, 0, 1);
                    updateII(0,1);

                    //TODO: --------------------------
                    // PhysicsData.getElasticImpulse() - метод который отдает true если упругое соударение
                    // и false - если неупругое, можешь его использовать где захочешь
                    // -----------------


                }
                for (Sprite sprite : sprites) {
                    sprite.update(canvas);
                    if (sprite instanceof PhysicsSprite) {
                        Rect rect = ((PhysicsSprite) sprite).getSize();
                        Vector2 velocity = ((PhysicsSprite) sprite).getVelocity();
                        for (Sprite collision : sprites) {
                            if (collision instanceof PhysicsSprite
                                    && sprite != collision) {
                                ((PhysicsSprite) collision).checkCollation(rect, velocity);
                            }
                        }
                    }
                }
            }

        }

    }


}