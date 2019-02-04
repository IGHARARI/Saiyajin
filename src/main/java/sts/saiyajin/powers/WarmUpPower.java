package sts.saiyajin.powers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.ui.PowerPaths;
import sts.saiyajin.utils.PowerNames;

public class WarmUpPower extends AbstractPower {

	public static final String POWER_ID = PowerNames.WARM_UP;
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	final Logger logger = LogManager.getLogger(WarmUpPower.class);
	private int powersPlayedThisTurn = 0;
	
	public WarmUpPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PowerPaths.WARM_UP_B), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PowerPaths.WARM_UP), 0, 0, 48, 48);
        this.description = DESCRIPTIONS[0];
		this.canGoNegative = false;
	}
	
	@Override
	public void updateDescription() {
		if (this.amount > 0) {
			this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
		} else {
			this.description = DESCRIPTIONS[0];
		}
	}

	@Override
	public void atStartOfTurn() {
		powersPlayedThisTurn = 0;
	}
	
	@Override
	public void onAfterCardPlayed(AbstractCard usedCard) {
		if (powersPlayedThisTurn < amount && usedCard.type == CardType.POWER) {
			AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
			powersPlayedThisTurn++;
		}
	}
	
}