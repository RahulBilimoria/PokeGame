/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokegame.pokemon.move;

import pokegame.pokemon.Pokemon;
import pokegame.pokemon.status.BattleStatus;
import pokegame.pokemon.status.Status;

/**
 *
 * @author Rahul
 */
public class StatusMove extends Move{
    
    /**
     * 0 do nothing
     * 1 Raises critical-hit ratio
     * 2 Apply Status (Burn, freeze, poison, paralyze, sleep)
     * 3 Guillotine, Horn Drill, Fissure, Sheer Cold: (1-hit ko with accuracy)
     * 4 Apply Stat change (att, def etc,)
     * 5 Apply Battle Status (bound flinch etc,)
     * 6 Jump Kick, High Jump Kick, Double-Edge: if it misses deals damage to you
     * 7 Take Down, Submission, Brave Bird, Wood Hammer, Head Smash: deals recoil damage based off damage you deal (Join with 9 somehow)
     * 8 Apply Battle Status at the end of the move duration
     * 9 
     * 10 Sonic Boom, Dragon Rage: Inflicts a flat damage
     * 11 Disable: disables enemy last used move
     * 12 Mist: Need to either add battle status to prevent stat changes or something else
     * 13 Low Kick, Grass Knot, Heavy Slam: Deals damage based off of enemy weight
     * 14 Counter, Mirror Coat, Metal Burst: Deals damage based off damage dealt to you
     * 15 Seismic Toss, Psywave: Deals damage based off your level
     * 16 Absorb, Mega Drain, Leech Life, Giga Drain, Drain Punch: Heal based off your damage
     * 17 Rage: Deals more damage in succession, raises stat
     * 18 Mimic: Copy and use one of the enemy moves
     * 19 Recover, Soft-Boiled, Milk Drink, Morning Sun, Synthesis, Moonlight, Slack Off, Roost, Heal Order: Heal based off of max hp
     * 20 Minimize, Withdraw, Swagger, Flatter: Apply stat change and Battle Status
     * 21 Light Screen: 0.5x damage dealt to you from special attack moves
     * 22 Haze: Resets stat changes of both pokemon
     * 23 Bide: Stores damage for 2 turns then releases it
     * 24 Metronome: Use random move
     * 25 Mirror Move: Use last move by opponent
     * 26 
     * 27 Dream Eater: Does damage only with a Status, heals for damage
     * 28 Transform: WTF AM I SUPPOSED TO DO
     * 29 
     * 30 Self-Destruct, Explosion: Deals damage but then kills you
     * 31 Rest: Heals and applies a Status
     * 32 Conversion: Change Types to type of enemies first move
     * 33 Tri Attack: Chance to paralyze, freeze, or burn target
     * 34 Super Fang: Deals %HP damage based off enemy HP
     * 35 Struggle: Struggle lul
     * 36 Sketch: Permanently learns the move last used by the enemy
     * 37 Triple Kick: Each hit does increasingly more damage
     * 38 Thief: Takes the targets held item
     * 39 Nightmare, Snore: Applies a Battle Status if enemy is under a certain status
     * 40 
     * 41 Flail: Deals more damage the less hp the user has
     * 42 Conversion2: Change types to a type that resist/immune to last move enemy used
     * 43 Spite: Lowers the PP by a random number between 4PP
     * 44 Belly Drum: deals 50% hp to you, raises your attack to +6
     * 45 Spikes, Toxic Spikes, Stealth Rock: Deals damage to pokemon that swap in
     * 46 Destiny Bond: If the user dies that turn so does the enemy
     * 47
     * 48 Sandstorm, Rain Dance, Sunny Day, Hail: Applies weather condition
     * 49 Rollout, Ice Ball: Each hit does increasingly more damage
     * 50 False Swipe: Leaves enemy with 1 hp if it would kill
     * 51 Fury Cutter: Power will double every time its used
     * 52 Sleep Talk: Uses a random known move if user is asleep
     * 53 Heal Bell, Aromatherapy: Heals all pokemon in the party of everything
     * 54 Return: Deals damage based off friendship
     * 55 Present: Deals damage or 20% chance to heal, damage chose from 40 80 or 120
     * 56 Safeguard: Protects with something
     * 57 Pain Split: Splits the damage between both pokemon
     * 58 Magnitude: Deals damage based on a random magnitude power
     * 59 Baton Pass: Switches out user and passes temp stat increases and status conditions
     * 60 Pursuit: Attacks first if target is switched out
     * 61 Rapid Spin: Removes effects of binding moves and leech seed and entry hazards
     * 62 Hidden Power: Changes damage type depending on terrain
     * 63 Mirror Coat: Deals twice the damage taken that turn
     * 64 Psych Up: Copies all the targets stat stages
     * 65 Ancient Power, Acupressure: Chance to raise a random stat
     * 66 Future Sight, Doom Desire: Deals damage two turns later
     * 67 Beat Up: Deals damage differently
     * 68 Uproar: Cant fall asleep while uproar is active
     * 69 Spit Up, Swallow: Uses stockpile to do something
     * 70 Memento: lowers attack and sp attack of enemy but kills user
     * 71 Facade: Power doubles if user is under status conditions
     * 72 Focus Punch: If hit before move is used, move is not used
     * 73 Smelling Salts, Wake-Up Slap: Deals double damage against a status but removes it
     * 74 Nature Power: Use different moves depending on the terrain
     * 75 Charge: If next move is a certain type, deals more damage
     * 76 Trick: Swaps items with enemy pokemon
     * 77 Role Play: Replaces ability with targets ability
     * 78 Wish: At the end of next turn, heal pokemon for half of Max HP
     * 79 Assist: Uses a random move of the users pokemon in party
     * 80 Recycle: Allows user to regain held item
     * 81 Revenge, Avalanche: Taking damage power doubles
     * 82 Brick Break: removes light screen and reflect
     * 83 Yawn: Applies sleep to enemy at the end of next turn
     * 84 Knock Off: knocks held item off of enemy for the battle
     * 85 Endeavor: sets the targets HP to the current users HP
     * 86 Eruption: Power depends on % current HP
     * 87 Skill Swap: Swaps abilities of both pokemon
     * 88 Imprison: Enemy cant use the same moves that you have
     * 89 Refresh: Cures user of status condition
     * 90 Grudge: If the user dies from a move, that moves loses all pp
     * 91 Snatch: Snatch steals a beneficial move used by the enemy in the same turn
     * 92 Secret Power: Applies status based on where move is used
     * 93 Camouflage: Changes users type based on terrain
     * 94 Mud Sport: Weakens electric types for 5 turns
     * 95 Weather Ball: Move type changes according to weather
     * 96 Silver Wind, Ominous Wind: chance to raise all stats
     * 97 Covet: Takes enemy held item
     * 98 Volt Tackle: Deals recoil while inflicting a status
     * 99 Water Sport: Weakens fire types for 5 turns
     * 100 Gravity: puts gravity well on the field
     * 101 Gyro Ball, Electro Ball: Deals more damage the slower the user is than the enemy, type 1 for faster the user is than the enemy
     * 102 Healing Wish: Kills user but next poke is full hp with no status condition
     * 103 Brine: Deals double damage below health threshold
     * 104 Natural Gift: Move type and power depends on held berry
     * 105 Feint: Deals damage even if protected by a battle status
     * 106 Pluck: takes berry from enemy and consumes it
     * 107 Tailwind: four turns the users speed stat is doubled
     * 108 U-Turn: Deals damage then switches user out
     * 109 Payback: Double power if user moves 2nd, or enemy uses item
     * 110 Assurance: Damage doubles if target received damage already
     * 111 Fling: Power and effect depends on held item
     * 112 Psycho Shift: Transfers status condition to enemy
     * 113 Trump Card: Power depends on remaining PP of move after use
     * 114 Wring Out, Crush Grip: Damage is based off enemys % of max hp
     * 115 Power Trick: Switches the users attack and defense stat
     * 116 Gastro Acid: Suppresses the targets ability
     * 117 Lucky Chant: Prevents enemy from landing critical hits
     * 118 Me First: Only works if it moves first, copies the enemys move that turn if its damage and increases power by 50%
     * 119 Copycat: Uses the last move used in battle
     * 120 Power Swap: Switches the users att and sp att stat stages with the enemy
     * 121 Guard Swap: Switches the users def and sp def stat stages with the enemy
     * 122 Punishment: deals damage based off he positive stat changes of enemy
     * 123 Last Resort: Only works if all other moves are used atleast once
     * 124 Worry Seed: Changes the targets ability to Insomnia
     * 125 Sucker Punch: Only works if target uses non status move and hasnt moved yet
     * 126 Heart Swap: swaps users stat stages with enemy
     * 127 Flare Blitz: Deals recoil damage and applies status
     * 128 Switcheroo: Swaps held items with the enemy
     * 129 Applies Status and Battle Status
     * 130 Trick Room: Slower pokemon attack first
     * 131 Judgement: Type changes based on plate held
     * 132 Bug Bite: Eats the enemies berry when it hits if they are holding one
     * 133 Lunar Dance: user faints, restores all hp,pp,status of pokemon sent in its place
     * 134 Guard Split: Averages the users Defense and Sp Defense with the enemy
     * 135 Power Split: Averages the users Attack and Sp Attack with the enemy
     * 136 Wonder Room: Swaps the Defense and Special Defense of all pokemon
     * 137 Psyshock: Users Sp Attack vs Opponents Defense
     * 138 Venochock: Doubles damage against a status
     * 139 Autotomize: Raises stat and lowers weight
     * 140 Magic Room: All held items by pokemon are suppressed
     * 141 Smack Down: flying pokemon become grounded
     * 142 Storm Throw: Always crits
     * 143 Quiver Dance: Raises sp att, sp def, speed by 1 stage
     * 144 Synchronoise: Damages enemies that are the same type(s) as the user
     * 145 Soak: Changes users type to pure water
     * 146 Coil: Raisers attack, defense, accuracy by 1 stage
     */
    
    /**
     * Battle:
     * Check MoveID 5 for Payday
     */
    
    private int type;
    private float statusChance;
    private Status statusInflict;
    private BattleStatus statusInflict2;
    private int statChange;
    private int statChangeAmount;
    
    public StatusMove(int id) {
        super(id);
    }
    
    public StatusMove(BaseMove move) {
        super(move);
    }
    
    public void applyStatus(Pokemon p){
        switch(type){

        }
    }
    
    @Override
    public float getAccuracy(Pokemon a, Pokemon e){
        if (a == null || e == null) return super.getAccuracy(e, a);
        if (type == 4) return (a.getLevel() - e.getLevel()+30)/(float)100.0;
        return super.getAccuracy(e, a);
    }
    
    public void takeDamage(Pokemon p, int damage){
        switch(id){
            case 6: p.damage((int)(Math.floor(p.getMaxHp()/2)));
                break;
            case 7: p.damage(damage/4);
                break;
            case 9: p.damage(damage/3);
                break;
        }
    }
}
