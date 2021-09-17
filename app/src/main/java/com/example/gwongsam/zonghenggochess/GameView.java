package com.example.gwongsam.zonghenggochess;

import android.graphics.Color;
import android.view.View;
import android.graphics.Bitmap;
import java.util.ArrayList;
import android.graphics.Point;
import android.graphics.Paint;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Canvas;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.os.Bundle;
import android.view.MotionEvent;

public class GameView extends View {

    private static final int MAX_LINE = 19;
    private Bitmap mBlack;
    private Bitmap mWhite;
    private float mLineHeight;
    private int mPanelWidth;
    private Paint mPaint = new Paint();
    private float rowSize = 0.95f;
    private int s = 0;
    private int[][] p = new int[19][19];
    private ArrayList<Point> stars = new ArrayList<>();
    private ArrayList<Point> mWhiteArray = new ArrayList<>();
    private ArrayList<Point> mBlackArray = new ArrayList<>();
    Gojude Gogame = new Gojude("19路普通", p);

   public GameView(Context context) {
       super(context);
    }
    private boolean mIsBlack = true;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        for(int i = 3; i < 16; i = i + 6) {
            for(int j = 3; j < 16; j = j + 6) {
                stars.add(new Point(i, j));
            }
        }
        initPaint();
        initBitmap();
    }
    
    private void initBitmap() {
        mBlack = BitmapFactory.decodeResource(getResources(), R.drawable.stone_black);
        mWhite = BitmapFactory.decodeResource(getResources(), R.drawable.stone_white);
    }
    
    private void initPaint() {
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }
    
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int hightSize = MeasureSpec.getSize(heightMeasureSpec);
        int hightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = Math.min(widthSize, heightMeasureSpec);
        if(widthMode == MeasureSpec.UNSPECIFIED) {
            width = hightSize;
        }
        else if(hightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }
        setMeasuredDimension(width, width);
    }
    
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPanelWidth = w;
        mLineHeight = (((float)mPanelWidth * 1.0f) / MAX_LINE);
        int mWhiteWidth = (int)(mLineHeight * rowSize);
        mWhite = Bitmap.createScaledBitmap(mWhite, mWhiteWidth, mWhiteWidth, false);
        mBlack = Bitmap.createScaledBitmap(mBlack, mWhiteWidth, mWhiteWidth, false);
    }
    
    private void drawLine(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHeight;
        for(int i = 0; i < MAX_LINE; i++) {
            int startX = (int)(lineHeight / 2);
            int endX = (int)(w - (lineHeight / 2));
            int y = (int)((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, endX, y, mPaint);
            canvas.drawLine(y, startX, y, endX, mPaint);
        }
    }
    
    private void drawPieces(Canvas canvas) {
        System.out.println("绘制棋子函数呗调用");
        for(int i = 0; i < 19; i ++) {
            for(int j = 0; j < 19; j ++) {
                if(p[i][j] == 1) {
                    System.out.println("绘制黑棋:"+i+","+j);
                    canvas.drawBitmap(mBlack,( i +(1 - rowSize) / 2) * mLineHeight,(j + (1 - rowSize) / 2) * mLineHeight,null);
                }
                else if(p[i][j] == -1) {
                    System.out.println("绘制白棋:"+i+","+j);
                    canvas.drawBitmap(mWhite, (i + (1 - rowSize) / 2) * mLineHeight, (j + ((1 - rowSize) / 2)) * mLineHeight,null);
                    continue;
                }
            }
        }
    }

    private void drawPoints(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        for(int i = 0; i < stars.size(); i = i + 1) {
            canvas.drawCircle(((mLineHeight * 0.5f) + ((stars.get(i).x + ((1 - (rowSize / 0.95f)) / 2.0f)) * mLineHeight)), (((stars.get(i).y + ((1.0f - (rowSize / 0.95f)) / 2.0f)) * mLineHeight) + (mLineHeight * 0.5f)), (mLineHeight * 0.1f), mPaint);
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
        drawPoints(canvas);
        drawPieces(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                System.out.println("单击了鼠标");
                int x = (int) event.getX();
                int y = (int) event.getY();
                Point p = getValidPoint(x, y);
                String color = null;
                if (mIsBlack) {
                    color = "black";
                }
                else{
                    color = "white";
                }
                if (Gogame.setnextpiece(color, p.x, p.y)) {
                    System.out.println("棋子被添加");
                    if (mIsBlack) {
                        mWhiteArray.add(p);
                    }
                    else {
                        mBlackArray.add(p);
                    }
                    if (s == 0) {
                        mIsBlack = !mIsBlack;
                    }
                    invalidate();
                    return true;
                }
                return false;
            }
        }
        return true;
    }
    
    private Point getValidPoint(int x, int y) {
        return new Point((int)((float)x / mLineHeight), (int)((float)y / mLineHeight));
    }
    
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instance", super.onSaveInstanceState());
        return bundle;
    }
    
    public void RestartGame() {
        Gogame.setallback();
        mWhiteArray.clear();
        mBlackArray.clear();
        invalidate();
        mIsBlack = true;
    }
    
    public void Regret() {
        if(!Gogame.getPersteps().isEmpty()) {
            String color = Gogame.getPersteps().get((Gogame.getPersteps().size() - 1)).getColor();
            if(Gogame.setback()) {
                if("black".equals(color)) {
                    mIsBlack = true;
                } else {
                    mIsBlack = false;
                }
                invalidate();
            }
        }
    }
    
    public void HeiOnly() {
        s = 1;
        mIsBlack = true;
    }
    
    public void BaiOnly() {
        s = -1;
        mIsBlack = false;
    }
    
    public void AlterOnly() {
        s = 0;
    }
}
