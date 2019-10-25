package com.example.parrot.pong1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine extends SurfaceView implements Runnable {

    // -----------------------------------
    // ## ANDROID DEBUG VARIABLES
    // -----------------------------------

    // Android debug variables
    final static String TAG = "CHIPPY";

    // -----------------------------------
    // ## SCREEN & DRAWING SETUP VARIABLES
    // -----------------------------------

    // screen size
    int screenHeight;
    int screenWidth;

    // game state
    boolean gameIsRunning;

    // threading
    Thread gameThread;


    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;


    // -----------------------------------
    // ## GAME SPECIFIC VARIABLES
    // -----------------------------------

    // ----------------------------
    // ## SPRITES
    // ----------------------------

    Player player;
    Enemy enemy;
    Enemy1 enemy1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16, e17, e18, e19, e20, e21, e22, e23, e24, e25;


    Powerup powerup;

    List<Enemy1> enemy1List = new ArrayList<Enemy1>();

    Bitmap background;
    int bgXposition = 0;
    int backgroundRightside = 0;
    private Enemy bullet;


    // ----------------------------
    // ## GAME STATS - number of lives, score, etc

    int Lives = 5;
    int EnemyLives = 1;

    // ----------------------------


    public GameEngine(Context context, int w, int h) {
        super(context);


        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        this.screenWidth = w;
        this.screenHeight = h;


        this.printScreenInfo();

        // @TODO: Add your sprites to this section
        // This is optional. Use it to:
        //  - setup or configure your sprites
        //  - set the initial position of your sprites
        this.player = new Player(getContext(), 100, 100);
        this.enemy = new Enemy(getContext(),1950,520);

        this.enemy1 = new Enemy1(getContext(),1700,500);
        this.e2 = new Enemy1(getContext(),1750,520);
        this.e11 = new Enemy1(getContext(),1800,400);
        this.e12 = new Enemy1(getContext(),1950,425);
        this.e13 = new Enemy1(getContext(),2100,450);
        this.e14 = new Enemy1(getContext(),2150,400);
        this.e15 = new Enemy1(getContext(),1900,450);
        this.e16 = new Enemy1(getContext(),1900,520);
        this.e17 = new Enemy1(getContext(),1950,475);
        this.e18 = new Enemy1(getContext(),1775,500);
        this.e19 = new Enemy1(getContext(),1850,520);
        this.e20 = new Enemy1(getContext(),2000,400);
        enemy1List.add(enemy1);





        this.background = BitmapFactory.decodeResource(context.getResources(), R.drawable.space);
        this.background = Bitmap.createScaledBitmap(this.background, this.screenWidth, this.screenHeight, false);
        this.bgXposition = 0;


        // @TODO: Any other game setup stuff goes here


    }

    // ------------------------------
    // HELPER FUNCTIONS
    // ------------------------------

    // This funciton prints the screen height & width to the screen.
    private void printScreenInfo() {

        Log.d(TAG, "Screen (w, h) = " + this.screenWidth + "," + this.screenHeight);
    }

    private void spawnPlayer() {
        //@TODO: Start the player at the left side of screen
    }

    private void spawnEnemyShips() {
        Random random = new Random();

        //@TODO: Place the enemies in a random location

    }




    // ------------------------------
    // GAME STATE FUNCTIONS (run, stop, start)
    // ------------------------------
    @Override
    public void run() {
        while (gameIsRunning == true) {
            this.updatePositions();
            this.redrawSprites();
            this.setFPS();
        }
    }


    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void startGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    // ------------------------------
    // GAME ENGINE FUNCTIONS
    // - update, draw, setFPS
    // ------------------------------

    int numLoops = 0;

    boolean enemyMoving = true;
    final int enemySpeed = 40;

    // 1. Tell Android the (x,y) positions of your sprites


    public void updatePositions() {
        // @TODO: Update the position of the sprites

        this.bgXposition = this.bgXposition - 50;

        backgroundRightside = this.bgXposition + this.background.getWidth();

        if (backgroundRightside < 0) {
            this.bgXposition = 0;
        }

        numLoops = numLoops + 1;


        if(numLoops % 10 == 0) {

//            this.player.spawnBullet();
            this.enemy.spawnBullet();
        }

        int PBULLET_SPEED = 50;
        int BULLET_SPEED = 100;

        for (int i = 0; i < this.player.getBullets().size();i++) {
            Rect bullet = this.player.getBullets().get(i);
            bullet.left = bullet.left + PBULLET_SPEED;
            bullet.right = bullet.right + PBULLET_SPEED;
        }

        for (int i = 0; i < this.enemy.getBullets().size();i++) {
            Rect bullet = this.enemy.getBullets().get(i);
            bullet.left = bullet.left - BULLET_SPEED;
            bullet.right = bullet.right - BULLET_SPEED;
        }
//        for (int i = 0; i < this.enemy.getBullets1().size();i++) {
//            Rect bullet1 = this.enemy.getBullets1().get(i);
//            bullet1.top = bullet1.top - BULLET_SPEED;
//            bullet1.bottom = bullet1.bottom - BULLET_SPEED;
//        }

        for (int i = 0; i < this.enemy.getBullets().size();i++) {
            Rect bullet = this
                    .enemy.getBullets().get(i);
            if(this.player.getHitbox().intersect(bullet)) {
                Lives = Lives -1;

            }

            if (Lives == 0) {
                getContext().startActivity(new Intent(getContext(),GameOverActivity.class));
            }
        }






        // COLLISION DETECTION ON THE BULLET AND WALL
        for (int i = 0; i < this.player.getBullets().size();i++) {
            Rect bullet = this.player.getBullets().get(i);

            // For each bullet, check if the bullet touched the wall
            if (bullet.left > this.screenWidth) {
                this.player.getBullets().remove(bullet);
            }

        }

        // COLLISION DETECTION BETWEEN BULLET AND ENEMY
        for (int i = 0; i < this.player.getBullets().size();i++) {
            Rect bullet = this.player.getBullets().get(i);

            if (this.enemy.getHitbox().intersect(bullet)) {
//                this.enemy.setxPosition(3000);
//                this.enemy.setyPosition(2000);
//                this.enemy1.setxPosition(3000);
//                this.enemy1.setyPosition(2000);
//
//                this.e2.setxPosition(3000);
//                this.e2.setyPosition(2000);
//                this.e11.setxPosition(3000);
//                this.e11.setyPosition(2000);
//                this.e13.setxPosition(3000);
//                this.e12.setxPosition(3000);
//                this.e12.setyPosition(2000);
//                this.e13.setyPosition(2000);
//                this.e14.setxPosition(3000);
//                this.e14.setyPosition(2000);
//                this.e16.setxPosition(3000);
//                this.e16.setyPosition(2000);
//                this.e15.setxPosition(3000);
//                this.e15.setyPosition(2000);
//                this.e17.setxPosition(3000);
//                this.e17.setyPosition(2000);
//                this.e18.setxPosition(3000);
//                this.e18.setyPosition(2000);
//                this.e19.setxPosition(3000);
//                this.e19.setyPosition(2000);
//                this.e20.setxPosition(3000);
//                this.e20.setyPosition(2000);


                this.player.getBullets().remove(bullet);
                this.enemy.getBullets().remove(enemy);
                this.enemy.getBullets().remove(e2);
                this.enemy.getBullets().remove(e11);
                this.enemy.getBullets().remove(e12);
                this.enemy.getBullets().remove(e13);
                this.enemy.getBullets().remove(e14);
                this.enemy.getBullets().remove(e15);


                this.enemy.getBullets().remove(enemy);


                this.enemy .updateHitbox();
                this.enemy1.updateHitbox();
                this.e2.updateHitbox();
                this.e11.updateHitbox();
                this.e12.updateHitbox();
                this.e13.updateHitbox();
                this.e14.updateHitbox();
                this.e15.updateHitbox();
                this.e16.updateHitbox();
                this.e17.updateHitbox();
                this.e18.updateHitbox();
                this.e19.updateHitbox();
                this.e20.updateHitbox();

                EnemyLives = EnemyLives - 1;


            }

//            if (EnemyLives == 0) {
//                getContext().startActivity(new Intent(getContext(),GameOverActivity.class));
//                paintbrush.setColor(Color.WHITE);
//                canvas.drawText("WINNER", screenHeight / 2,screenWidth / 2,paintbrush);
//
//            }


        }







            // @TODO: Update position of player based on mouse tap
        if (this.fingerAction == "mousedown") {

            player.setxPosition(player.x);
            player.setyPosition(player.y);
            player.updateHitbox();
            this.player.spawnBullet();


        }

//        boolean enemyIsMovingDown = true;
//
//        if (enemyIsMovingDown ==  true) {
//            this .enemy.setyPosition(this.enemy.getyPosition() + 1);
//            this.enemy1.setyPosition(this.enemy1.getyPosition() +1);
//
//
//        }
//        else {
//            this.enemy.setyPosition(this.enemy.getyPosition() - 1);
//
//        }
//
//        if (this.enemy.getyPosition() >= this.screenHeight + 1 ){
//            enemyIsMovingDown = false;
//        }
//        if(this.enemy.getyPosition() < 120) {
//            enemyIsMovingDown = true;
//        }

        enemy.updateHitbox();
//        this.enemy1.setyPosition(this.enemy1.getyPosition() + 5);
        enemy1.updateHitbox();

//        Log.d(TAG,"Bullet position: " + this.bullet.getxPosition() + ", " + this.bullet.getyPosition());
//        Log.d(TAG,"Enemy position: " + this.enemy.getxPosition() + ", " + this.enemy.getyPosition());
//
//        double a = this.enemy.getxPosition() - this.bullet.getxPosition();
//        double b = this.enemy.getyPosition() - this.bullet.getyPosition();
//
//        double d = Math.sqrt((a * a)+(b * b));
//        Log.d(TAG, "Distance to enemy: " + d);
//        double xn = (a / d);
//                double yn = (b / d);
//
//                        // 3. calculate new (x,y) coordinates
//        int newX = this.bullet.getxPosition() + (int) (xn * 15);
//        int newY = this.bullet.getyPosition() + (int) (yn * 15);
//                this.bullet.setxPosition(newX);
//                this.bullet.setyPosition(newY);
//
//                        Log.d(TAG,"----------");






    }

    // 2. Tell Android to DRAW the sprites at their positions
    public void redrawSprites() {
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------
            // Put all your drawing code in this section

            // configure the drawing tools
//            this.canvas.drawColor(Color.argb(255,0,0,255));
            paintbrush.setColor(Color.WHITE);

            // DRAW THE PLAYER HITBOX
            // ------------------------
            // 1. change the paintbrush settings so we can see the hitbox
            paintbrush.setColor(Color.BLACK);
            paintbrush.setStyle(Paint.Style.STROKE);
            paintbrush.setStrokeWidth(5);


            //@TODO: Draw the sprites (rectangle, circle, etc)
            canvas.drawBitmap(this.background, this.bgXposition, 0, paintbrush);

            canvas.drawBitmap(this.background, this.backgroundRightside, 0, paintbrush);

            // draw player graphic on screen

            canvas.drawBitmap(player.getImage(), player.getxPosition(), player.getyPosition(), paintbrush);
            // draw the player's hitbox
            canvas.drawRect(player.getHitbox(), paintbrush);

            canvas.drawBitmap(enemy.getImage(),enemy.getxPosition(),enemy.getyPosition(),paintbrush);
            canvas.drawRect(enemy.getHitbox(),paintbrush);

            canvas.drawBitmap(enemy1.getImage(),enemy1.getxPosition(),enemy1.getyPosition(),paintbrush);
            canvas.drawRect(enemy1.getHitbox(),paintbrush);

            canvas.drawBitmap(e16.getImage(), e16.getxPosition(), e16.getyPosition(),paintbrush);
            canvas.drawRect(e16.getHitbox(),paintbrush);

            canvas.drawBitmap(e11.getImage(), e11.getxPosition(), e11.getyPosition(),paintbrush);
            canvas.drawRect(e11.getHitbox(),paintbrush);

            canvas.drawBitmap(e12.getImage(), e12.getxPosition(), e12.getyPosition(),paintbrush);
            canvas.drawRect(e12.getHitbox(),paintbrush);
            canvas.drawBitmap(e12.getImage(), e12.getxPosition(), e12.getyPosition(),paintbrush);
            canvas.drawRect(e12.getHitbox(),paintbrush);
            canvas.drawBitmap(e13.getImage(), e13.getxPosition(), e13.getyPosition(),paintbrush);
            canvas.drawRect(e13.getHitbox(),paintbrush);
            canvas.drawBitmap(e13.getImage(), e13.getxPosition(), e13.getyPosition(),paintbrush);
            canvas.drawRect(e13.getHitbox(),paintbrush);
            canvas.drawBitmap(e14.getImage(), e13.getxPosition(), e13.getyPosition(),paintbrush);
            canvas.drawRect(e14.getHitbox(),paintbrush);
            canvas.drawBitmap(e15.getImage(), e13.getxPosition(), e13.getyPosition(),paintbrush);
            canvas.drawRect(e15.getHitbox(),paintbrush);
            canvas.drawBitmap(e16.getImage(), e13.getxPosition(), e13.getyPosition(),paintbrush);
            canvas.drawRect(e15.getHitbox(),paintbrush);
            canvas.drawBitmap(e17.getImage(), e13.getxPosition(), e13.getyPosition(),paintbrush);
            canvas.drawRect(e15.getHitbox(),paintbrush);
            canvas.drawBitmap(e20.getImage(), e13.getxPosition(), e13.getyPosition(),paintbrush);
            canvas.drawRect(e20.getHitbox(),paintbrush);
            canvas.drawBitmap(e2.getImage(), e17.getxPosition(), e17.getyPosition(),paintbrush);
            canvas.drawRect(e2.getHitbox(),paintbrush);
            canvas.drawBitmap(e18.getImage(), e18.getxPosition(), e18.getyPosition(),paintbrush);
            canvas.drawRect(e18.getHitbox(),paintbrush);
            canvas.drawBitmap(e19.getImage(), e19.getxPosition(), e19.getyPosition(),paintbrush);
            canvas.drawRect(e19.getHitbox(),paintbrush);






            for (int i = 0; i < this.player.getBullets().size(); i++) {
                Rect bullet = this.player.getBullets().get(i);
                paintbrush.setStyle(Paint.Style.FILL);
                paintbrush.setColor(Color.RED);
                canvas.drawRect(bullet, paintbrush);

            }

            for (int i = 0; i < this.enemy.getBullets().size(); i++) {
                Rect bullet = this.enemy.getBullets().get(i);
                paintbrush.setStyle(Paint.Style.FILL);
                paintbrush.setColor(Color.YELLOW);
                canvas.drawRect(bullet, paintbrush);

            }

//            for (int i = 0; i < this.enemy.getBullets1().size(); i++) {
//                Rect bullet1 = this.enemy.getBullets1().get(i);
//                paintbrush.setStyle(Paint.Style.FILL);
//                paintbrush.setColor(Color.GREEN);
//                canvas.drawRect(bullet1, paintbrush);
//
//            }



            //@TODO: Draw game statistics (lives, score, etc)
            paintbrush.setTextSize(60);
            canvas.drawText("Lives: " + Lives ,  20, 100, paintbrush);
            canvas.drawText("Enemy: " + EnemyLives ,  100, 20, paintbrush);

            //----------------
            this.holder.unlockCanvasAndPost(canvas);
        }


    }

    // Sets the frame rate of the game
    public void setFPS() {
        try {
            gameThread.sleep(30);

        } catch (Exception e) {

        }
    }

    String fingerAction = "";

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE:


                player.x = (int) event.getX();
                player.y = (int) event.getY();

                Log.d("PUSH", "PERSON CLICKED AT: (" + event.getX() + "," + event.getY() + ")");
                fingerAction = "mousedown";
//                    this.player.spawnBullet();
//                this.enemy.spawnBullet();
                this.player.spawnBullet();
                break;

        }

        return true;
    }
}