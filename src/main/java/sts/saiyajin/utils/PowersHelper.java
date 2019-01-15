package sts.saiyajin.utils;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import sts.saiyajin.powers.ComboPower;
import sts.saiyajin.powers.MajinSealPower;

public class PowersHelper {
	public static int getPlayerPowerAmount(String powerId){
		return AbstractDungeon.player != null && AbstractDungeon.player.hasPower(powerId) ? 
				AbstractDungeon.player.getPower(powerId).amount : 0;
	}
	
	public static void startCombo() {
		AbstractPlayer player = AbstractDungeon.player;
		if(PowersHelper.getPlayerPowerAmount(ComboPower.POWER_ID) == 0){
			int comboStacks = 1;
			if (player.hasPower(MajinSealPower.POWER_ID)) comboStacks += player.getPower(MajinSealPower.POWER_ID).amount;
			for (int i=0; i < comboStacks; i++) {
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ComboPower(player, 1), 1));
			}
		}
	}

	public static void comboFollowUp() {
		AbstractPlayer player = AbstractDungeon.player;
		if(PowersHelper.getPlayerPowerAmount(ComboPower.POWER_ID) > 0){
			int comboStacks = 1;
			if (player.hasPower(MajinSealPower.POWER_ID)) comboStacks += player.getPower(MajinSealPower.POWER_ID).amount;
			for (int i=0; i < comboStacks; i++) {
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ComboPower(player, 1), 1));
			}
		}
	}
}
