package com.example.gwongsam.zonghenggochess;

import java.util.ArrayList;

/**
 * Created by gwongsam on 2017/12/13.
 */

public class Pieceblock {private int QI;
    private int piece_num;
    private String color;
    private ArrayList<PieceMsg> allpiece = new ArrayList<>();
    private int realEyes_num;
    private int halfEyes_num;

    Pieceblock (PieceMsg onepiece,int [][] perGoboard){
        QI = 0;
        color = onepiece.getColor();
        findallByonepiece(onepiece,perGoboard);
        piece_num = allpiece.size();
        if(color == null){
            System.out.println("棋块颜色为空");
        }
    }


    public int getQI() {
        return QI;
    }
    public void setQI(int qI) {
        QI = qI;
    }
    public int getPiece_num() {
        return piece_num;
    }
    public void setPiece_num(int Piece_num) {
        piece_num = Piece_num;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public ArrayList<PieceMsg> getAllpiece() {
        return allpiece;
    }
    public void setAllpiece(ArrayList<PieceMsg> allpiece) {
        this.allpiece = allpiece;
    }
    public int getRealEyes_num() {
        return realEyes_num;
    }
    public void setRealEyes_num(int realEyes_num) {
        this.realEyes_num = realEyes_num;
    }
    public int getHalfEyes_num() {
        return halfEyes_num;
    }
    public void setHalfEyes_num(int halfEyes_num) {
        this.halfEyes_num = halfEyes_num;
    }

    //用一颗棋子找到那个棋块，返回棋块的气
    public void findallByonepiece(PieceMsg onepiece,int [][] perGoboard){
        int boardsize = perGoboard.length;						//棋盘宽度
        int boardarea = boardsize*boardsize;					//棋盘大小
        boolean [][] Is_readed = new boolean[boardsize][boardsize];	//遍历历史
        int percolor;
        ArrayList<Integer> recursion = new ArrayList<Integer>();	//保存递归栈

        if("black".equals(onepiece.getColor()))
            percolor = 1;
        else
            percolor = -1;
        recursion.add(onepiece.getY()*boardsize+onepiece.getX());
        allpiece.add(new PieceMsg(color,onepiece.getX(),onepiece.getY()));

        int respect;							//遍历方向
        int perpiece = onepiece.getY()*boardsize+onepiece.getX();	//当前遍历棋子
        Is_readed[onepiece.getX()][onepiece.getY()] = true;
        System.out.println("棋子：（"+onepiece.getX()+","+onepiece.getY()+")入栈");
        System.out.println("onepiece：("+onepiece.getX()+","+onepiece.getY()+")");
        System.out.println("perpiece："+perpiece);
        while(!recursion.isEmpty()){
            System.out.println("递归数组大小"+recursion.size());
            System.out.println("当前元素"+perpiece);
            respect = perpiece/boardarea;
            System.out.println("当前方向"+respect);
            System.out.println("棋盘大小"+boardarea+"\n");
            //改变方向
            recursion.set(recursion.size()-1, recursion.get(recursion.size()-1)+boardarea);
            //棋子上方
            if(respect == 0)
            {
                System.out.println("当前棋子：（"+perpiece%boardsize+","+perpiece/boardsize+")");
                //下标不越界
                if(perpiece-boardsize>=0){
                    int mx = perpiece%boardsize;
                    int my = perpiece/boardsize-1;
                    System.out.println("棋子上方：（"+mx+","+my+")");
                    //棋子未遍历过
                    if(!Is_readed[mx][my]){
                        //同色棋
                        if(perGoboard[mx][my] == percolor){
                            recursion.add(perpiece-boardsize);
                            allpiece.add(new PieceMsg(color,mx,my));
                            System.out.println("棋子：（"+mx+","+my+")入栈");
                            Is_readed[mx][my] = true;
                            perpiece = perpiece-boardsize;
                        }
                        //没棋子,气加一
                        else if(perGoboard[mx][my] == 0){
                            QI++;
                            Is_readed[mx][my] = true;
                            perpiece += boardarea;
                        }
                        //异色棋
                        else{
                            Is_readed[mx][my] = true;
                            perpiece += boardarea;
                        }
                    }
                    else{
                        perpiece += boardarea;
                    }
                }
                else{
                    perpiece += boardarea;
                }
            }

            //棋子右方
            else if(respect == 1)
            {
                System.out.println("当前棋子：（"+perpiece%boardsize+","+(perpiece-boardarea)/boardsize+")");
                //下标不越界
                if((perpiece-boardarea)%boardsize+1 < boardsize){
                    int mx = (perpiece-boardarea+1)%boardsize;
                    int my = (perpiece-boardarea+1)/boardsize;
                    System.out.println("棋子右方：（"+mx+","+my+")");
                    //棋子未遍历过
                    if(!Is_readed[mx][my]){
                        //同色棋
                        if(perGoboard[mx][my] == percolor){
                            recursion.add(perpiece-boardarea+1);
                            allpiece.add(new PieceMsg(color,mx,my));
                            System.out.println("棋子：（"+mx+","+my+")入栈");
                            Is_readed[mx][my] = true;
                            perpiece = perpiece-boardarea+1;
                        }
                        //没棋子
                        else if(perGoboard[mx][my] == 0){
                            QI++;
                            Is_readed[mx][my] = true;
                            perpiece += boardarea;
                        }
                        //异色棋
                        else{
                            Is_readed[mx][my] = true;
                            perpiece += boardarea;
                        }
                    }
                    else{
                        perpiece += boardarea;
                    }
                }
                else{
                    perpiece += boardarea;
                }
            }

            //棋子下方
            else if(respect == 2)
            {
                System.out.println("当前棋子：（"+perpiece%boardsize+","+(perpiece-boardarea*2)/boardsize+")");
                //下标不越界
                if(perpiece-boardarea*2+boardsize < boardarea){
                    int mx = (perpiece-boardarea*2+boardsize)%boardsize;
                    int my = (perpiece-boardarea*2+boardsize)/boardsize;
                    System.out.println("棋子下方：（"+mx+","+my+")");
                    //棋子未遍历过
                    if(!Is_readed[mx][my]){
                        //同色棋
                        if(perGoboard[mx][my] == percolor){
                            recursion.add(perpiece-boardarea*2+boardsize);
                            allpiece.add(new PieceMsg(color,mx,my));
                            System.out.println("棋子：（"+mx+","+my+")入栈");
                            Is_readed[mx][my] = true;
                            perpiece = perpiece-boardarea*2+boardsize;
                        }
                        //没棋子
                        else if(perGoboard[mx][my] == 0){
                            QI++;
                            Is_readed[mx][my] = true;
                            perpiece += boardarea;
                        }
                        //异色棋
                        else{
                            Is_readed[mx][my] = true;
                            perpiece += boardarea;
                        }
                    }
                    else{
                        perpiece += boardarea;
                    }
                }
                else{
                    perpiece += boardarea;
                }
            }

            //棋子左方
            else if(respect == 3)
            {
                System.out.println("当前棋子：（"+perpiece%boardsize+","+(perpiece-boardarea*3)/boardsize+")");
                //下标不越界
                if((perpiece-boardarea*3)%boardsize-1 >= 0){
                    int mx = (perpiece-boardarea*3-1)%boardsize;
                    int my = (perpiece-boardarea*3-1)/boardsize;
                    System.out.println("棋子左方：（"+mx+","+my+")");
                    //棋子未遍历过
                    if(!Is_readed[mx][my]){
                        System.out.println("棋子未遍历过");
                        //同色棋
                        if(perGoboard[mx][my] == percolor){
                            recursion.add(perpiece-boardarea*3-1);
                            allpiece.add(new PieceMsg(color,mx,my));
                            System.out.println("棋子：（"+mx+","+my+")入栈");
                            Is_readed[mx][my] = true;
                            perpiece = perpiece-boardarea*3-1;
                        }
                        //没棋子
                        else if(perGoboard[mx][my] == 0){
                            QI++;
                            Is_readed[mx][my] = true;
                            perpiece += boardarea;
                        }
                        //异色棋
                        else{
                            Is_readed[mx][my] = true;
                            perpiece += boardarea;
                        }
                    }
                    else{
                        perpiece += boardarea;
                    }
                }
                else{
                    perpiece += boardarea;
                }
            }

            //该棋子遍历完，将其出栈，取下一元素
            else
            {
                recursion.remove(recursion.size()-1);
                if(!recursion.isEmpty())
                    perpiece = recursion.get(recursion.size()-1);
            }


        }

    }
}
