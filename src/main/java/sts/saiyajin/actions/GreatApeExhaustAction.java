package sts.saiyajin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GreatApeExhaustAction extends AbstractGameAction {
    
	AbstractCard usedGreatApe;
	
    public GreatApeExhaustAction(AbstractCard usedGreatApe) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.usedGreatApe = usedGreatApe;
        this.duration = Settings.ACTION_DUR_LONG;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_LONG) {
            this.isDone = true;
            boolean uiOffset = false;
            AbstractPlayer player = AbstractDungeon.player;
            for (final AbstractCard c : player.hand.group) {
            	if (c.type != CardType.ATTACK && !c.equals(usedGreatApe)) {
            		moveCardToScreenCenter(c, uiOffset);
            		AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, player.hand));
            		uiOffset = !uiOffset;
            	}
            }
            for (final AbstractCard c : player.drawPile.group) {
            	if (c.type != CardType.ATTACK && !c.equals(usedGreatApe)) {
            		moveCardToScreenCenter(c, uiOffset);
            		AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, player.drawPile));
            		uiOffset = !uiOffset;
            	}
            }
            for (final AbstractCard c : player.discardPile.group) {
            	if (c.type != CardType.ATTACK && !c.equals(usedGreatApe)) {
            		moveCardToScreenCenter(c, uiOffset);
            		AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, player.discardPile));
            		uiOffset = !uiOffset;
            	}
            }
        }
        this.tickDuration();
    }

	private void moveCardToScreenCenter(AbstractCard c, boolean offset) {
		c.current_y = -200.0f * Settings.scale;
		c.target_x = Settings.WIDTH / 2.0f + 200.0f * Settings.scale;
		if (offset) c.target_x += 250.0f * Settings.scale;
		c.target_y = Settings.HEIGHT / 2.0f;
		c.targetAngle = 0.0f;
		c.lighten(false);
		c.drawScale = 0.12f;
		c.targetDrawScale = 0.75f;
	}
}
