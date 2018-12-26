package sts.saiyajin.cards.utils;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import sts.saiyajin.powers.ComboPower;

public class PowersHelper {
	public static int getPlayerPowerAmount(String powerId){
		return AbstractDungeon.player != null && AbstractDungeon.player.hasPower(powerId) ? 
				AbstractDungeon.player.getPower(powerId).amount : 0;
	}
	
	public static void startCombo() {
		AbstractPlayer player = AbstractDungeon.player;
		if(PowersHelper.getPlayerPowerAmount(ComboPower.POWER_ID) == 0){
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ComboPower(player, 1), 1));
		}
	}

	public static void comboFollowUp() {
		AbstractPlayer player = AbstractDungeon.player;
		if(PowersHelper.getPlayerPowerAmount(ComboPower.POWER_ID) > 0){
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ComboPower(player, 1), 1));
		}
	}
}
