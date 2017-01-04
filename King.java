/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import static java.lang.Math.abs;

/**
 *
 * @author Gamon
 */
public class King extends Piece {

    public King(Color team, int let, int num, boolean isAlive) {
        super(team, let, num, 'K', isAlive);
    }

    @Override
    public Piece getTargetAndMoveTo(int let, int num, Board board) {
        if (canMoveTo(let, num, board) && 
            isNotOnTeamOf(board.getPiece(let, num)))
            {
                board.getPiece(let, num).setNextAliveFalse();
                setNextCoordinates(let, num);
                return board.getPiece(let, num);
            }

        return null;
    }

    @Override
    public void confirmCurrValues() {
        confirmCurrBaseValues();
    }

    @Override
    public void undoNextValues() {
        undoNextBaseValues();
    }

    @Override
    public boolean canMoveTo(int let, int num, Board board) {
        return abs(let - getLet()) < 2 && abs(num - getNum()) < 2 &&
                !(let == getLet() && num == getNum());
    }
    
}
