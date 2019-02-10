package sts.saiyajin.ui.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class GhostAttackBallEffect extends AbstractGameEffect {
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private int count;
    private float timer;
    
    public GhostAttackBallEffect(final float sX, final float sY, final float tX, final float tY, final int count) {
        this.count = 0;
        this.timer = 0.0f;
        this.sX = sX - 20.0f * Settings.scale;
        this.sY = sY + 80.0f * Settings.scale;
        this.tX = tX;
        this.tY = tY;
        this.count = count;
    }
    
    @Override
    public void update() {
        this.timer -= Gdx.graphics.getDeltaTime();
        if (this.timer < 0.0f) {
            this.timer += MathUtils.random(0.05f, 0.15f);
            AbstractDungeon.effectsQueue.add(new KiBallParticleEffect(this.sX, this.sY, this.tX, this.tY, Color.WHITE.cpy(), Color.TAN.cpy(), 1.5f));
            --this.count;
            if (this.count == 0) {
                this.isDone = true;
            }
        }
    }
    
    @Override
    public void render(final SpriteBatch sb) {
    }
    
    @Override
    public void dispose() {
    }
}
