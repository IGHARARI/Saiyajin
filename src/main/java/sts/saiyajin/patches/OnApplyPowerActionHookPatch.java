package sts.saiyajin.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.interfaces.OnApplyPowerActionSubscriber;

//This patch adds a hook to ApplyPowerAction so powers implementing OnApplyPowerActionSubscriber can trigger effects when ApplyPowerAction is called for Any character on the field.
/***
 * 
 * @author Lelouch
 * This patch adds a hook to ApplyPowerAction so powers implementing OnApplyPowerActionSubscriber can trigger effects when ApplyPowerAction is called for Any character on the field.
 */
@SpirePatch(clz=ApplyPowerAction.class, method=SpirePatch.CONSTRUCTOR,
	paramtypez = {AbstractCreature.class, AbstractCreature.class, AbstractPower.class, int.class, boolean.class, AttackEffect.class})
public class OnApplyPowerActionHookPatch {
		

    public static SpireReturn<Void> Prefix(ApplyPowerAction __instance, AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast, AttackEffect effect) {
    	if (AbstractDungeon.player != null) {
    		for (AbstractPower power : AbstractDungeon.player.powers) {
    			if (power instanceof OnApplyPowerActionSubscriber) {
    				((OnApplyPowerActionSubscriber) power).onApplyPowerAction(target, source, powerToApply, stackAmount, isFast, effect);
    			}
    		}
    	}
		return SpireReturn.Continue();
	}

}
