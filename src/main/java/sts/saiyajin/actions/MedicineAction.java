package sts.saiyajin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import sts.saiyajin.powers.MedicinePower;

public class MedicineAction extends AbstractGameAction {
    
    public MedicineAction() {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_FASTER;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            AbstractPlayer player = AbstractDungeon.player;
            if (player.hasPower(MedicinePower.POWER_ID) && player.getPower(MedicinePower.POWER_ID).amount > 0){
            	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ArtifactPower(player, 1), 1));
            	AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(player, player, MedicinePower.POWER_ID));
            } else {
            	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new MedicinePower(player, 1), 1));
            }
        }
        this.tickDuration();
    }

}
