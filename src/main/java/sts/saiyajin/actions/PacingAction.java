package sts.saiyajin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import sts.saiyajin.utils.PowersHelper;

public class PacingAction extends AbstractGameAction {
    
    private boolean freeToPlayOnce;
    private AbstractPlayer p;
    private int energyOnUse;
    private boolean upgraded;
    
    
    public PacingAction(final AbstractPlayer p, final int energyOnUse, final boolean upgraded, final boolean freeToPlayOnce) {
        this.freeToPlayOnce = false;
        this.energyOnUse = -1;
        this.p = p;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
        this.freeToPlayOnce = freeToPlayOnce;
    }
    
    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }
        if (this.p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            this.p.getRelic(ChemicalX.ID).flash();
        }
        if (this.upgraded) {
            ++effect;
        }
        if (effect > 0) {
        	for(int i = 0; i < effect; i++) {
        		PowersHelper.increaseComboBy(1);
        	}
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }
        this.isDone = true;
    }

}
