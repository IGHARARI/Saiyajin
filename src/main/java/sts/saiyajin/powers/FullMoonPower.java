package sts.saiyajin.powers;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.ui.PowerPaths;

public class FullMoonPower extends AbstractPower {

	public static final String POWER_ID = PowerNames.FULL_MOON;
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public FullMoonPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = 1;
		this.type = AbstractPower.PowerType.BUFF;
		this.img = new Texture(PowerPaths.FULL_MOON);
		this.canGoNegative = false;
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
}