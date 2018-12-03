package sts.saiyajin.powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.ui.PowerPaths;

public class CantRevivePower extends AbstractPower {

	public static final String POWER_ID = PowerNames.CANT_REVIVE;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private boolean justApplied;
	

	public CantRevivePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = 1;
		this.type = AbstractPower.PowerType.BUFF;
		this.img = new Texture(PowerPaths.CANT_REVIVE);
		this.canGoNegative = false;
		this.justApplied = true;
	}
	
	@Override
	public int onAttacked(DamageInfo info, int damageAmount) {
		if (justApplied) {
			return 0;
		}
		return super.onAttacked(null, damageAmount);
	}
	
	@Override
	public void atEndOfRound() {
		if(justApplied){
			justApplied = false;
		}
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
}