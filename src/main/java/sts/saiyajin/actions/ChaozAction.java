package sts.saiyajin.actions;

import java.util.UUID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ChaozAction extends AbstractGameAction {
    
    private int increaseAmount;
    private UUID uuid;
    
    public ChaozAction(final int incAmount, final UUID targetUUID) {
        this.increaseAmount = incAmount;
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.1f;
        this.uuid = targetUUID;
    }
    
    @Override
    public void update() {
        if (this.duration == 0.1f && this.target != null) {
                for (final AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                    if (!c.uuid.equals(this.uuid)) {
                        continue;
                    }
                    final AbstractCard abstractCard = c;
                    abstractCard.baseMagicNumber += this.increaseAmount;
                    c.isMagicNumberModified = false;
                }
        }
        this.tickDuration();
    }
}
