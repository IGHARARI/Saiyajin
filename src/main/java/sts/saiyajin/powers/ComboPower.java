package sts.saiyajin.powers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.cards.types.ComboFinisher;
import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.ui.PowerPaths;

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
		this.canGoNegative = false;
	}
	
	@Override
	public void onRemove() {
		logger.info("ON REMOVE CALLED FOR COMBO");
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
			if (c instanceof ComboFinisher) ((ComboFinisher) c).resetComboChain();
		}
		for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
			if (c instanceof ComboFinisher) ((ComboFinisher) c).resetComboChain();
		}
		for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
			if (c instanceof ComboFinisher) ((ComboFinisher) c).resetComboChain();
		}
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
}