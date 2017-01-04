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
public class Knight extends Piece {

    public Knight(Color team, int let, int num, boolean isAlive) {
        super(team, let, num, 'H', isAlive);
    }

    @Override
    public Piece getTargetAndMoveTo(int let, int num, Board board) {
        if (
            canMoveTo(let, num, board)
        )
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
        return ((abs(let - getLet()) == 2 && abs(num - getNum()) == 1) ||
                (abs(let - getLet()) == 1 && abs(num - getNum()) == 2)) &&
                isNotOnTeamOf(board.getPiece(let, num));
    }
    
}
