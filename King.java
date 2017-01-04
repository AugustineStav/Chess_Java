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
    private boolean firstMove;

    public King(Color team, int let, int num, boolean isAlive) {
        super(team, let, num, 'K', isAlive);
        this.firstMove = true;
    }
    
    public King(King otherKing)
    {
        super(otherKing);
        this.firstMove = otherKing.firstMove;
    }
    
    @Override
    public King copy() {
        return new King(this);
    }

    @Override
    public boolean moveTo(int let, int num, Board board) {
        if (canMoveTo(let, num, board) && 
            isNotOnTeamOf(board.getPiece(let, num)))
        {
            firstMove = false;
            board.getPiece(let, num).setNextAliveFalse();
            setNextCoordinates(let, num);
            return true;
        }

        return castle(let, num, board);
    }
    
    private boolean castle(int let, int num, Board board)
    {
        if (getNum() == num && let - getLet() == -2 && 
                firstMove && board.getPiece(1, num).canCastle() && 
                isOnTeam(board.getPiece(1, num).getTeam()) &&
                board.getPiece(2, num).isVacant() &&
                board.getPiece(3, num).isVacant() &&
                board.getPiece(4, num).isVacant())
        {
            setNextCoordinates(4, num);
            board.updateBoard();
            if(board.isInCheck(getTeam()))
            {
                //passed through check, reset the board:
                setNextCoordinates(5, num);
                return false;
            }
            board.getPiece(1, num).setNextCoordinates(4, num);
            setNextCoordinates(3, num);            
            firstMove = false;
            return true;
        }
        if (getNum() == num && let - getLet() == 2 && 
                firstMove && board.getPiece(8, num).canCastle() && 
                isOnTeam(board.getPiece(8, num).getTeam()) &&
                board.getPiece(7, num).isVacant() &&
                board.getPiece(6, num).isVacant())
        {
            setNextCoordinates(6, num);
            board.updateBoard();
            if(board.isInCheck(getTeam()))
            {
                //passed through check, reset the board:
                setNextCoordinates(5, num);
                return false;
            }
            board.getPiece(8, num).setNextCoordinates(6, num);
            setNextCoordinates(7, num);            
            firstMove = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean canMoveTo(int let, int num, Board board) {
        return abs(let - getLet()) < 2 && abs(num - getNum()) < 2 &&
                !(let == getLet() && num == getNum());
    }
    
    @Override
    public boolean isEqualTo(Piece otherPiece) {
        if (otherPiece instanceof King)
        {
            return (this.firstMove == ((King)otherPiece).firstMove &&
                    isEqualToBaseValues(otherPiece));
        }
        
        return false;
    }
    
    @Override
    public boolean canCastle() {
        return false;
    }

    @Override
    public boolean isVulnerableToEnPassant() {
        return false;
    }
}
