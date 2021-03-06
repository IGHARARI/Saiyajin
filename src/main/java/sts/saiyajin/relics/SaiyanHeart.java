package sts.saiyajin.relics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import sts.saiyajin.core.SaiyaMod;
import sts.saiyajin.ui.RelicPaths;
import sts.saiyajin.utils.RelicNames;

public class SaiyanHeart extends CustomRelic {

	private static final String IMG = RelicPaths.SAIYAN_HEART;
	private static final String IMG_OTL = RelicPaths.SAIYAN_HEART_OUTLINE;

	private static final int MAX_TURNS_HEART = 5;
	private static final int TEMP_HP_PER_DEBUFF = 4;

	private ArrayList<String> currentDebuffs = new ArrayList<String>();
	private boolean isBattling = false;
	private final static Set<String> invalidDebuffs = new HashSet<String>(Arrays.asList(StrengthPower.POWER_ID, DexterityPower.POWER_ID, LoseStrengthPower.POWER_ID, LoseDexterityPower.POWER_ID));

	
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

//	@Override
//	public void onEnterRoom(AbstractRoom room) {
//		if (room instanceof MonsterRoomElite || room instanceof MonsterRoomBoss) {
//			this.flash();
//		}
//	}
//    
//    @Override
//    public void onVictory() {
//    	AbstractRoom room = AbstractDungeon.getCurrRoom();
//        if (room instanceof MonsterRoomElite || room instanceof MonsterRoomBoss) {
//        	this.flash();
//            AbstractDungeon.player.increaseMaxHp(5, true);
//        }
//    }
	
	@Override
	public void atBattleStart() {
		currentDebuffs.clear();
		isBattling = true;
		this.counter = 0;
		this.beginLongPulse();
	}
	
	public void battleEnd(){
		if (counter <= MAX_TURNS_HEART) {
			int remainingDebuffs = 0;
			for(AbstractPower power : AbstractDungeon.player.powers){
				if (power.type.equals(PowerType.DEBUFF) && !invalidDebuffs.contains(power.ID)){
					remainingDebuffs++;
				}
			}
			if (remainingDebuffs > 0) AbstractDungeon.player.increaseMaxHp(remainingDebuffs, true);
		}
		
		isBattling = false;
		this.counter = -1;
		this.stopPulse();
	}
	
	@Override
	public void atTurnStart() {
		super.atTurnStart();
		++this.counter;
		if (counter > MAX_TURNS_HEART) this.stopPulse();
	}
	
	public void powersWereModified() {
		if (!isBattling || GameActionManager.turn > MAX_TURNS_HEART) return;
		int debuffsPurged = 0;
		for (String oldDebuff : currentDebuffs){
			if (!AbstractDungeon.player.hasPower(oldDebuff)){
				debuffsPurged++;
			}
		}
		currentDebuffs.clear();
		for(AbstractPower power : AbstractDungeon.player.powers){
			if (power.type.equals(PowerType.DEBUFF) && !invalidDebuffs.contains(power.ID)){
				currentDebuffs.add(power.ID);
			}
		}
		if (debuffsPurged > 0){
			this.flash();
			AbstractDungeon.player.increaseMaxHp(debuffsPurged, true);
			AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, debuffsPurged*TEMP_HP_PER_DEBUFF));
		}
	}

}