package com.siimk.garden;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class BedView extends View{
    private static final String TAG = "CustomDrawableView";
    Paint red_fill, blue_fill, green_fill, brown_fill, gray_fill;
    Paint red_stroke, blue_stroke, green_stroke;
    Paint mpaint, paint2;
    int x0, y0, x100, y100 = 0;

    Canvas customCanvas;
    private Garden chosenGarden;
    private Rect rectangle001;

    TextView tw;

    int width;
    int height;
    int maxGardenWidth;
    int maxGardenHeight;

    Bed chosenBed1;
    Bed chosenBed2;
    Bed chosenBed;

    int position;

    public BedView(Context context, Garden garden00x, boolean editable, final Bed chosenBed) {
        super(context);
        chosenGarden = garden00x;
        this.chosenBed = chosenBed;
        Log.d(TAG, "BedView: " + this.chosenBed.toString());
        rectangle001 = new Rect();

        mpaint= new Paint();
        mpaint.setColor(Color.RED);
        mpaint.setStyle(Paint.Style.FILL);
        paint2= new Paint();
        paint2.setColor(Color.RED);
        paint2.setTextSize(15);
        paint2.setTextAlign(Paint.Align.CENTER);
        //paint2.setStrokeWidth(5);
        paint2.setFakeBoldText(true);

        red_fill = new Paint(); red_fill.setColor(Color.RED); red_fill.setStyle(Paint.Style.FILL);
        blue_fill = new Paint(); blue_fill.setColor(Color.BLUE); blue_fill.setStyle(Paint.Style.FILL);
        green_fill = new Paint(); green_fill.setColor(Color.GREEN); green_fill.setStyle(Paint.Style.FILL);
        brown_fill = new Paint(); brown_fill.setColor(Color.LTGRAY); brown_fill.setStyle(Paint.Style.FILL);
        gray_fill = new Paint(); gray_fill.setColor(Color.GRAY); gray_fill.setStyle(Paint.Style.FILL);
        red_stroke = new Paint(); red_stroke.setColor(Color.RED); red_stroke.setStyle(Paint.Style.STROKE); red_stroke.setStrokeWidth(5);
        blue_stroke = new Paint(); blue_stroke.setColor(Color.BLUE); blue_stroke.setStyle(Paint.Style.STROKE); blue_stroke.setStrokeWidth(5);
        green_stroke = new Paint(); green_stroke.setColor(Color.GREEN); green_stroke.setStyle(Paint.Style.STROKE); green_stroke.setStrokeWidth(5);

        if(editable){
            this.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch(event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            for(Bed bed : chosenGarden.getBeds()){
                            }
                            if(chosenGarden.fingerCollidingWithBed(event.getX(), event.getY())){
                                chosenBed1 = chosenGarden.chosenBed(event.getX(), event.getY());
                            }else {
                                chosenBed1 = null;
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            if(chosenGarden.fingerCollidingWithBed(event.getX(), event.getY())){
                                chosenBed2 = chosenGarden.chosenBed(event.getX(), event.getY());
                            }else {
                                chosenBed2 = null;
                            }
                            break;
                    }
                    if(chosenBed2 != null && chosenBed1 != null && chosenBed1.equals(chosenBed2)){
                        ViewGardenActivity vga = (ViewGardenActivity)getContext();
                        Intent bedViewIntent = new Intent(vga, BedTabActivity.class);
                        bedViewIntent.putExtra("view_bed", chosenBed1);
                        bedViewIntent.putExtra("view_garden", chosenGarden);
                        Log.d(TAG, "onTouch: " + chosenBed1.toString());
                        //Log.d(TAG, "onTouch: " + chosenGarden.getViewWidth() + " " + chosenGarden.getViewHeight());
                        bedViewIntent.putExtra("view_bed_position", vga.garden.getPosition(chosenBed1));
                        vga.startActivityForResult(bedViewIntent, 404);
                    }
                    invalidate();
                    return true;
                }
            });
        }
    }

    protected void onDraw(Canvas canvas) {
        customCanvas = canvas;
        super.onDraw(customCanvas);
        width = (int)(chosenGarden.getViewWidth());
        height = (int)(chosenGarden.getViewHeight());
        Log.d(TAG, "onDraw: width and height of the garden2: " + width + " " + height);
        maxGardenWidth = width - 30;
        maxGardenHeight = height - 50;

        // TODO: scaling math to methods.
        chosenGarden.setScaledHeight(maxGardenHeight);
        chosenGarden.setScaledWidth(chosenGarden.getScaledHeight() * (chosenGarden.getWidth()/chosenGarden.getHeight()));

        if(chosenGarden.getScaledWidth() > maxGardenWidth){
            chosenGarden.setScaledWidth(maxGardenWidth);
            chosenGarden.setScaledHeight(chosenGarden.getScaledWidth() * (chosenGarden.getHeight()/chosenGarden.getWidth()));
        }

        x0 = (int)(this.width - chosenGarden.getScaledWidth())/2;
        y0 = (int)(this.height - chosenGarden.getScaledHeight())/2;
        x100 = (int)(x0 + chosenGarden.getScaledWidth());
        y100 = (int)(y0 + chosenGarden.getScaledHeight());


        for(Bed bed : chosenGarden.getBeds()){
            rectangle001.set(bed.getMinX(), bed.getMinY(), bed.getMaxX(), bed.getMaxY());
            Log.d(TAG, "onDraw: " + bed.toString());

            if(bed.getMinX() == chosenBed.getMinX() &&
                    bed.getMaxX() == chosenBed.getMaxX() &&
                    bed.getMinY() == chosenBed.getMinY() &&
                    bed.getMaxY() == chosenBed.getMaxY()){
                Log.d(TAG, "onDraw: 111");
                customCanvas.drawRect(rectangle001, gray_fill);
                customCanvas.drawRect(rectangle001, green_stroke);
            }else if(bed.equals(chosenBed2)){
                customCanvas.drawRect(rectangle001, gray_fill);
                customCanvas.drawRect(rectangle001, green_stroke);
            }else{
                customCanvas.drawRect(rectangle001, brown_fill);
                customCanvas.drawRect(rectangle001, green_stroke);
            }


            customCanvas.drawText(bed.getShortDesc(), (bed.getMinX() + bed.getMaxX())/2, (bed.getMinY() + bed.getMaxY())/2, paint2);
        }
        rectangle001.set(x0, y0, x100, y100);
        customCanvas.drawRect(rectangle001, green_stroke);

    }

    public Garden getDrawnGarden(){
        return chosenGarden;
    }


    public Canvas getCustomCanvas(){
        return customCanvas;
    }

}























