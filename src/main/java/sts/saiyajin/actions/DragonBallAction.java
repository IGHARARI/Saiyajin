package sts.saiyajin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import sts.saiyajin.powers.DragonBallPower;
import sts.saiyajin.powers.PlusEnergyPower;
import sts.saiyajin.utils.CardColors;

public class DragonBallAction extends AbstractGameAction implements basemod.helpers.ModalChoice.Callback{
    
	private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DragonBallAction");
	
    public DragonBallAction() {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.actionType = ActionType.SPECIAL;
    }
    
    @Override
    public void update() {
        if (!this.isDone) {
        	this.isDone = true;
            AbstractPlayer player = AbstractDungeon.player;
            if (player.hasPower(DragonBallPower.POWER_ID) && player.getPower(DragonBallPower.POWER_ID).amount == 6){
            	AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(player, player, DragonBallPower.POWER_ID));
            	
            	ModalChoice modal = new ModalChoiceBuilder()
                        .setCallback(this)
                        .setTitle(uiStrings.TEXT[0])
                        .setColor(CardColors.SAIYAN_CARD_COLOR)
                        .addOption(uiStrings.TEXT[1], uiStrings.TEXT[2], CardTarget.NONE)
                        .addOption(uiStrings.TEXT[3], uiStrings.TEXT[4], CardTarget.NONE)
                        .addOption(uiStrings.TEXT[5], uiStrings.TEXT[6], CardTarget.NONE)
                        .addOption(uiStrings.TEXT[7], uiStrings.TEXT[8], CardTarget.NONE)
                        .create();
            	modal.open();
            } else {
            	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new DragonBallPower(player, 1), 1));
            }
        }
    }

	@Override
	public void optionSelected(AbstractPlayer paramAbstractPlayer, AbstractMonster paramAbstractMonster, int paramInt) {
		AbstractPlayer player = AbstractDungeon.player;
        switch (paramInt) {
        case 0:
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new StrengthPower(player, 9), 9));
            break;
        case 1:
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new DexterityPower(player, 5), 5));
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new PlatedArmorPower(player, 6), 6));
            break;
        case 2:
        	AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(3));
        	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new PlusEnergyPower(player, 3), 3));
            break;
        case 3:
    		int[] damage = DamageInfo.createDamageMatrix(99);
    		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, damage, DamageType.NORMAL, AttackEffect.SMASH));
        	break;
        default:
            return;
    }
		
	}

}
