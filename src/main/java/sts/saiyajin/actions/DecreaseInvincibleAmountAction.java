package sts.saiyajin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.InvinciblePower;

public class DecreaseInvincibleAmountAction extends AbstractGameAction {
    
	private AbstractCreature owner;
	
	public DecreaseInvincibleAmountAction(AbstractCreature owner) {
		this(owner, 0);
	}
	
    public DecreaseInvincibleAmountAction(AbstractCreature owner, int decreaseTo) {
        this.setValues(owner, owner);
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.owner = owner;
        this.amount = decreaseTo; 
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST) {
        	if (owner!= null && owner.hasPower(InvinciblePower.POWER_ID)) {
        		InvinciblePower power = (InvinciblePower)owner.getPower(InvinciblePower.POWER_ID);
        		power.amount = this.amount;
        	}
        }
        this.tickDuration();
    }

}
