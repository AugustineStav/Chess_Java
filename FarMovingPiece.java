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
public abstract class FarMovingPiece extends Piece{
    
    public FarMovingPiece(Color team, int let, int num, char symbol, boolean isAlive) {
        super(team, let, num, symbol, isAlive);
    }

    public boolean canMoveUp(int testLet, int testNum, int endLet, int endNum, Board board)
    {
        //reached destination on empty space:
        if (testLet == endLet && testNum == endNum)
        {
            return true;
        }

        //if the piece above is empty, try moving up:
        if (testNum + 1 <= 8 && board.getPiece(testLet, testNum + 1).isVacant())
        {
            return(canMoveUp(testLet, testNum + 1, endLet, endNum, board));
        }
        //otherwise, check if the next piece up is an enemy at the destination:
        else if (testNum + 1 <= 8 && isEnemyOf(board.getPiece(testLet, testNum + 1)) &&
                testLet == endLet && testNum + 1 == endNum)
        {
            return true;
        }

        return false;
    }
    
    public boolean canMoveDown(int testLet, int testNum, int endLet, int endNum, Board board)
    {
        //reached destination on empty space:
        if (testLet == endLet && testNum == endNum)
        {
            return true;
        }

        //if the piece above is empty, try moving up:
        if (testNum - 1 >= 1 && board.getPiece(testLet, testNum - 1).isVacant())
        {
            return(canMoveDown(testLet, testNum - 1, endLet, endNum, board));
        }
        //otherwise, check if the next piece up is an enemy at the destination:
        else if (testNum - 1 >= 1 && isEnemyOf(board.getPiece(testLet, testNum - 1)) &&
                testLet == endLet && testNum - 1 == endNum)
        {
            return true;
        }

        return false;
    }
    
    public boolean canMoveLeft(int testLet, int testNum, int endLet, int endNum, Board board)
    {
        //reached destination on empty space:
        if (testLet == endLet && testNum == endNum)
        {
            return true;
        }

        //if the piece above is empty, try moving up:
        if (testLet - 1 >= 1 && board.getPiece(testLet - 1, testNum).isVacant())
        {
            return(canMoveLeft(testLet - 1, testNum, endLet, endNum, board));
        }
        //otherwise, check if the next piece up is an enemy at the destination:
        else if (testLet - 1 >= 1 && isEnemyOf(board.getPiece(testLet - 1, testNum)) &&
                testLet - 1 == endLet && testNum== endNum)
        {
            return true;
        }

        return false;
    }

    public boolean canMoveRight(int testLet, int testNum, int endLet, int endNum, Board board)
    {
        //reached destination on empty space:
        if (testLet == endLet && testNum == endNum)
        {
            return true;
        }

        //if the piece above is empty, try moving up:
        if (testLet + 1 <= 8 && board.getPiece(testLet + 1, testNum).isVacant())
        {
            return(canMoveRight(testLet + 1, testNum, endLet, endNum, board));
        }
        //otherwise, check if the next piece up is an enemy at the destination:
        else if (testLet + 1 <= 8 && isEnemyOf(board.getPiece(testLet + 1, testNum)) &&
                testLet + 1 == endLet && testNum== endNum)
        {
            return true;
        }

        return false;
    }
    
    public boolean canMoveDiagonalUpRight(int testLet, int testNum, int endLet, int endNum, Board board)
    {
        //reached destination on empty space:
        if (testLet == endLet && testNum == endNum)
        {
            return true;
        }

        //if the piece above is empty, try moving up:
        if (testLet + 1 <= 8 && testNum + 1 <= 8 && board.getPiece(testLet + 1, testNum + 1).isVacant())
        {
            return(canMoveDiagonalUpRight(testLet + 1, testNum + 1, endLet, endNum, board));
        }
        //otherwise, check if the next piece up is an enemy at the destination:
        else if (testLet + 1 <= 8 && testNum + 1 <= 8 && isEnemyOf(board.getPiece(testLet + 1, testNum + 1)) &&
                testLet + 1 == endLet && testNum + 1 == endNum)
        {
            return true;
        }

        return false;
    }
    
    public boolean canMoveDiagonalUpLeft(int testLet, int testNum, int endLet, int endNum, Board board)
    {
        //reached destination on empty space:
        if (testLet == endLet && testNum == endNum)
        {
            return true;
        }

        //if the piece above is empty, try moving up:
        if (testLet - 1 >= 1 && testNum + 1 <= 8 && board.getPiece(testLet - 1, testNum + 1).isVacant())
        {
            return(canMoveDiagonalUpLeft(testLet - 1, testNum + 1, endLet, endNum, board));
        }
        //otherwise, check if the next piece up is an enemy at the destination:
        else if (testLet - 1 >= 1 && testNum + 1 <= 8 && isEnemyOf(board.getPiece(testLet - 1, testNum + 1)) &&
                testLet - 1 == endLet && testNum + 1 == endNum)
        {
            return true;
        }

        return false;
    }
    
    public boolean canMoveDiagonalDownRight(int testLet, int testNum, int endLet, int endNum, Board board)
    {
        //reached destination on empty space:
        if (testLet == endLet && testNum == endNum)
        {
            return true;
        }

        //if the piece above is empty, try moving up:
        if (testLet + 1 <= 8 && testNum - 1 >= 1 && board.getPiece(testLet + 1, testNum - 1).isVacant())
        {
            return(canMoveDiagonalDownRight(testLet + 1, testNum - 1, endLet, endNum, board));
        }
        //otherwise, check if the next piece up is an enemy at the destination:
        else if (testLet + 1 <= 8 && testNum - 1 >= 1 && isEnemyOf(board.getPiece(testLet + 1, testNum - 1)) &&
                testLet + 1 == endLet && testNum - 1 == endNum)
        {
            return true;
        }

        return false;
    }
    
    public boolean canMoveDiagonalDownLeft(int testLet, int testNum, int endLet, int endNum, Board board)
    {
        //reached destination on empty space:
        if (testLet == endLet && testNum == endNum)
        {
            return true;
        }

        //if the piece above is empty, try moving up:
        if (testLet - 1 >= 1 && testNum - 1 >= 1 && board.getPiece(testLet - 1, testNum - 1).isVacant())
        {
            return(canMoveDiagonalDownLeft(testLet - 1, testNum - 1, endLet, endNum, board));
        }
        //otherwise, check if the next piece up is an enemy at the destination:
        else if (testLet - 1 >= 1 && testNum - 1 >= 1 && isEnemyOf(board.getPiece(testLet - 1, testNum - 1)) &&
                testLet - 1 == endLet && testNum - 1 == endNum)
        {
            return true;
        }

        return false;
    }
}