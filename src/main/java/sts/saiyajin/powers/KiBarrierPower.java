package sts.saiyajin.powers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.cards.utils.PowerNames;

public class KiBarrierPower extends AbstractPower {
	public static final String POWER_ID = PowerNames.KI_BARRIER;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	final Logger logger = LogManager.getLogger(KiBarrierPower.class);
	
	public KiBarrierPower(AbstractCreature owner, final int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.description = DESCRIPTIONS[0];
		this.img = new Texture("img/powers/reflection.png");
	}
  
	@Override
	public void updateDescription()
	{
		this.description = DESCRIPTIONS[0];
	}
  
    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }
	
    @Override
    public int onAttacked(final DamageInfo info, final int damageAmount) {
        AbstractDungeon.actionManager.addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE, true));
        return damageAmount;
    }
}