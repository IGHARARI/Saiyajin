package sts.saiyajin.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;
import sts.saiyajin.cards.tags.SaiyajinCustomCardTags;

public class PlanningAction extends AbstractGameAction {
    
	final Logger logger = LogManager.getLogger(PlanningAction.class);
	private final int amount;
	private final AbstractPlayer p;
	private final static String[] TEXT = CardCrawlGame.languagePack.getUIString("PlanningAction").TEXT;
	
	public PlanningAction(int amount) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.amount = amount;
        this.p = AbstractDungeon.player;
    }
    
    @Override
    public void update() {
        if (this.p.hand.size() >= BaseMod.MAX_HAND_SIZE) {
            this.isDone = true;
            return;
        }
        CardGroup availableCards = new CardGroup(CardGroupType.CARD_POOL);
        for (AbstractCard c : this.p.drawPile.group) {
        	if (c.hasTag(SaiyajinCustomCardTags.COMBO_STARTER) || c.hasTag(SaiyajinCustomCardTags.COMBO_FOLLOW_UP)) {
        		availableCards.addToRandomSpot(c);
        	}
        }
        if (availableCards.size() == 0) {
        	this.isDone = true;
        	return;
        }
        if (availableCards.size() == 1) {
            final AbstractCard card = availableCards.group.get(0);
            this.p.hand.addToHand(card);
            card.lighten(false);
            this.p.drawPile.removeCard(card);
            this.p.hand.refreshHandLayout();
            this.isDone = true;
            return;
        }
        if (this.duration == Settings.ACTION_DUR_MED) {
            AbstractDungeon.gridSelectScreen.open(availableCards, this.amount, TEXT[0], false);
            this.tickDuration();
            return;
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            for (final AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                this.p.hand.addToHand(c);
                this.p.drawPile.removeCard(c);
                c.lighten(false);
                c.unhover();
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.p.hand.refreshHandLayout();
            for (final AbstractCard c : availableCards.group) {
                c.unhover();
                c.target_x = CardGroup.DRAW_PILE_X;
                c.target_y = 0.0f;
            }
            this.isDone = true;
        }
        this.tickDuration();
    }
}
