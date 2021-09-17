package com.example.gwongsam.zonghenggochess;

import java.util.ArrayList;

/**
 * Created by gwongsam on 2017/12/13.
 */

public class Steps_killpiece {private int piece;
    private ArrayList<PieceMsg> killpiece;

    Steps_killpiece (int piece,ArrayList<PieceMsg> killpiece){
        this.piece = piece;
        this.killpiece = killpiece;
    }

    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }

    public ArrayList<PieceMsg> getKillpiece() {
        return killpiece;
    }

    public void setKillpiece(ArrayList<PieceMsg> killpiece) {
        this.killpiece = killpiece;
    }
}
