package com.example.visualphysics10.objects;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;

import com.example.visualphysics10.database.PhysicsData;
import com.example.visualphysics10.engine.PhysicsSprite;
import com.example.visualphysics10.engine.Vector2;


public class PhysicsModel extends PhysicsSprite {
    double x;
    public static boolean beginning = false;
    double y;
    public static boolean firstDraw = true;
    public static double x0;
    public static double y0;
    int l = 150;
    int h = 150;
    double[] vectorXs = new double[2];
    double vectorX;
    double vectorY;
    double vectorI;
    double p;
    double m;
    double vectorXR;
    double vectorYR;
    public static boolean isTouchedI = false;
    public static boolean onEarth = false;
    public static boolean L1 = false;
    public static boolean L2 = false;
    public static boolean L3 = false;
    public static boolean L4 = false;
    public static boolean L5 = false;
    int angle = 0;
    int n = 1;
    final static double g = 9.8;
    double F;
    private final Paint paint = new Paint();
    private final Paint paint2 = new Paint();

    public PhysicsModel(Context context, double x, double y, double vectorX, double vectorY) {
        super(context);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint2.setColor(Color.BLACK);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(10);
        this.x = x;
        this.y = y;
        this.x0 = x;
        this.y0 = y;
        this.vectorX = vectorX;
        this.vectorY = vectorY;

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public Rect getSize() {
        return new Rect((int) x, (int) y, (int) x + l, (int) y + h);
    }


    @Override
    public boolean checkCollation(Rect rect, Vector2 velocity) {
        boolean result = rect.intersect((int) x, (int) y, (int) x+l , (int) y +h);
        if (result) {
            isTouchedI = true;
            vectorX = -vectorX;
            vectorY = -vectorY;
        }
        return result;
    }

    @Override
    public double addForce(Vector2 velocity) {
        return F;
    }

    @Override
    public Vector2 getVelocity() {
        return new Vector2(vectorX, vectorY);
    }

    @Override
    public void update(Canvas canvas) {
        Rect rect = new Rect();
        updateVector(vectorX, vectorY);
        if (L1 || L3 || L4 || L5) {
            x = x + vectorX;
            y = y + vectorY;
            rect = new Rect((int) x, (int) y, (int) x + l, (int) y + h);
            checkBoard(canvas.getWidth(), canvas.getHeight());
        } else if (L2) {
            canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, (float) PhysicsData.getRadius(), paint2);
            if (firstDraw) {
                rect = new Rect((int) x0, (int) ((int) y0 - PhysicsData.getRadius()), (int) x0 + l, (int) ((int) y0 + h - PhysicsData.getRadius()));
            } else {
                x = x0 + vectorX;
                y = y0 + vectorY;
                rect = new Rect((int) x, (int) y, (int) x + l, (int) y + h);
            }
        } else {
            rect = new Rect(0, 0, 0, 0);
        }


        canvas.drawRect(rect, paint);
    }

    public void updateVector(double vX, double vY) {
        vectorX = vX;
        vectorY = vY;
    }

    public void updateAC(double radius, double anglev) {
        vectorXR = radius * Math.cos((Math.PI / 180) * angle);
        vectorYR = radius * Math.sin((Math.PI / 180) * angle);
        if (angle >= 360 * (n - 1) && angle < 90 + 360 * (n - 1)) {
            vectorX = Math.sqrt((radius * radius) - (vectorYR * vectorYR));
            vectorY = Math.sqrt((radius * radius) - (vectorXR * vectorXR));
        } else if (angle >= 90 + 360 * (n - 1) && angle < 180 + 360 * (n - 1)) {
            vectorX = -Math.sqrt((radius * radius) - (vectorYR * vectorYR));
            vectorY = Math.sqrt((radius * radius) - (vectorXR * vectorXR));
        } else if (angle >= 180 + 360 * (n - 1) && angle < 270 + 360 * (n - 1)) {
            vectorX = -Math.sqrt((radius * radius) - (vectorYR * vectorYR));
            vectorY = -Math.sqrt((radius * radius) - (vectorXR * vectorXR));
        } else if (angle >= 270 + 360 * (n - 1) && angle < 360 + 360 * (n - 1)) {
            vectorX = Math.sqrt((radius * radius) - (vectorYR * vectorYR));
            vectorY = -Math.sqrt((radius * radius) - (vectorXR * vectorXR));
        }
        if (angle >= 360 * n) {
            n++;
        }
        angle += anglev;
    }


    private void checkBoard(int width, int height) {
        if (x < 0 && vectorX - l < 0 || (x > width - l && vectorX > 0)) {
            vectorX = 0;
        }
        if (y < 0 && vectorY - h < 0 || (y > height - h && vectorY > 0)) {
            onEarth = true;
            vectorY = 0;
        }
    }
    public void updateGravity(double vel,double ang){
        vectorX=vel*Math.sin((Math.PI / 180) * ang);
        vectorY=-vel*Math.cos((Math.PI / 180) * ang);
    }

    public double getVectorX() {
        return vectorX;
    }

    public void updateI(double m1, double m2, double vectorX1, double vectorX2) {
        if (isTouchedI) {
            isTouchedI=false;
        }
    }

    public void updateA(double ax, double ay) {
        if (onEarth) {
            ay = 0;
            vectorY=0;
        }
        vectorX += ax;
        vectorY += ay;
    }

    @Override
    public void destroy(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

}
