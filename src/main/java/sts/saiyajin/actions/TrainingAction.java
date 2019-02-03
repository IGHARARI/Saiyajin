package sts.saiyajin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import sts.saiyajin.cards.special.Training;

public class TrainingAction extends AbstractGameAction {
	
    public TrainingAction(int amount) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.amount = amount;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST) {
        	boolean isTraining = false;
        	for (AbstractCard card : AbstractDungeon.player.hand.group) {
        		if (card instanceof Training) {
        			Training trainCard = (Training) card;
        			trainCard.flash();
        			trainCard.addTrainingTurns(this.amount);
        			isTraining = true;
        		}
        	}
        	if (!isTraining) AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new Training(2)));
        }
        this.tickDuration();
    }

}
