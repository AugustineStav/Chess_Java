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

    public King(Color team, int let, int num) {
        super(team, let, num, 'K', true);
    }

    @Override
    public boolean canMoveTo(int let, int num, Board board) {
        if (abs(let - getLet()) < 2 && abs(num - getNum()) < 2 &&
            !(let == getLet() && num == getNum()) && 
            isNotOnTeamOf(board.getPiece(let, num)))
            {
                board.getPiece(let, num).setNextAliveFalse();
                setNextCoordinates(let, num);
                
                if (!board.movedIntoCheck())
                {
                    board.getPiece(let, num).confirmCurrValues();
                    confirmCurrValues();
                    return true;
                }
            }
        board.getPiece(let, num).undoNextValues();
        undoNextValues();
        return false;
    }

    @Override
    public void confirmCurrValues() {
        confirmCurrBaseValues();
    }

    @Override
    public void undoNextValues() {
        undoNextBaseValues();
    }
    
}
