package sts.saiyajin.powers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.ui.PowerPaths;

public class KiPower extends AbstractPower {

	public static final String POWER_ID = PowerNames.KI;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
//	private static final int KI_REGEN_AMOUNT = 3;

	public KiPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.KI_B), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.KI), 0, 0, 48, 48);
		this.canGoNegative = false;
		updateDescription();
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
	
//	@Override
//	public void atStartOfTurn() {
//		super.atStartOfTurn();
//		AbstractPlayer player = AbstractDungeon.player;
//		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new Ki(player, KI_REGEN_AMOUNT), KI_REGEN_AMOUNT));
//	}
}