package sts.saiyajin.ui.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;
import com.megacrit.cardcrawl.vfx.GhostlyWeakFireEffect;
import com.megacrit.cardcrawl.vfx.combat.GhostIgniteEffect;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;


public class FireEffect extends AbstractGameEffect {
    private static final float FIREBALL_INTERVAL = 0.009f;
    private float x;
    private float y;
    private float startX;
    private float startY;
    private float targetX;
    private float targetY;
    private float vfxTimer;
    
    public FireEffect(final float startX, final float startY, final float targetX, final float targetY, float duration) {
        this.vfxTimer = 0.0f;
        this.startingDuration = duration;
        this.duration = duration;
        this.startX = startX;
        this.startY = startY;
        this.targetX = targetX + MathUtils.random(-20.0f, 20.0f) * Settings.scale;
        this.targetY = targetY + MathUtils.random(-20.0f, 20.0f) * Settings.scale;
        this.x = startX;
        this.y = startY;
    }
    
    @Override
    public void update() {
        this.x = Interpolation.fade.apply(this.targetX, this.startX, this.duration / this.startingDuration);
        this.y = Interpolation.fade.apply(this.targetY, this.startY, this.duration / this.startingDuration);
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0f) {
            this.vfxTimer = FIREBALL_INTERVAL;
            AbstractDungeon.effectsQueue.add(new LightFlareParticleEffect(this.x, this.y, Color.ORANGE.cpy()));
            AbstractDungeon.effectsQueue.add(new FireBurstParticleEffect(this.x, this.y));
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0f) {
            this.isDone = true;
            AbstractDungeon.effectsQueue.add(new GhostIgniteEffect(this.x, this.y));
            AbstractDungeon.effectsQueue.add(new GhostlyWeakFireEffect(this.x, this.y));
        }
    }
    
    @Override
    public void render(final SpriteBatch sb) {
    }
    
    @Override
    public void dispose() {
    }
}