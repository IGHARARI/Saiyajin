package sts.saiyajin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import sts.saiyajin.core.Saiyajin;
import sts.saiyajin.powers.Ki;

public class KiRegenAction extends AbstractGameAction {

	public KiRegenAction(AbstractCreature target, AbstractCreature source, int amount) {
		setValues(target, source, amount);
		this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;	
		}
	
	@Override
	public void update() {
		if (!(this.target instanceof Saiyajin)) return;
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.target, this.source, new Ki(this.target, this.amount), this.amount));
	}

}
