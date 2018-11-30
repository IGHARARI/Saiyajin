package sts.saiyajin.powers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ReflectionPower
  extends AbstractPower
{
	public static final String POWER_ID = "Reflection";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Reflection");
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean justApplied;
	
	final Logger logger = LogManager.getLogger(ReflectionPower.class);
	
	public ReflectionPower(AbstractCreature owner) {
		this (owner, 1, false);
	}
	public ReflectionPower(AbstractCreature owner, final int amount) {
		this (owner, amount, false);
	}
	public ReflectionPower(AbstractCreature owner, final int amount, final boolean justApplied)
	{
		this.name = NAME;
		this.ID = "Reflection";
		this.owner = owner;
		this.amount = amount;
		this.justApplied = justApplied;
        this.isTurnBased = true;
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
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Reflection"));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Reflection", 1));
        }
    }
	
    @Override
    public int onAttacked(final DamageInfo info, final int damageAmount) {
    	logger.info("On attacked for refelctionpower");
    	logger.info("reflection damage amount : " + damageAmount);
    	logger.info("refelctionpower info : " + info.owner.id + " " + this.owner.id + " " + info.type + " " + info.output);
        if (info.owner != null && info.owner != this.owner && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, info.output, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        }
        return damageAmount;
    }
}