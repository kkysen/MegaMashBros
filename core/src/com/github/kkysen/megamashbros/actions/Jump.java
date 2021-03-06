package com.github.kkysen.megamashbros.actions;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.megamashbros.core.Player;
import com.github.kkysen.megamashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Jump extends Move {
    
    //private int numMidairJumps = 1;
    public boolean jumpPressed = false;
    
    public Jump(final State state, final float duration, final float cooldown, final float speed) {
        super(state, KeyBinding.JUMP, new State[] {}, 0, duration, cooldown, speed);
    }
    
    @Override
    protected boolean isContinuous() {
        return false;
    }
    
    @Override
    protected boolean dontExecute(final Player player) {
        return jumpPressed;
    }
    
    @Override
    protected void move(final Player player) {
        //if (alreadyUsed) return;
        
        final boolean isOnPlatform = player.wasOnPlatform;
        if (isOnPlatform || player.numMidairJumps++ <= 1 && !jumpPressed) {
            System.out.println("someone jumped");
            player.velocity.y = maxSpeed;
            if (isOnPlatform) {
                player.numMidairJumps = 1;
            }
            jumpPressed = true;
        } /*
          else if (player.stunTime > 0|| numMidairJumps++ <= 1) {
          
          }*/
    }
    
    @Override
    public void reset() {
        super.reset();
        jumpPressed = false;
    }
}
