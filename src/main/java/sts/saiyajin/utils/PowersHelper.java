package sts.saiyajin.utils;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import sts.saiyajin.actions.ComboFollowUpAction;
import sts.saiyajin.actions.ComboStarterAction;
import sts.saiyajin.powers.ComboPower;

public class PowersHelper {
	public static int getPlayerPowerAmount(String powerId){
		return getCreaturePowerAmount(powerId, AbstractDungeon.player);
	}

	public static int getCreaturePowerAmount(String powerId, AbstractCreature creature){
		return creature != null && creature.hasPower(powerId) ? creature.getPower(powerId).amount : 0;
	}
	
	public static void startCombo() {
		AbstractDungeon.actionManager.addToBottom(new ComboStarterAction());
	}

	public static void comboFollowUp() {
		AbstractDungeon.actionManager.addToBottom(new ComboFollowUpAction());
	}

	public static void startOrFollowUpCombo() {
		increaseComboBy(1);
	}
	
	public static void increaseComboBy(int amount) {
		AbstractPlayer p = AbstractDungeon.player;
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ComboPower(p, amount), amount));
	}
	
}
