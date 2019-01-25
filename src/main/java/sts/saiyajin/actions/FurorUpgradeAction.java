package sts.saiyajin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import sts.saiyajin.powers.FurorPower;

public class FurorUpgradeAction extends AbstractGameAction {
    
	private int kiAmount;
	
    public FurorUpgradeAction(int kiAmount) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.kiAmount = kiAmount;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST) {
        	if (AbstractDungeon.player.hasPower(FurorPower.POWER_ID)) {
        		((FurorPower)AbstractDungeon.player.getPower(FurorPower.POWER_ID)).kiToRegen += kiAmount;
        	}
        }
        this.tickDuration();
    }

}
