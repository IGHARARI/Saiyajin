package sts.saiyajin.powers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.core.Saiyajin;
import sts.saiyajin.ui.PowerPaths;

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
		this.img = new Texture(PowerPaths.STUN);
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
				Ki kiPower = (Ki) kakarot.getPower(PowerNames.KI);
				int usableKi = Math.min(this.amount, kiPower.amount);
				if (usableKi > 0) {
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(kakarot, kakarot, new KiBarrierPower(kakarot, usableKi), usableKi));
				    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(kakarot, kakarot, new Ki(kakarot, -usableKi), -usableKi));
				}
			}
		}
	}
}