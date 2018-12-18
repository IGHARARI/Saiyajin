package sts.saiyajin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import sts.saiyajin.powers.DragonBallPower;

public class DragonBallAction extends AbstractGameAction {
    
    public DragonBallAction() {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.actionType = ActionType.SPECIAL;
    }
    
    @Override
    public void update() {
        if (!this.isDone) {
            this.isDone = true;
            AbstractPlayer player = AbstractDungeon.player;
            if (player.hasPower(DragonBallPower.POWER_ID) && player.getPower(DragonBallPower.POWER_ID).amount == 6){
            	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new StrengthPower(player, 7), 7));
            	AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(player, player, DragonBallPower.POWER_ID));
            } else {
            	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new DragonBallPower(player, 1), 1));
            }
        }
    }

}
