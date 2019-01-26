package sts.saiyajin.relics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import sts.saiyajin.core.SaiyaMod;
import sts.saiyajin.core.Saiyajin;
import sts.saiyajin.powers.KiRegenPower;
import sts.saiyajin.ui.RelicPaths;
import sts.saiyajin.utils.RelicNames;

public class SaiyanSoul extends CustomRelic {

	private static final String IMG = RelicPaths.SAIYAN_SOUL;
	private static final String IMG_OTL = RelicPaths.SAIYAN_SOUL_OUTLINE;

	private static final int KI_REGEN = 5;

	final Logger logger = LogManager.getLogger(SaiyaMod.class);
	
	public SaiyanSoul() {
		super(
			RelicNames.SAIYAN_SOUL,
			ImageMaster.loadImage(IMG),
			ImageMaster.loadImage(IMG_OTL),
			RelicTier.STARTER,
			LandingSound.MAGICAL
		);
		this.description = DESCRIPTIONS[0];
	}

	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	public AbstractRelic makeCopy() {
		return new SaiyanSoul();
	}

	@Override
	public void atBattleStartPreDraw() {
		this.flash();
		Saiyajin kakarot = (Saiyajin) AbstractDungeon.player;
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(kakarot, kakarot, new KiRegenPower(kakarot, KI_REGEN), KI_REGEN));
	}
	
}