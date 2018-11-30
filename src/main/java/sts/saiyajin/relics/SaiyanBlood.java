package sts.saiyajin.relics;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import sts.saiyajin.cards.utils.RelicNames;
import sts.saiyajin.core.SaiyaMod;
import sts.saiyajin.core.Saiyajin;
import sts.saiyajin.powers.Ki;
import sts.saiyajin.ui.RelicPaths;

public class SaiyanBlood extends CustomRelic {

	private static final String IMG = RelicPaths.SAIYAN_BLOOD;
	private static final String IMG_OTL = RelicPaths.SAIYAN_BLOOD_OUTLINE;

	private ArrayList<String> currentDebuffs = new ArrayList<String>();
	private boolean battleStarted = false;

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
		Ki kiPower = new Ki(kakarot, kakarot.getMaxKi());
		ApplyPowerAction powerAction = new ApplyPowerAction(kakarot, kakarot, kiPower, kakarot.getMaxKi());
		AbstractDungeon.actionManager.addToTop(powerAction);
		currentDebuffs.clear();
		battleStarted = true;
	}
	
	public void battleEnd(){
		battleStarted = false;
	}
	
	public void powersWereModified() {
		if (!battleStarted) return;
		int debuffsPurged = 0;
		for (String oldDebuff : currentDebuffs){
			if (!AbstractDungeon.player.hasPower(oldDebuff)){
				debuffsPurged++;
			}
		}
		currentDebuffs = new ArrayList<String>();
		for(AbstractPower power : AbstractDungeon.player.powers){
			if (power.type.equals(PowerType.DEBUFF)){
				currentDebuffs.add(power.ID);
			}
		}
		if (debuffsPurged > 0){
			AbstractDungeon.player.increaseMaxHp(debuffsPurged, true);
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, debuffsPurged), debuffsPurged));
		}
	}

//	@Override
//	public boolean onReceivePower(AbstractPower power, AbstractCreature source) {
//		if (power.type == PowerType.DEBUFF && AbstractDungeon.player.hasPower(power.ID)){
//			AbstractPower preExistingPower = AbstractDungeon.player.getPower(power.ID);
//			if (preExistingPower.amount + power.amount == 0){
//				AbstractDungeon.player.increaseMaxHp(1, true);
//				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
//			}
//		}
//		return true;
//	}
	
}