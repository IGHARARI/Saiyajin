package sts.saiyajin.powers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.utils.PowerNames;

public class RetainOncePower extends AbstractPower {
	public static final String POWER_ID = PowerNames.RETAIN_ONCE;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	final Logger logger = LogManager.getLogger(RetainOncePower.class);
	
	public RetainOncePower(AbstractCreature owner) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = 1;
		this.loadRegion("retain");
        updateDescription();
	}
  
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
	
	@Override
	public void atEndOfTurn(boolean isPlayer){
		if (isPlayer && (!AbstractDungeon.player.hand.isEmpty())) {
			AbstractDungeon.actionManager.addToTop(new RetainCardsAction(this.owner, this.amount));
		}
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
	}
  
}