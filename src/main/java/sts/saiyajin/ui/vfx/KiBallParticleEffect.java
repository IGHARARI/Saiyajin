package sts.saiyajin.ui.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AdditiveSlashImpactEffect;

public class KiBallParticleEffect extends AbstractGameEffect {
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private float x;
    private float y;
    private float vY;
    private float vX;
    private TextureAtlas.AtlasRegion img;
    private boolean activated;
    private Color secondaryColor;
    private float drawScale;
    
    public KiBallParticleEffect(final float sX, final float sY, final float tX, final float tY, final Color ballColor) {
    	this(sX, sY, tX, tY, ballColor, Color.BLACK.cpy());
    }
    
    public KiBallParticleEffect(final float sX, final float sY, final float tX, final float tY, final Color ballColor, final Color secondaryColor) {
    	this(sX, sY, tX, tY, ballColor, secondaryColor, 1.0f);
    }
    public KiBallParticleEffect(final float sX, final float sY, final float tX, final float tY, final Color ballColor, final Color secondaryColor, float drawScale) {
        this.activated = false;
        this.img = ImageMaster.GLOW_SPARK_2;
        this.sX = sX + MathUtils.random(-90.0f, 90.0f) * Settings.scale;
        this.sY = sY + MathUtils.random(-90.0f, 90.0f) * Settings.scale;
        this.tX = tX + MathUtils.random(-50.0f, 50.0f) * Settings.scale;
        this.tY = tY + MathUtils.random(-50.0f, 50.0f) * Settings.scale;
        this.vX = this.sX + MathUtils.random(-200.0f, 200.0f) * Settings.scale;
        this.vY = this.sY + MathUtils.random(-200.0f, 200.0f) * Settings.scale;
        this.x = this.sX;
        this.y = this.sY;
        this.scale = 0.01f;
        this.startingDuration = 0.8f;
        this.duration = this.startingDuration;
        this.renderBehind = MathUtils.randomBoolean(0.2f);
        this.color = ballColor;
        this.secondaryColor = secondaryColor;
        this.drawScale = drawScale;
    }
    
    @Override
    public void update() {
        if (this.duration > this.startingDuration / 2.0f) {
            this.scale = Interpolation.pow3In.apply(2.5f, this.startingDuration / 2.0f, (this.duration - this.startingDuration / 2.0f) / (this.startingDuration / 2.0f)) * Settings.scale;
            this.x = Interpolation.swingIn.apply(this.sX, this.vX, (this.duration - this.startingDuration / 2.0f) / (this.startingDuration / 2.0f));
            this.y = Interpolation.swingIn.apply(this.sY, this.vY, (this.duration - this.startingDuration / 2.0f) / (this.startingDuration / 2.0f));
        }
        else {
            this.scale = Interpolation.pow3Out.apply(2.0f, 2.5f, this.duration / (this.startingDuration / 2.0f)) * Settings.scale;
            this.x = Interpolation.swingOut.apply(this.tX, this.vX, this.duration / (this.startingDuration / 2.0f));
            this.y = Interpolation.swingOut.apply(this.tY, this.vY, this.duration / (this.startingDuration / 2.0f));
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < this.startingDuration / 2.0f && !this.activated) {
            this.activated = true;
            this.sX = this.x;
            this.sY = this.y;
        }
        if (this.duration < 0.0f) {
            AbstractDungeon.effectsQueue.add(new AdditiveSlashImpactEffect(this.tX, this.tY, this.color.cpy()));
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, MathUtils.randomBoolean());
            this.isDone = true;
        }
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        sb.setColor(secondaryColor);
        sb.draw(this.img, this.x, this.y, this.img.packedWidth / 2.0f, this.img.packedHeight / 2.0f, this.img.packedWidth, this.img.packedHeight, this.scale * (this.drawScale+1f), this.scale * (this.drawScale+1f), this.rotation);
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, this.img.packedWidth / 2.0f, this.img.packedHeight / 2.0f, this.img.packedWidth, this.img.packedHeight, this.scale*this.drawScale, this.scale*this.drawScale, this.rotation);
    }
    
    @Override
    public void dispose() {
    }
}
