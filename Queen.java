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
public class Queen extends FarMovingPiece {
    
    public Queen(Color team, int let, int num, boolean isAlive) {
        super(team, let, num, 'Q', isAlive);
    }

    @Override
    public Piece getTargetAndMoveTo(int let, int num, Board board) {
        if ((getLet() == let && getNum() == num))
        {
            return null;
        }

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
        return (canMoveUp(getLet(), getNum(), let, num, board)) ||
                (canMoveDown(getLet(), getNum(), let, num, board)) ||
                (canMoveLeft(getLet(), getNum(), let, num, board)) ||
                (canMoveRight(getLet(), getNum(), let, num, board)) ||
                (canMoveDiagonalUpRight(getLet(), getNum(), let, num, board)) ||
                (canMoveDiagonalUpLeft(getLet(), getNum(), let, num, board)) ||
                (canMoveDiagonalDownRight(getLet(), getNum(), let, num, board)) ||
                (canMoveDiagonalDownLeft(getLet(), getNum(), let, num, board));
    }
}