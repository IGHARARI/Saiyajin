package sts.saiyajin.powers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.ui.PowerPaths;
import sts.saiyajin.utils.PowerNames;

public class MedicinePower extends AbstractPower {
	public static final String POWER_ID = PowerNames.MEDICINE;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	final Logger logger = LogManager.getLogger(MedicinePower.class);
	
	public MedicinePower(AbstractCreature owner, final int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.MEDICINE_B), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.MEDICINE), 0, 0, 48, 48);
        this.updateDescription();
	}
  
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
}