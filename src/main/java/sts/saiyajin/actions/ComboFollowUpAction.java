package sts.saiyajin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import sts.saiyajin.powers.ComboPower;
import sts.saiyajin.powers.MajinSealPower;
import sts.saiyajin.utils.PowersHelper;

public class ComboFollowUpAction extends AbstractGameAction {
    
    public ComboFollowUpAction() {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_XFAST;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST) {
    		AbstractPlayer player = AbstractDungeon.player;
    		if(PowersHelper.getPlayerPowerAmount(ComboPower.POWER_ID) > 0){
    			int comboStacks = 1;
    			if (player.hasPower(MajinSealPower.POWER_ID)) comboStacks += player.getPower(MajinSealPower.POWER_ID).amount;
    			for (int i=0; i < comboStacks; i++) {
    				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ComboPower(player, 1), 1));
    			}
    		}
        }
        this.tickDuration();
    }

}
