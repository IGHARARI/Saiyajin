package sts.saiyajin.powers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.ui.PowerPaths;

public class GreatApePower extends AbstractPower {
	public static final String POWER_ID = PowerNames.GREAT_APE;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public static final int ENERGY_GAIN_PER_STACK = 2;

	final Logger logger = LogManager.getLogger(GreatApePower.class);
	
	public GreatApePower(AbstractCreature owner, final int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.img = new Texture(PowerPaths.GREAT_APE);
	}
  
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
  
	@Override
	public void atStartOfTurnPostDraw() {
		AbstractDungeon.player.gainEnergy(this.amount * ENERGY_GAIN_PER_STACK);
	}
}