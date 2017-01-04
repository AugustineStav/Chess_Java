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
public class Rook extends FarMovingPiece {
    private boolean currFirstMove;
    private boolean prevFirstMove;
    
    public Rook(Color team, int let, int num, boolean isAlive) {
        super(team, let, num, 'R', isAlive);
        currFirstMove = true;
        prevFirstMove = true;
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
        prevFirstMove = currFirstMove;
    }

    @Override
    public void undoNextValues() {
        undoNextBaseValues();
        currFirstMove = prevFirstMove;
    }

    @Override
    public boolean canMoveTo(int let, int num, Board board) {
        return (canMoveUp(getLet(), getNum(), let, num, board)) ||
                (canMoveDown(getLet(), getNum(), let, num, board)) ||
                (canMoveLeft(getLet(), getNum(), let, num, board)) ||
                (canMoveRight(getLet(), getNum(), let, num, board));
    }
    
}
