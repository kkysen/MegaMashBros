package com.github.kkysen.supersmashbros.players;

import static com.github.kkysen.supersmashbros.app.Game.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.github.kkysen.libgdx.util.Textures;
import com.github.kkysen.libgdx.util.keys.Controller;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.libgdx.util.keys.User;
import com.github.kkysen.supersmashbros.actions.Executable;
import com.github.kkysen.supersmashbros.actions.Jump;
import com.github.kkysen.supersmashbros.actions.Message;
import com.github.kkysen.supersmashbros.actions.MoveLeft;
import com.github.kkysen.supersmashbros.actions.MoveRight;
import com.github.kkysen.supersmashbros.actions.RangeAttack;
import com.github.kkysen.supersmashbros.actions.Stop;
import com.github.kkysen.supersmashbros.ai.FrozenAI;
import com.github.kkysen.supersmashbros.ai.RandomAI;
import com.github.kkysen.supersmashbros.ai.SmartAI;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Mario extends Player {
    
    private static final State idleState = new State("Mario idle state",
            new Animation<>(
                    0.2f,
                    Textures.getFrames(
                            new Texture(asset("sprites_transparent.png")),
                            6,
                            16, 24, 27, 38),
                    PlayMode.LOOP_PINGPONG));
    
    private static final State moveRightState = new State("Mario move right state",
            new Animation<>(
                    0.1f,
                    Textures.getFrames(
                            new Texture(asset("sprites_transparent.png")),
                            8,
                            10, 147, 32, 38),
                    PlayMode.LOOP));
    
    private static final State moveLeftState = moveRightState; // FIXME
    
    private static final State jumpState = new State("Mario jump state",
            new Animation<>(
                    0.5f,
                    Textures.getFrames(
                            new Texture(asset("sprites_transparent.png")),
                            17, 84,
                            new int[][] {
                                {28, 42},
                                {31, 42},
                                {33, 44}
                            })));
    
    public static Mario userControlled() {
        return new Mario(User.get());
    }
    
    public static Mario randomlyControlled() {
        return new Mario(new RandomAI());
    }
    
    public static Mario frozen() {
        return new Mario(new FrozenAI());
    }
    
    public static Mario smart() {
        return new Mario(new SmartAI());
    }
    
    public Mario(final Controller controller) {
        super("Mario", controller, idleState, new Stop(idleState), 1, new Executable[] {
            new Stop(idleState),
            new MoveLeft(moveLeftState, 0f, 200f),
            new MoveRight(moveRightState, 0f, 200f),
            new Jump(jumpState, 1f, 0.1f, 300f),
            new RangeAttack(idleState, 0, 10f, 1f, 5f, 5f),
            new Message(KeyBinding.P, player -> player.position),
        });
    }
    
}
