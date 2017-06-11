package com.github.kkysen.supersmashbros.actions;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Hitbox;
import com.github.kkysen.supersmashbros.core.State;

public class UpTiltAttack extends GroundAttack {
    
    public UpTiltAttack(final State state, final float startup, final float duration,
            final float cooldown, final float damage, final float knockback) {
        super(state, KeyBinding.UP_TILT, new State[] {}, startup, duration, cooldown, damage, 88,
                knockback);
    }
    
    @Override
    protected void attack(final State state, final boolean facingRight) {
        super.attack(state, facingRight);
        final Hitbox hitbox = state.newHitbox(this, 20f, 40f);
        hitbox.angle = facingRight ? angle : PI - angle;
        hitbox.position.y += 20f;
        hitbox.position.x += facingRight ? 20f : -5f; // TODO is this right?
        state.addHitbox(hitbox);
    }
    
}
