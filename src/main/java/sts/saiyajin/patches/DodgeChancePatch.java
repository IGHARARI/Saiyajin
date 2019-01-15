package sts.saiyajin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;

import sts.saiyajin.powers.DodgePower;
import sts.saiyajin.utils.PowersHelper;

@SpirePatch(clz=AbstractPlayer.class, method="damage")
public class DodgeChancePatch {
	    public static SpireReturn<Void> Prefix(AbstractPlayer self, DamageInfo info) {
	    	if(AbstractDungeon.player.hasPower(DodgePower.POWER_ID)) {
	    		if (info.type == DamageType.NORMAL && info.output > 0 && checkPlayerDodge()) {
	    			int damageAmount = 0;
	    			if (info.owner == self) {
	    				for (AbstractRelic r : self.relics) {
	    					r.onAttack(info, damageAmount, self);
	    				}
	    			}
	    			if (info.owner != null) {
	    				for (AbstractPower p : info.owner.powers) {
	    					p.onAttack(info, damageAmount, self);
	    				}
	    				for (AbstractPower p : self.powers) {
	    					damageAmount = p.onAttacked(info, damageAmount);
	    				}
	    				for (AbstractRelic r : self.relics) {
	    					damageAmount = r.onAttacked(info, damageAmount);
	    				}
	    			}
	    			AbstractDungeon.effectList.add(new BlockedWordEffect(self, self.hb.cX, self.hb.cY, "Dodged"));
	    			return SpireReturn.Return(null);
	    		}
	    		return SpireReturn.Continue();
	    	} else {
	    		return SpireReturn.Continue();
	    	}
	    }

		private static boolean checkPlayerDodge() {
			return AbstractDungeon.cardRandomRng.random(99) < PowersHelper.getPlayerPowerAmount(DodgePower.POWER_ID);
		}
}
