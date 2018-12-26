package sts.saiyajin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import sts.saiyajin.powers.KiRegenPower;

public class HoningAction extends AbstractGameAction {
    
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private int toExhaust;
	
    public HoningAction(int cardsToExhaust) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.toExhaust = cardsToExhaust;
    }
    
    @Override
    public void update() {
        if (this.duration != Settings.ACTION_DUR_FAST) {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                for (final AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    if (c.costForTurn == -1) {
                    	int kiGain = EnergyPanel.getCurrentEnergy() * 2;
                    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.p, this.p, new KiRegenPower(this.p, kiGain), kiGain));
                    }
                    else if (c.costForTurn > 0) {
                    	int kiGain = c.costForTurn * 2;
                    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.p, this.p, new KiRegenPower(this.p, kiGain), kiGain));
                    }
                    this.p.hand.moveToExhaustPile(c);
                }
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            }
            this.tickDuration();
            return;
        }
        if (this.p.hand.isEmpty()) {
            this.isDone = true;
            return;
        }
        AbstractDungeon.handCardSelectScreen.open(HoningAction.TEXT[0] + toExhaust + HoningAction.TEXT[1], toExhaust, true, true);
        this.tickDuration();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("HoningAction");
        TEXT = HoningAction.uiStrings.TEXT;
    }
    
}
