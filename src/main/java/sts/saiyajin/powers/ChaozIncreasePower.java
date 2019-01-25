package sts.saiyajin.powers;


import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.actions.ChaozAction;
import sts.saiyajin.utils.PowerNames;

public class ChaozIncreasePower extends AbstractPower implements InvisiblePower {

	public static final String POWER_ID = PowerNames.COMBO;
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	final Logger logger = LogManager.getLogger(ChaozIncreasePower.class);
	 final private int incAmount;
	 final private UUID targetUUID;
	 
	public ChaozIncreasePower(final AbstractCreature owner, final int turns, final int incAmount, final UUID targetUUID) {
		this.name = NAME;
		this.owner = owner;
		this.amount = turns;
		this.ID = POWER_ID;
		this.type = AbstractPower.PowerType.BUFF;
        this.description = DESCRIPTIONS[0];
		this.canGoNegative = false;
		this.incAmount = incAmount;
		this.targetUUID = targetUUID;
	}
	
	
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
            if (this.amount == 1) {
                AbstractDungeon.actionManager.addToBottom(new ChaozAction(incAmount, targetUUID));
            }
        }
    }
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
}