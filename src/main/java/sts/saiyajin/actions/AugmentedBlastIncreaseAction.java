package sts.saiyajin.actions;

import java.util.UUID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AugmentedBlastIncreaseAction extends AbstractGameAction {
    
    private int increaseAmount;
    private UUID uuid;
    
    public AugmentedBlastIncreaseAction(final int incAmount, final UUID targetUUID) {
        this.increaseAmount = incAmount;
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.1f;
        this.uuid = targetUUID;
    }
    
    @Override
    public void update() {
        if (this.duration == 0.1f) {
            for (final AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (!c.uuid.equals(this.uuid)) {
                    continue;
                }
                c.misc += this.increaseAmount;
            }
        }
        this.tickDuration();
    }
}
