/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 *
 * @author Gamon
 */
public class Bishop extends FarMovingPiece {
    
    public Bishop(Color team, int let, int num) {
        super(team, let, num, 'B', true);
    }

    @Override
    public Piece canMoveTo(int let, int num, Board board) {
        if ((getLet() == let && getNum() == num))
        {
            return null;
        }

        if (
            (canMoveDiagonalUpRight(getLet(), getNum(), let, num, board)) ||
            (canMoveDiagonalUpLeft(getLet(), getNum(), let, num, board)) ||
            (canMoveDiagonalDownRight(getLet(), getNum(), let, num, board)) ||
            (canMoveDiagonalDownLeft(getLet(), getNum(), let, num, board))
            )
        {
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
}
