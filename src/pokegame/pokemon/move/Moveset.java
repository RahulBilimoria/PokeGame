/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.pokemon.move;

/**
 *
 * @author Rahul
 */
public class Moveset {

    private Move move1, move2, move3, move4;

    public Moveset(Move move1) {
        this.move1 = new Move(move1);
        move2 = null;
        move3 = null;
        move4 = null;
    }

    public Moveset(Move move1, Move move2) {
        this.move1 = new Move(move1);
        this.move2 = new Move(move2);
        move3 = null;
        move4 = null;
    }

    public Moveset(Move move1, Move move2, Move move3) {
        this.move1 = new Move(move1);
        this.move2 = new Move(move2);
        this.move3 = new Move(move3);
        move4 = null;
    }

    public Moveset(Move move1, Move move2, Move move3, Move move4) {
        this.move1 = new Move(move1);
        this.move2 = new Move(move2);
        this.move3 = new Move(move3);
        this.move4 = new Move(move4);
    }

    public void heal() {
        move1.heal();
        move2.heal();
        move3.heal();
        move4.heal();
    }

    public Move getMove(int moveID) {
        switch (moveID) {
            case 0:
                return move1;
            case 1:
                return move2;
            case 2:
                return move3;
            case 3:
                return move4;
            default:
                return null;
        }
    }

    public void setMove(int moveID, Move move) {
        switch (moveID) {
            case 0:
                move1 = new Move(move);
                break;
            case 1:
                move2 = new Move(move);
                break;
            case 2:
                move3 = new Move(move);
                break;
            case 3:
                move4 = new Move(move);
                break;
            default:
                break;

        }
    }
    
    

    public Move getMove1() {
        return move1;
    }

    public Move getMove2() {
        return move2;
    }

    public Move getMove3() {
        return move3;
    }

    public Move getMove4() {
        return move4;
    }

    public void setMove1(Move move1) {
        this.move1 = move1;
    }

    public void setMove2(Move move2) {
        this.move2 = move2;
    }

    public void setMove3(Move move3) {
        this.move3 = move3;
    }

    public void setMove4(Move move4) {
        this.move4 = move4;
    }
}
