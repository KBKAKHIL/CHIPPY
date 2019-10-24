package com.example.parrot.pong1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

class Powerup {

    private Bitmap image;
    private Rect hitbox;

    private int xPosition;
    private int yPosition;

    private ArrayList<Rect> powerUps = new ArrayList<Rect>();


    public Powerup(Context context, int x, int y) {

        this.xPosition = x;
        this.yPosition = y;


        this.image = BitmapFactory.decodeResource(context.getResources(), R.drawable.p1);
        this.image = BitmapFactory.decodeResource(context.getResources(), R.drawable.p1);



        this.hitbox = new Rect(
                this.xPosition,
                this.yPosition,
                this.xPosition + this.image.getWidth(),
                this.yPosition + this.image.getHeight()
        );

    }
    // GETTER AND SETTER METHODS
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Rect getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rect hitbox) {
        this.hitbox = hitbox;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }
    public int getrand(int min, int max){

        return (int)(Math.random() * max + min);

        }

    public ArrayList<Rect> getPowerUps() {
        return powerUps;
    }

    public void updateHitbox() {
        this.hitbox.left = this.xPosition;
        this.hitbox.top = this.yPosition;
        this.hitbox.right = this.xPosition + this.image.getWidth();
        this.hitbox.bottom = this.yPosition + this.image.getHeight();

    }

    public void spawnPowerUp() {
        //randomly create a powerup on the screen







    }

}
