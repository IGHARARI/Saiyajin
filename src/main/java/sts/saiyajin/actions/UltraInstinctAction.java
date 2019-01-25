package sts.saiyajin.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import sts.saiyajin.powers.DodgePower;

public class UltraInstinctAction extends AbstractGameAction {
    
    private boolean freeToPlayOnce;
    private AbstractPlayer p;
    private int energyOnUse;
    public static final Logger logger = LogManager.getLogger(UltraInstinctAction.class);
    
    public UltraInstinctAction(final AbstractPlayer p, final int amount, final boolean freeToPlayOnce, final int energyOnUse) {
        this.freeToPlayOnce = false;
        this.amount = amount;
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
	        int effect = this.energyOnUse;
	        if (this.p.hasRelic(ChemicalX.ID)) {
	            effect += 2;
	            this.p.getRelic(ChemicalX.ID).flash();
	        }
	        if (effect > 0) {
	            for (int i = 0; i < effect; ++i) {
	            	logger.info("Applying dodge amount: " + amount);
	                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.p, this.p, new DodgePower(p, amount), amount));
	            }
	            if (!this.freeToPlayOnce) {
	                this.p.energy.use(EnergyPanel.totalCount);
	            }
	        }
        }
        this.tickDuration();
    }

    
}
