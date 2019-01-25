package sts.saiyajin.actions;

import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class InsertCardsIntoDiscardAction extends AbstractGameAction {
    
	final Logger logger = LogManager.getLogger(InsertCardsIntoDiscardAction.class);
	CardGroup toInsert;
	Predicate<AbstractCard> predicate = null;
	
	public InsertCardsIntoDiscardAction(CardGroup toInsert) {
		this(toInsert, null);
	}
	
    public InsertCardsIntoDiscardAction(CardGroup toInsert, Predicate<AbstractCard> pred) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.toInsert = toInsert;
        this.predicate = pred;
        this.duration = Settings.ACTION_DUR_MED;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
        	while (toInsert.size() > 0){
        		AbstractCard c = toInsert.getTopCard();
        		toInsert.removeCard(c);
        		if (predicate != null && !predicate.test(c)) continue;
        		toInsert.moveToDiscardPile(c);
        	}
        }
        this.tickDuration();
    }
}
