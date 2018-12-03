package sts.saiyajin.cards.utils;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PowersHelper {
	public static int getPlayerPowerAmount(String powerId){
		return AbstractDungeon.player.hasPower(powerId) ? 
				AbstractDungeon.player.getPower(powerId).amount : 0;
	}
}
