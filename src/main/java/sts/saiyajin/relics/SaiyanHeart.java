package sts.saiyajin.relics;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import sts.saiyajin.core.SaiyaMod;
import sts.saiyajin.ui.RelicPaths;
import sts.saiyajin.utils.RelicNames;

public class SaiyanHeart extends CustomRelic {

	private static final String IMG = RelicPaths.SAIYAN_HEART;
	private static final String IMG_OTL = RelicPaths.SAIYAN_HEART_OUTLINE;

	private ArrayList<String> currentDebuffs = new ArrayList<String>();
	private boolean isBattling = false;

	final Logger logger = LogManager.getLogger(SaiyaMod.class);
	
	public SaiyanHeart() {
		super(
			RelicNames.SAIYAN_HEART,
			ImageMaster.loadImage(IMG),
			ImageMaster.loadImage(IMG_OTL),
			RelicTier.STARTER,
			LandingSound.MAGICAL
		);
		this.description = DESCRIPTIONS[0];
		this.counter = -2;
	}

	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	public AbstractRelic makeCopy() {
		return new SaiyanHeart();
	}

	@Override
	public void atBattleStart() {
		currentDebuffs.clear();
		isBattling = true;
		this.counter = 0;
		this.beginLongPulse();
	}
	
	public void battleEnd(){
		isBattling = false;
		this.counter = -1;
		this.stopPulse();
	}
	
	@Override
	public void atTurnStart() {
		super.atTurnStart();
		++this.counter;
		if (counter == 8) this.stopPulse();
	}
	
	public void powersWereModified() {
		if (!isBattling || GameActionManager.turn > 7) return;
		int debuffsPurged = 0;
		for (String oldDebuff : currentDebuffs){
			if (!AbstractDungeon.player.hasPower(oldDebuff)){
				debuffsPurged++;
			}
		}
		currentDebuffs.clear();
		for(AbstractPower power : AbstractDungeon.player.powers){
			if (power.type.equals(PowerType.DEBUFF) && !power.ID.equals(StrengthPower.POWER_ID) && !power.ID.equals(DexterityPower.POWER_ID)){
				currentDebuffs.add(power.ID);
			}
		}
		if (debuffsPurged > 0){
			this.flash();
			AbstractDungeon.player.increaseMaxHp(debuffsPurged, true);
			AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, debuffsPurged*3));
		}
	}

}