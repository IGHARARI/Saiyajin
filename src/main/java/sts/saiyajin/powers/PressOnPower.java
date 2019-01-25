package sts.saiyajin.powers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.cards.special.KiBurn;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.PowerPaths;
import sts.saiyajin.utils.PowerNames;

public class PressOnPower extends AbstractPower {

	public static final String POWER_ID = PowerNames.PRESS_ON;
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	final Logger logger = LogManager.getLogger(PressOnPower.class);
	
	public PressOnPower(AbstractCreature owner) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = -1;
		this.type = AbstractPower.PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.PRESS_ON_B), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.PRESS_ON), 0, 0, 48, 48);
        this.description = DESCRIPTIONS[0];
		this.canGoNegative = false;
	}
	
	@Override
	public void atEndOfTurn(boolean isPlayer) {
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
	}
	
	@Override
	public void onUseCard(AbstractCard card, UseCardAction action) {
		super.onUseCard(card, action);
		if (card instanceof SaiyanCard) {
			SaiyanCard saiyanCard = (SaiyanCard) card;
			if (saiyanCard.kiRequired > 0) {
				this.flash();
				AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new KiBurn(), 1));
			}
		}
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
}