package com.example.gwongsam.zonghenggochess;

import java.util.ArrayList;

/**
 * Created by gwongsam on 2017/12/13.
 */

public class Gojude {
    private final int black = 1;
    private final int white = -1;
    private final int empty = 0;
    private int board_size;
    private ArrayList<PieceMsg> persteps = new ArrayList<PieceMsg>(); // 记录每步棋子
    private int[][] perGoboard; // 当前棋盘上摆的棋子
    private ArrayList<Steps_killpiece> persteps_killpiece = new ArrayList<Steps_killpiece>(); // 每步提了子的棋和它所提的子

    public ArrayList<PieceMsg> getPersteps(){
        return persteps;
    }
    public Gojude(String type,int [][] perGoboard) {
        if ("19路普通".equals(type)) {
            this.board_size = 19;
        }
		/*perGoboard = new int[this.board_size][this.board_size];
		for (int i = 0; i < this.board_size; i++)
			for (int j = 0; i < this.board_size; j++) {
				perGoboard[i][j] = empty;
			}*/
        this.perGoboard = perGoboard;
    }

    // 用于判断所申请的一步棋是否被允许的
    public boolean is_PermissiblePiece(String color, int x, int y) {
        // 当前位置有子，则直接返回false
        if (perGoboard[x][y] != empty)
            return false;

        // 如果当前位置四周有空位则直接返回true
        if ((x - 1 >= 0 && perGoboard[x - 1][y] == empty)
                || (x + 1 < board_size && perGoboard[x + 1][y] == empty)
                || (y - 1 >= 0 && perGoboard[x][y - 1] == empty)
                || (y + 1 <board_size && perGoboard[x][y + 1] == empty)) {
            return true;
        }

        // 判断打劫情况
        int ko = is_Ko(color, x, y);
        if (ko == 0) {
            return false;
        }
        if (ko == 1) {
            return true;
        }

        // 如果周围有相同颜色棋子，任意一相同颜色子的气大于零，则返回true
        // 如果周围有不同颜色的棋子，任意一块不相同颜色的棋子没有气，则返回true
        String SKocolor;
        Object[] adjoinPiece = new Object[4];
        if (x - 1 >= 0) {
            if (perGoboard[x - 1][y] == 1)
                SKocolor = "black";
            else
                SKocolor = "white";
            adjoinPiece[0] = new Pieceblock(new PieceMsg(SKocolor, x - 1, y),
                    perGoboard);
            if (color.equals(SKocolor)) {
                if (((Pieceblock) adjoinPiece[0]).getQI() > 1) {
                    return true;
                }
            } else if (((Pieceblock) adjoinPiece[0]).getQI() <= 1) {
                return true;
            }
        }
        if (x + 1 < board_size) {
            if (perGoboard[x + 1][y] == 1)
                SKocolor = "black";
            else
                SKocolor = "white";
            adjoinPiece[1] = new Pieceblock(new PieceMsg(SKocolor, x + 1, y),
                    perGoboard);
            if (color.equals(SKocolor)) {
                if (((Pieceblock) adjoinPiece[1]).getQI() > 1) {
                    return true;
                }
            } else if (((Pieceblock) adjoinPiece[1]).getQI() <= 1) {
                return true;
            }
        }
        if (y - 1 >= 0) {
            if (perGoboard[x][y - 1] == 1)
                SKocolor = "black";
            else
                SKocolor = "white";
            adjoinPiece[2] = new Pieceblock(new PieceMsg(SKocolor, x, y - 1),
                    perGoboard);
            if (color.equals(SKocolor)) {
                if (((Pieceblock) adjoinPiece[2]).getQI() > 1) {
                    return true;
                }
            } else if (((Pieceblock) adjoinPiece[2]).getQI() <= 1) {
                return true;
            }

        }
        if (y + 1 < board_size) {
            if (perGoboard[x][y + 1] == 1)
                SKocolor = "black";
            else
                SKocolor = "white";
            adjoinPiece[3] = new Pieceblock(new PieceMsg(SKocolor, x, y + 1),
                    perGoboard);
            if (color.equals(SKocolor)) {
                if (((Pieceblock) adjoinPiece[3]).getQI() > 1) {
                    return true;
                }
            } else if (((Pieceblock) adjoinPiece[3]).getQI() <= 1) {
                return true;
            }
        }

        return false;
    }

    // 设置下一步
    public boolean setnextpiece(String color, int x, int y) {
        // 看是否能下
        if (!is_PermissiblePiece(color, x, y))
            return false;
        persteps.add(new PieceMsg(color, x, y));
        int Inocolor;
        if("black".equals(color)){
            perGoboard[x][y] = black;
        }
        else{
            perGoboard[x][y] = white;
        }

        // 看是否有吃子
        ArrayList<PieceMsg> killpiece = new ArrayList<PieceMsg>();
        String SKocolor;
        if("black".equals(color)){
            SKocolor = "white";
            Inocolor = -1;
        }
        else if("white".equals(color)){
            SKocolor = "black";
            Inocolor = 1;
        }
        else{
            SKocolor = "empty";
            Inocolor = 0;
        }
        if(SKocolor == null){
            System.out.print("SKocolor为空");
        }
        else
        {
            System.out.print("SKocolor不为空");

        }
        // 左方
        if (x - 1 >= 0) {
            if (perGoboard[x-1][y] == Inocolor) {
                Pieceblock left = new Pieceblock(new PieceMsg(SKocolor, x - 1,
                        y), perGoboard);
                if(left.getColor() == null){
                    System.out.println("分组时出错");
                }
                else{
                    System.out.println("分组时没出错");
                }
                if(left.getQI()<=0){
                    killpiece.addAll(left.getAllpiece());
                }
            }
        }

        // 上方
        if (y - 1 >= 0) {
            if (perGoboard[x][y-1] == Inocolor) {
                Pieceblock left = new Pieceblock(new PieceMsg(SKocolor, x ,
                        y - 1), perGoboard);
                if(left.getColor() == null){
                    System.out.println("分组时出错");
                }
                else{
                    System.out.println("分组时没出错");
                }
                if(left.getQI()<=0){
                    killpiece.addAll(left.getAllpiece());
                }
            }
        }
        // 右方
        if (x + 1 < board_size) {
            if (perGoboard[x+1][y] == Inocolor) {
                Pieceblock left = new Pieceblock(new PieceMsg(SKocolor, x + 1,
                        y), perGoboard);
                if(left.getColor() == null){
                    System.out.println("分组时出错");
                }
                else{
                    System.out.println("分组时没出错");
                }
                if(left.getQI()<=0){
                    killpiece.addAll(left.getAllpiece());
                }
            }
        }

        // 下方
        if (y + 1 < board_size) {
            if (perGoboard[x][y+1] == Inocolor) {
                Pieceblock left = new Pieceblock(new PieceMsg(SKocolor, x ,
                        y + 1), perGoboard);
                if(left.getColor() == null){
                    System.out.println("分组时出错");
                }
                else{
                    System.out.println("分组时没出错");
                }
                if(left.getQI()<=0){
                    killpiece.addAll(left.getAllpiece());
                }
            }
        }

        if(killpiece.size() > 0)
        {
            persteps_killpiece.add(new Steps_killpiece(persteps.size()-1,killpiece));
            System.out.print("第"+persteps.size()+"手吃子:");
            for(int i=0;i<killpiece.size();i++){
                perGoboard[killpiece.get(i).getX()][killpiece.get(i).getY()] = empty;
                System.out.print("("+killpiece.get(i).getX()+","+killpiece.get(i).getY()+") ");
            }
            System.out.println();
        }
        return true;
    }

    // 判断是否为打劫，是且可以下返回1，不可以下返回0，不是劫返回-1
    public int is_Ko(String color, int x, int y) {
        int Kocolor;
        String SKocolor;
        if ("black".equals(color)) {
            Kocolor = white;
            SKocolor = "white";
        } else {
            Kocolor = black;
            SKocolor = "black";
        }

        // 周围不都是异色棋子就直接返回-1
        if ((x - 1 >= 0 && perGoboard[x - 1][y] != Kocolor)
                || (x + 1 < board_size && perGoboard[x + 1][y] != Kocolor)
                || (y - 1 >= 0 && perGoboard[x][y - 1] != Kocolor)
                || (y + 1 < board_size && perGoboard[x][y + 1] != Kocolor)) {
            System.out.println("打劫没有问题");
            return -1;
        }

        int oneQIsum = 0;
        int oneQIpoint = 0;
        Object[] adjoinPiece = new Object[4];
        if (x - 1 >= 0) {
            adjoinPiece[0] = new Pieceblock(new PieceMsg(SKocolor, x - 1, y),
                    perGoboard);
            if (((Pieceblock) adjoinPiece[0]).getQI() == 1) {
                oneQIpoint = 0;
                oneQIsum++;
            }
        }
        if (x + 1 < board_size) {
            adjoinPiece[1] = new Pieceblock(new PieceMsg(SKocolor, x + 1, y),
                    perGoboard);
            if (((Pieceblock) adjoinPiece[1]).getQI() == 1) {
                oneQIpoint = 1;
                oneQIsum++;
            }
        }
        if (y - 1 >= 0) {
            adjoinPiece[2] = new Pieceblock(new PieceMsg(SKocolor, x, y - 1),
                    perGoboard);
            if (((Pieceblock) adjoinPiece[2]).getQI() == 1) {
                oneQIpoint = 2;
                oneQIsum++;
            }
        }
        if (y + 1 < board_size) {
            adjoinPiece[3] = new Pieceblock(new PieceMsg(SKocolor, x, y + 1),
                    perGoboard);
            if (((Pieceblock) adjoinPiece[3]).getQI() == 1) {
                oneQIpoint = 3;
                oneQIsum++;
            }
        }

        if (oneQIsum != 1) {
            System.out.println("打劫没有问题");
            return -1;
        }

        Pieceblock oneQIPiece = (Pieceblock) adjoinPiece[oneQIpoint];
        if (oneQIPiece.getPiece_num() == 1) {
            if (0 == oneQIPiece.getAllpiece().get(0).cmp(oneQIPiece.getAllpiece().get(0),
                            persteps.get(persteps.size() - 1))) {
                if(persteps_killpiece.isEmpty())
                    return 1;
                if (persteps_killpiece.get(persteps_killpiece.size() - 1)
                        .getPiece() == persteps.size() - 1) {
                    if (persteps_killpiece.get(persteps_killpiece.size() - 1)
                            .getKillpiece().size() == 1) {
                        PieceMsg choisepiece = new PieceMsg(color, x, y);
                        if (choisepiece.cmp(
                                persteps_killpiece
                                        .get(persteps_killpiece.size() - 1)
                                        .getKillpiece().get(0), choisepiece) == 0) {
                            System.out.println("打劫没有问题");
                            return 0;
                        }
                    }
                }
            }
            System.out.println("打劫没有问题");
            return 1;
        }

        System.out.println("打劫没有问题");
        return -1;
    }

    // 悔棋
    public boolean setback() {
        if (persteps.isEmpty()) {
            return false;
        }


        if(persteps_killpiece.isEmpty()){
            // 将persteps最后一步删除
            perGoboard[persteps.get(persteps.size() - 1).getX()][persteps.get(
                    persteps.size() - 1).getY()] = empty;
            persteps.remove(persteps.size() - 1);
            return true;
        }

        // 如果悔的那步棋有提子，直接将提子放回棋盘
        if (persteps_killpiece.get(persteps_killpiece.size() - 1).getPiece() == persteps
                .size()-1) {
            ArrayList<PieceMsg> piece_return = persteps_killpiece.get(
                    persteps_killpiece.size() - 1).getKillpiece();

            int percolor;
            System.out.println("棋子颜色为："+persteps_killpiece.get(
                    persteps_killpiece.size() - 1).getKillpiece().get(0).getColor());
            if ("black".equals(piece_return.get(0).getColor()))
                percolor = black;
            else
                percolor = white;
            while (!piece_return.isEmpty()) {
                perGoboard[piece_return.get(0).getX()][piece_return.get(0)
                        .getY()] = percolor;
                piece_return.remove(0);
            }
            persteps_killpiece.remove(persteps_killpiece.size() - 1);
        }

        // 将persteps最后一步删除
        perGoboard[persteps.get(persteps.size() - 1).getX()][persteps.get(
                persteps.size() - 1).getY()] = empty;
        persteps.remove(persteps.size() - 1);

        return true;
    }

    public boolean setallback(){
        for(int i=0;i<board_size;i++){
            for(int j=0;j<board_size;j++)
            {
                perGoboard[i][j] = empty;
            }
        }
        persteps.clear();
        persteps_killpiece.clear();
        return true;
    }

}
