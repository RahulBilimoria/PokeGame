/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.entity.ai;

import pokegame.pokemon.Pokemon;
import pokegame.pokemon.move.Move;
import pokegame.pokemon.status.Status;
import pokegame.type.Type;

/**
 *
 * @author Rahul
 */
public class AI {

    public static Pokemon chooseNextPokemon(int difficulty, Pokemon[] yourParty, Pokemon activePokemon) {
        switch (difficulty) {
            case 0:
                return easyP(yourParty);
            case 1:
                return mediumP(yourParty, activePokemon);
            case 2:
                return hardP(yourParty, activePokemon);
            default:
                return null;
        }
    }

    public static int chooseNextMove(int difficulty, Pokemon yours, Pokemon theirs) {
        switch (difficulty) {
            case 0:
                return easyM(yours);
            case 1:
                return mediumM(yours, theirs);
            case 2:
                return hardM(yours, theirs);
            default:
                return -1;
        }
    }

    private static Pokemon easyP(Pokemon[] yourParty) {
        for (int x = 0; x < 6; x++) {
            Pokemon p = yourParty[x];
            if (p != null) {
                if (p.getHp() > 0) {
                    return p;
                }
            }
        }
        return null;
    }

    private static Pokemon mediumP(Pokemon[] yourParty, Pokemon activePokemon) {
        int moves;
        float score;
        float bestScore = 0;
        int bestPokemonIndex = 0;
        for (int x = 0; x < 6; x++) {
            moves = 0;
            score = 0;
            Pokemon p = yourParty[x];
            if (p != null) {
                if (p.getHp() > 0) {
                    Type type1 = activePokemon.getType1();
                    Type type2 = activePokemon.getType2();
                    Move move1 = p.getMoveset().getMove1();
                    Move move2 = p.getMoveset().getMove2();
                    Move move3 = p.getMoveset().getMove3();
                    Move move4 = p.getMoveset().getMove4();
                    if (move1 != null) {
                        moves++;
                        score = score + (Type.getEffectiveness(move1.getType(), type1, type2) * 5 * (move1.getPP() / move1.getMaxPP()));
                    }
                    if (move2 != null) {
                        moves++;
                        score = score + (Type.getEffectiveness(move2.getType(), type1, type2) * 5 * (move2.getPP() / move2.getMaxPP()));
                    }
                    if (move3 != null) {
                        moves++;
                        score = score + (Type.getEffectiveness(move3.getType(), type1, type2) * 5 * (move3.getPP() / move3.getMaxPP()));
                    }
                    if (move4 != null) {
                        moves++;
                        score = score + (Type.getEffectiveness(move4.getType(), type1, type2) * 5 * (move4.getPP() / move4.getMaxPP()));
                    }
                    score = score / moves;
                }
                //Trainers pokemon moves vs ur pokemon type
            }
            if (score > bestScore) {
                bestScore = score;
                bestPokemonIndex = x;
            }
        }
        if (bestScore == 0) {
            return null;
        }
        return yourParty[bestPokemonIndex];
    }

    private static Pokemon hardP(Pokemon[] yourParty, Pokemon activePokemon) {
        float score = 0;
        float bestScore = 0;
        int bestPokemonIndex = 0;
        for (int x = 0; x < 6; x++) {
            Pokemon p = yourParty[x];
            int moves = 0;
            score = 0;
            if (p != null) {
                if (p.getHp() > 0) {
                    Type type1 = activePokemon.getType1();
                    Type type2 = activePokemon.getType2();
                    Move move1 = p.getMoveset().getMove1();
                    Move move2 = p.getMoveset().getMove2();
                    Move move3 = p.getMoveset().getMove3();
                    Move move4 = p.getMoveset().getMove4();
                    if (move1 != null) {
                        moves++;
                        score = score + (Type.getEffectiveness(move1.getType(), type1, type2) * 5 * (move1.getPP() / move1.getMaxPP()));
                    }
                    if (move2 != null) {
                        moves++;
                        score = score + (Type.getEffectiveness(move2.getType(), type1, type2) * 5 * (move2.getPP() / move2.getMaxPP()));
                    }
                    if (move3 != null) {
                        moves++;
                        score = score + (Type.getEffectiveness(move3.getType(), type1, type2) * 5 * (move3.getPP() / move3.getMaxPP()));
                    }
                    if (move4 != null) {
                        moves++;
                        score = score + (Type.getEffectiveness(move4.getType(), type1, type2) * 5 * (move4.getPP() / move4.getMaxPP()));
                    }
                    score = score / moves;
                    float hp = p.getHp() / (float) p.getMaxHp();
                    if (hp <= 0.25) {
                        score += 1;
                    } else if (hp <= 0.5) {
                        score += 2;
                    } else if (hp <= 0.75) {
                        score += 5;
                    } else {
                        score += 10;
                    }
                    score = score + ((p.getLevel() / activePokemon.getLevel()) * 10);
                    Status cond = p.getStatus();
                    if (cond == Status.NORMAL) {
                        score += 10;
                    } else if (cond == Status.POISONED || cond == Status.BURN || cond == Status.CONFUSION) {
                        score -= 5;
                    } else {
                        score -= 10;
                    }
                    //Trainers pokemon moves vs ur pokemon type
                }
            }
            if (score > bestScore) {
                bestScore = score;
                bestPokemonIndex = x;
            }
        }
        if (bestScore == 0) {
            return null;
        }
        return yourParty[bestPokemonIndex];
    }

    private static int easyM(Pokemon yours) {
        for (int x = 0; x < 4; x++) {
            Move m = yours.getMoveset().getMove(x);
            if (m != null) {
                if (m.getPP() == 0) {
                    continue;
                }
                if (m.getCategory() == 0 || m.getCategory() == 1) {
                    return x;
                }
            }
        }
        return -1;
    }

    private static int mediumM(Pokemon yours, Pokemon theirs) {
        float score;
        float bestScore = 0;
        int moveID = -1;
        Type type1 = theirs.getType1();
        Type type2 = theirs.getType2();
        for (int x = 0; x < 4; x++) {
            score = 0;
            Move m = yours.getMoveset().getMove(x);
            if (m != null) {
                if (m.getPP() > 0) {
                    score += Type.getEffectiveness(m.getType(), type1, type2) * 5;
                    if (m.getCategory() == 2) {
                        score = score * 0.75f;
                        if (theirs.getHp() / (float) theirs.getMaxHp() < 0.25) {
                            score = score * 0.5f;
                        }
                    }
                }
            }
            if (score > bestScore) {
                bestScore = score;
                moveID = x;
            }
        }
        return moveID;
    }

    private static int hardM(Pokemon yours, Pokemon theirs) {
        float score;
        float bestScore = 0;
        int moveID = -1;
        Type type1 = theirs.getType1();
        Type type2 = theirs.getType2();
        for (int x = 0; x < 4; x++) {
            score = 0;
            Move m = yours.getMoveset().getMove(x);
            if (m != null) {
                if (m.getPP() > 0) {
                    score += Type.getEffectiveness(m.getType(), type1, type2) * 5;
                    if (m.getCategory() == 2) {
                        score = score * 0.75f;
                        if (theirs.getHp() / (float) theirs.getMaxHp() < 0.25) {
                            score = score * 0.5f;
                        }
                    }
                }
            }
            if (score > bestScore) {
                bestScore = score;
                moveID = x;
            }
        }
        return moveID;
    }
}
