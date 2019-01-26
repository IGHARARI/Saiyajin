package sts.saiyajin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import sts.saiyajin.powers.ConcussionPower;
import sts.saiyajin.utils.PowersHelper;

public class MindBoomAction extends AbstractGameAction {
    
	
	public MindBoomAction(AbstractCreature target, AbstractCreature source) {
		this(target, source, 1);
	}

	public MindBoomAction(AbstractCreature target, AbstractCreature source, int multiplier) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.target = target;
        this.source = source;
        this.amount = multiplier;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST) {
        	int concussionStacks = PowersHelper.getCreaturePowerAmount(ConcussionPower.POWER_ID, this.target);
        	if (concussionStacks > 0) {
        		for (int i = 1 ; i < this.amount; i++) {
        			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, source, new ConcussionPower(target, concussionStacks), concussionStacks));
        		}
        	}
        }
        this.tickDuration();
    }

}
