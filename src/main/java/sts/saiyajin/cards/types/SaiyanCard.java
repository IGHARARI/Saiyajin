package sts.saiyajin.cards.types;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.ExceptionHandler;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import sts.saiyajin.cards.utils.DescriptionStrings;
import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.cards.utils.PowersHelper;

public class SaiyanCard extends CustomCard {
	
	public static final Logger logger = LogManager.getLogger(SaiyanCard.class);

	public SaiyanCard(String id, String name, String img, int cost, String rawDescription, CardType type,
			CardColor color, CardRarity rarity, CardTarget target) {
		super(id, name, img, cost, rawDescription, type, color, rarity, target);
	}
	
	protected int kiRequired = 0;
	
	@Override
	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		boolean canUse = super.canUse(p, m);
		return canUseKiCard(this, canUse, kiRequired);
	}
	
	@SpireOverride
	protected void renderEnergy(final SpriteBatch sb) {
		SpireSuper.call(sb);
		if (kiRequired > 0) {
			final float drawX = this.current_x - 290.0f;
			final float drawY = this.current_y - 280.0f;
			sb.setColor(Color.valueOf("fce9b5"));//CardProperties.SAIYAN_CARD_RENDER_COLOR);
			try {
				sb.draw(ImageMaster.CARD_COLORLESS_ORB, drawX, drawY, 290.0f, 280.0f, 512.0f, 512.0f, 0.8f * this.drawScale * Settings.scale, 0.8f * this.drawScale * Settings.scale, this.angle, 0, 0, 512, 512, false, false);
			}
			catch (Exception e) {
				ExceptionHandler.handleException(e, logger);
			}
			Color costColor = Color.valueOf("f0f291");
//		Color costColor = new Color();
//        Color.rgb888ToColor(costColor, Color.rgb888(250, 250, 195));//Color.WHITE.cpy();
			if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(this) && !canUseKiCard(this, true, this.kiRequired)) {
				costColor = Color.FIREBRICK.cpy();
			}
			costColor.a = this.transparency;
			final String text = String.valueOf(this.kiRequired);
			FontHelper.cardEnergyFont_L.getData().setScale(this.drawScale * 0.72f);
			final BitmapFont font = FontHelper.cardEnergyFont_L;
			if ((this.type != CardType.STATUS || this.cardID.equals("Slimed")) && (this.color != CardColor.CURSE || this.cardID.equals("Pride"))) {
				FontHelper.renderRotatedText(sb, font, text, this.current_x, this.current_y, -132.0f * this.drawScale * Settings.scale, 136.0f * this.drawScale * Settings.scale, this.angle, false, costColor);
			}
		}
	}
	
	public static boolean canUseKiCard(CustomCard c, boolean superCanUse, int kiRequired) {
		if (kiRequired == 0) return superCanUse;
		if (!superCanUse) return false;
		if (AbstractDungeon.player.hasPower(PowerNames.KI)){
			int kiPower = PowersHelper.getPlayerPowerAmount(PowerNames.KI);
			if (kiPower >= kiRequired) return true;
		}
		c.cantUseMessage = DescriptionStrings.KI_CARD_CANT_USE;
		return false;
	}

	@Override
	public void upgrade() {
	}

	@Override
	public void use(AbstractPlayer p0, AbstractMonster p1) {
	}

}
