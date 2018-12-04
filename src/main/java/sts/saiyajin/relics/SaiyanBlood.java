package sts.saiyajin.relics;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import sts.saiyajin.cards.utils.RelicNames;
import sts.saiyajin.core.SaiyaMod;
import sts.saiyajin.core.Saiyajin;
import sts.saiyajin.powers.KiRegenPower;
import sts.saiyajin.ui.RelicPaths;

public class SaiyanBlood extends CustomRelic {

	private static final String IMG = RelicPaths.SAIYAN_BLOOD;
	private static final String IMG_OTL = RelicPaths.SAIYAN_BLOOD_OUTLINE;

	private ArrayList<String> currentDebuffs = new ArrayList<String>();
	private boolean isBattling = false;
	private static final int KI_REGEN = 3;

	final Logger logger = LogManager.getLogger(SaiyaMod.class);
	
	public SaiyanBlood() {
		super(
			RelicNames.SAIYAN_BLOOD,
			ImageMaster.loadImage(IMG),
			ImageMaster.loadImage(IMG_OTL),
			RelicTier.STARTER,
			LandingSound.MAGICAL
		);
	}

	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	public AbstractRelic makeCopy() {
		return new SaiyanBlood();
	}

	@Override
	public void atBattleStartPreDraw() {
		flash();
		Saiyajin kakarot = (Saiyajin) AbstractDungeon.player;
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(kakarot, kakarot, new KiRegenPower(kakarot, KI_REGEN), KI_REGEN));
		currentDebuffs.clear();
		isBattling = true;
	}
	
	public void battleEnd(){
		isBattling = false;
	}
	
	public void powersWereModified() {
		if (!isBattling) return;
		int debuffsPurged = 0;
		for (String oldDebuff : currentDebuffs){
			if (!AbstractDungeon.player.hasPower(oldDebuff)){
				logger.info("debuff purged: " + oldDebuff);
				debuffsPurged++;
			}
		}
		currentDebuffs = new ArrayList<String>();
		for(AbstractPower power : AbstractDungeon.player.powers){
			if (power.type.equals(PowerType.DEBUFF) && !power.ID.equals(StrengthPower.POWER_ID) && !power.ID.equals(DexterityPower.POWER_ID)){
				logger.info("current debuffs: " + power.ID);
				currentDebuffs.add(power.ID);
			}
		}
		if (debuffsPurged > 0){
			AbstractDungeon.player.increaseMaxHp(debuffsPurged, true);
//			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, debuffsPurged), debuffsPurged));
			AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, debuffsPurged*3));
		}
	}

}