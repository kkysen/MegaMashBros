package com.github.kkysen.supersmashbros.actions;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.State;

public abstract class AirAttack extends Attack {
    
    public AirAttack(final State state, final KeyBinding keyBinding,
            final State[] impossiblePreStates, final float startup, final float duration,
            final float cooldown, final float damage, final float angle, final float knockback) {
        super(state, keyBinding, impossiblePreStates, startup, duration, cooldown, damage, angle,
                knockback);
    }
    
    @Override
    protected boolean dontExecute(final Player player) {
        if (player.velocity.y == 0) {
            System.out.println("skipping air");
        }
        return super.dontExecute(player) || player.velocity.y == 0;
    }
    
}
