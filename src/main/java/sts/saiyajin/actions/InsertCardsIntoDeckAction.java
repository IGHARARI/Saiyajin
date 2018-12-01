package sts.saiyajin.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class InsertCardsIntoDeckAction extends AbstractGameAction {
    
	final Logger logger = LogManager.getLogger(InsertCardsIntoDeckAction.class);
	CardGroup toInsert;
	
    public InsertCardsIntoDeckAction(CardGroup toInsert) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.toInsert = toInsert;
    }
    
    @Override
    public void update() {
        if (!this.isDone) {
        	while (toInsert.size() > 0){
        		AbstractCard c = toInsert.getTopCard();
        		toInsert.removeCard(c);
        		toInsert.moveToDeck(c, true);
        	}
            this.isDone = true;
        }
    }
}
