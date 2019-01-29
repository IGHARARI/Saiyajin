package sts.saiyajin.powers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.cards.types.ComboFinisher;
import sts.saiyajin.ui.PowerPaths;
import sts.saiyajin.utils.PowerNames;
import sts.saiyajin.utils.PowersHelper;

public class ComboPower extends AbstractPower {

	public static final String POWER_ID = PowerNames.COMBO;
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	final Logger logger = LogManager.getLogger(ComboPower.class);
	
	public ComboPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.COMBO_B), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.COMBO), 0, 0, 48, 48);
        this.description = DESCRIPTIONS[0];
		this.canGoNegative = false;
	}
	
	@Override
	public void onRemove() {
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
			if (c instanceof ComboFinisher) ((ComboFinisher) c).onComboRemoved();
		}
		for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
			if (c instanceof ComboFinisher) ((ComboFinisher) c).onComboRemoved();
		}
		for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
			if (c instanceof ComboFinisher) ((ComboFinisher) c).onComboRemoved();
		}
		for (AbstractCard c : AbstractDungeon.player.limbo.group) {
			if (c instanceof ComboFinisher) ((ComboFinisher) c).onComboRemoved();
		}
	}
	
	@Override
	public void onAfterUseCard(AbstractCard card, UseCardAction action) {
		AbstractPlayer player = AbstractDungeon.player;
		
		if(card instanceof ComboFinisher && player.hasPower(ComboPower.POWER_ID)){
			ComboFinisher comboCard = (ComboFinisher) card;
			int comboAmount = PowersHelper.getPlayerPowerAmount(this.ID);
			if (comboAmount > 0) {
				comboCard.finisher(player, action.target, comboAmount);
				if (player.hasPower(TwizePower.POWER_ID)) {
					comboCard.finisher(player, action.target, comboAmount);
					AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(player, player, TwizePower.POWER_ID, 1));
				}
			}
			consumeCombo(player);
		}
		super.onAfterUseCard(card, action);
	}
	
	
	
	private void consumeCombo(AbstractPlayer player) {
		double comboAmount = (double) PowersHelper.getPlayerPowerAmount(ComboPower.POWER_ID);
		int consumeAmount = player.hasPower(MomentumPower.POWER_ID) ? (int) Math.ceil(comboAmount/2) : (int) comboAmount;
		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(player, player, ComboPower.POWER_ID, consumeAmount));
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
}