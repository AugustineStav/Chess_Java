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
public enum Color {
    BLACK(0), WHITE(1), VACANT(3);
    
    private final int value;
    Color(final int value) {
        this.value = value;
    }

    public int getValue() { return value; }
}
