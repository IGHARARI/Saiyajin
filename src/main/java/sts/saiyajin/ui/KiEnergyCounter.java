package sts.saiyajin.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.ClickableUIElement;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.utils.PowersHelper;

public class KiEnergyCounter extends ClickableUIElement {
	    private static float x = 0f * Settings.scale;
	    private static float y = 148f * Settings.scale;
	    private static float textX = 64f * Settings.scale;
	    private static float textY = 190f * Settings.scale;
	    private static float hb_w = 128f;
	    private static float hb_h = 128f;
	    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("KiCounter");
	    private int kiAmount;

	    public KiEnergyCounter(Texture image) {
	        super(image, x, y, hb_w, hb_h);
	    }

	    @Override
	    protected void onHover() {
	        TipHelper.renderGenericTip(50.0f * Settings.scale, 380.0f * Settings.scale, uiStrings.TEXT[0], uiStrings.TEXT[1]);
	    }

	    @Override
	    protected void onUnhover() {

	    }

	    @Override
	    protected void onClick() {

	    }

	    @Override
	    public void update() {
	        if (CardCrawlGame.dungeon != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
	            super.update();
	            kiAmount = PowersHelper.getPlayerPowerAmount(KiPower.POWER_ID);
	        }
	    }

	    @Override
	    public void render(SpriteBatch sb) {
	        super.render(sb);
	        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontBlue, Integer.toString(this.kiAmount), textX, textY, Color.WHITE);
	    }
}
