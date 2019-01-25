package sts.saiyajin.powers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.ui.PowerPaths;
import sts.saiyajin.utils.PowerNames;

public class MomentumPower extends AbstractPower {

	public static final String POWER_ID = PowerNames.MOMENTUM;
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public MomentumPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = -1;
		this.type = AbstractPower.PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.MOMENTUM_B), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.MOMENTUM), 0, 0, 48, 48);
		this.canGoNegative = false;
		updateDescription();
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
}