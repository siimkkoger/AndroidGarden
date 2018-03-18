package com.siimk.garden;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;


public class BedDrawableView extends View{
    private static final String TAG = "CustomDrawableView";
    Paint red_fill, blue_fill, green_fill, brown_fill;
    Paint red_stroke, blue_stroke, green_stroke, brown_stroke;
    float downX, downY, moveX, moveY, upX, upY = 0;
    int Gx, Gy, GX, GY = 0;

    Canvas customCanvas;
    private Garden chosenGarden;
    private Rect rectangle001;

    int width;
    int height;
    int maxGardenWidth;
    int maxGardenHeight;

    float tempDownX, tempDownY;
    boolean isDrawing = false;
    boolean isSideCollision = false;
    boolean isVerticalCollision = false;

    boolean fingerInside = false;
    boolean collided = false;

    public BedDrawableView(Context context, int gardenW, int gardenH) {
        super(context);
        chosenGarden = new Garden("", gardenW, gardenH);
        rectangle001 = new Rect();
    }

    public BedDrawableView(Context context, Garden garden) {
        super(context);
        chosenGarden = garden;
        rectangle001 = new Rect();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        red_fill = new Paint(); red_fill.setColor(Color.RED); red_fill.setStyle(Paint.Style.FILL);
        blue_fill = new Paint(); blue_fill.setColor(Color.BLUE); blue_fill.setStyle(Paint.Style.FILL);
        green_fill = new Paint(); green_fill.setColor(Color.GREEN); green_fill.setStyle(Paint.Style.FILL);
        brown_fill = new Paint(); brown_fill.setColor(Color.LTGRAY); green_fill.setStyle(Paint.Style.FILL);
        red_stroke = new Paint(); red_stroke.setColor(Color.RED); red_stroke.setStyle(Paint.Style.STROKE); red_stroke.setStrokeWidth(5);
        blue_stroke = new Paint(); blue_stroke.setColor(Color.BLUE); blue_stroke.setStyle(Paint.Style.STROKE); blue_stroke.setStrokeWidth(5);
        green_stroke = new Paint(); green_stroke.setColor(Color.GREEN); green_stroke.setStyle(Paint.Style.STROKE); green_stroke.setStrokeWidth(5);
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        isDrawing = true;
                        downX = event.getX();
                        downY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        isDrawing = false;

                        if(fingerInside){
                            upX = moveX;
                            upY = moveY;
                        }else {
                            upX = event.getX();
                            upY = event.getY();
                        }

                        // Checks the collision with garden edges
                        if(tempDownX < Gx){
                            tempDownX = Gx;
                        }
                        if(tempDownY < Gy){
                            tempDownY = Gy;
                        }
                        if(upX > GX){
                            upX = GX;
                        }
                        if(upY > GY){
                            upY = GY;
                        }

                        Bed bed00x = new Bed((int)tempDownX, (int)upX, (int)tempDownY, (int)upY);
                        bed00x.setDrawn(true);
                        chosenGarden.addBed(bed00x);
                        v.invalidate();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveX = event.getX();
                        moveY = event.getY();
                        tempDownX = downX;
                        tempDownY = downY;

                        // Enables to draw rectangles in every direction
                        if(downX > moveX){
                            float moveXcopy = moveX;
                            float downXcopy = downX;
                            moveX = downXcopy;
                            tempDownX = moveXcopy;
                        }else {
                            tempDownX = downX;
                        }
                        if(downY > moveY){
                            float moveYcopy = moveY;
                            float downYcopy = downY;
                            moveY = downYcopy;
                            tempDownY = moveYcopy;
                        }else {
                            tempDownY = downY;
                        }

                        // Checks the collision with garden edges
                        if(tempDownX < Gx){
                            tempDownX = Gx;
                        }
                        if(tempDownY < Gy){
                            tempDownY = Gy;
                        }
                        if(moveX > GX){
                            moveX = GX;
                        }
                        if(moveY > GY){
                            moveY = GY;
                        }
                        for(Bed bed : chosenGarden.getBeds()){
                            if(bed.isDrawn() && GlobalFunctions.isAABB(tempDownX, moveX, tempDownY, moveY, bed.getMinX(), bed.getMaxX(), bed.getMinY(), bed.getMaxY())){

                                if(!isVerticalCollision && !isSideCollision){
                                    if(moveX > bed.getMinX() && moveY <= bed.getMaxY() && moveY >= bed.getMinY()){
                                        isVerticalCollision = true;
                                        isSideCollision = false;
                                    } if(moveY > bed.getMinY() && moveX <= bed.getMaxX() && moveX >= bed.getMinX()){
                                        isSideCollision = true;
                                        isVerticalCollision = false;
                                    }
                                }

                                if(isSideCollision){
                                    moveX = bed.getMinX();
                                    upX = moveX;
                                    fingerInside = true;
                                }
                                if(isVerticalCollision){
                                    moveY = bed.getMinY();
                                    upY = moveY;
                                    fingerInside = true;
                                }
                            }else{
                                isVerticalCollision = false;
                                isSideCollision = false;
                            }
                        }
                        chosenGarden.addBed(new Bed((int)tempDownX, (int)moveX, (int)tempDownY, (int)moveY));
                        v.invalidate();
                        break;
                }
                return true;
            }
        });
    }

    protected void onDraw(Canvas canvas) {
        customCanvas = canvas;
        super.onDraw(customCanvas);
        width = this.getWidth();
        height = this.getHeight();
        chosenGarden.setViewWidth(width);
        chosenGarden.setViewHeight(height);
        maxGardenWidth = width - 30;
        maxGardenHeight = height - 50;

        // TODO: scaling math to methods.
        chosenGarden.setScaledHeight(maxGardenHeight);
        chosenGarden.setScaledWidth(chosenGarden.getScaledHeight() * (chosenGarden.getWidth()/chosenGarden.getHeight()));

        if(chosenGarden.getScaledWidth() > maxGardenWidth){
            chosenGarden.setScaledWidth(maxGardenWidth);
            chosenGarden.setScaledHeight(chosenGarden.getScaledWidth() * (chosenGarden.getHeight()/chosenGarden.getWidth()));
        }

        // TODO: Garden coordinates outside of draw - only needed to calculate once
        Gx = (int)(this.width - chosenGarden.getScaledWidth())/2;
        Gy = (int)(this.height - chosenGarden.getScaledHeight())/2;
        GX = (int)(Gx + chosenGarden.getScaledWidth());
        GY = (int)(Gy + chosenGarden.getScaledHeight());

        if(isDrawing && chosenGarden.size() >= 2){
            Bed removableBed = chosenGarden.getBed(chosenGarden.size() - 2);
            if(!removableBed.isDrawn()){
                chosenGarden.remove(chosenGarden.size() - 2);
            }
        }

        for(Bed bed : chosenGarden.getBeds()){
            rectangle001.set(bed.getMinX(), bed.getMinY(), bed.getMaxX(), bed.getMaxY());
            customCanvas.drawRect(rectangle001, brown_fill);
            customCanvas.drawRect(rectangle001, green_stroke);
        }
        rectangle001.set(Gx, Gy, GX, GY);
        customCanvas.drawRect(rectangle001, green_stroke);
    }

    public Garden getDrawnGarden(){
        return chosenGarden;
    }

}































































