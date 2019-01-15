package sts.saiyajin.powers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.core.Saiyajin;
import sts.saiyajin.ui.PowerPaths;
import sts.saiyajin.utils.PowerNames;
import sts.saiyajin.utils.PowersHelper;

public class BurningSoulPower extends AbstractPower {
	public static final String POWER_ID = PowerNames.BURNING_SOUL;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	final Logger logger = LogManager.getLogger(BurningSoulPower.class);
	
	public BurningSoulPower(AbstractCreature owner, final int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.BURNING_SOUL_B), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.BURNING_SOUL), 0, 0, 48, 48);
	}
  
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
  
	@Override
	public void atEndOfTurn(boolean isPlayer) {
		if(isPlayer){
			Saiyajin kakarot = (Saiyajin) AbstractDungeon.player;
			if (kakarot.hasPower(PowerNames.KI)){
				int kiPower = PowersHelper.getPlayerPowerAmount(PowerNames.KI);
				int usableKi = Math.min(this.amount, kiPower);
				if (usableKi > 0) {
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(kakarot, kakarot, new KiBarrierPower(kakarot, usableKi), usableKi));
					AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(kakarot, kakarot, KiPower.POWER_ID, usableKi));
				}
			}
		}
	}
}