package com.example.gwongsam.zonghenggochess;

/**
 * Created by gwongsam on 2017/12/13.
 */

public class PieceMsg {
    private String color;
    private int x;
    private int y;

    public PieceMsg(String color,int x,int y){
        this.color = color;
        this.x = x;
        this.y = y;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int cmp(PieceMsg piece1,PieceMsg piece2){
        if(piece1.x == piece2.x)
        {
            if(piece1.y == piece2.y)
            {
                if(piece1.color.equals(piece2.color)){
                    return 0;
                }
                else
                {
                    return 1;
                }
            }
        }
        return -1;
    }
}
