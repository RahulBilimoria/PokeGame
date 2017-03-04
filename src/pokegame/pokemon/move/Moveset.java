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
    
    public Moveset (){
        createMoveset(-1,-1,-1,-1);
    }

    public Moveset(int id1) {
        createMoveset(id1,-1,-1,-1);
    }

    public Moveset(int id1, int id2) {
        createMoveset(id1,id2,-1,-1);
    }

    public Moveset(int id1, int id2, int id3) {
        createMoveset(id1,id2,id3,-1);
    }

    public Moveset(int id1, int id2, int id3, int id4) {
        createMoveset(id1,id2,id3,id4);
    }

    public void heal() {
        if (move1 != null) {
            move1.heal();
        }
        if (move2 != null) {
            move2.heal();
        }
        if (move3 != null) {
            move3.heal();
        }
        if (move4 != null) {
            move4.heal();
        }
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
    
    public String getMoveName(int moveId){
        switch (moveId) {
            case 0:
                if (move1 == null)
                    break;
                return move1.getName();
            case 1:
                if (move2 == null)
                    break;
                return move2.getName();
            case 2:
                if (move3 == null)
                    break;
                return move3.getName();
            case 3:
                if (move4 == null)
                    break;
                return move4.getName();
            default:
                return "null";
        }
        return "null";
    }

    public void setMove(int moveID, Move move) {
        switch (moveID) {
            case 0:
                move1 = new Move(move.getId());
                break;
            case 1:
                move2 = new Move(move.getId());
                break;
            case 2:
                move3 = new Move(move.getId());
                break;
            case 3:
                move4 = new Move(move.getId());
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
    
    public void setMovePP(int moveID, int pp){
        if (getMove(moveID) != null){
            getMove(moveID).setPP(pp);
        }
    }
    
    public void createMoveset(int id1, int id2, int id3, int id4){
        move1 = createMove(id1);
        move2 = createMove(id2);
        move3 = createMove(id3);
        move4 = createMove(id4);
    }
    
    public Move createMove(int id){
        if (id >= 0){
            return new Move(id);
        }
        return null;
    }
}
