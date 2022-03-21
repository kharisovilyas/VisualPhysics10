package com.example.visualphysics10.database;

public class PhysicsData {
    public static double radius;
    public static double speed;
    public static double speed2;
    public static double distance;
    public static double acc;
    public static double mass1;
    public static double mass2;
    public static double x0;
    public static double y0;
    public boolean impact;
    public static boolean elasticImpulse;

    public static boolean getElasticImpulse() {
        return elasticImpulse;
    }

    public static void setElasticImpulse(boolean elasticImpulse) {
        PhysicsData.elasticImpulse = elasticImpulse;
    }

    public static double getSpeed2() {
        return speed2;
    }

    public static void setSpeed2(double speed2) {
        PhysicsData.speed2 = speed2;
    }


    public boolean isImpact() {
        return impact;
    }

    public void setImpact(boolean impact) {
        this.impact = impact;
    }

    public static double getX0() {
        return x0;
    }

    public static void setX0(double x0) {
        PhysicsData.x0 = x0;
    }

    public static double getY0() {
        return y0;
    }

    public static void setY0(double y0) {
        PhysicsData.y0 = y0;
    }

    public static double getRadius() {
        return radius;
    }

    public static void setRadius(double radius) {
        PhysicsData.radius = radius;
    }

    public static double getSpeed() {
        return speed;
    }

    public static void setSpeed(double speed) {
        PhysicsData.speed = speed;
    }

    public static double getDistance() {
        return distance;
    }

    public static void setDistance(double distance) {
        PhysicsData.distance = distance;
    }

    public static double getAcc() {
        return acc;
    }

    public static void setAcc(double acc) {
        PhysicsData.acc = acc;
    }

    public static double getMass1() {
        return mass1;
    }

    public static void setMass1(double mass1) {
        PhysicsData.mass1 = mass1;
    }

    public static double getMass2() {
        return mass2;
    }

    public static void setMass2(double mass2) {
        PhysicsData.mass2 = mass2;
    }

}
