package sts.saiyajin.utils;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import sts.saiyajin.actions.ComboFollowUpAction;
import sts.saiyajin.actions.ComboStarterAction;

public class PowersHelper {
	public static int getPlayerPowerAmount(String powerId){
		return AbstractDungeon.player != null && AbstractDungeon.player.hasPower(powerId) ? 
				AbstractDungeon.player.getPower(powerId).amount : 0;
	}
	
	public static void startCombo() {
		AbstractDungeon.actionManager.addToBottom(new ComboStarterAction());
	}

	public static void comboFollowUp() {
		AbstractDungeon.actionManager.addToBottom(new ComboFollowUpAction());
	}
}
