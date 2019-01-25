package sts.saiyajin.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.tags.SaiyajinCustomCardTags;

//This patch adds a hook to ApplyPowerAction so powers implementing OnApplyPowerActionSubscriber can trigger effects when ApplyPowerAction is called for Any character on the field.
/***
 * 
 * @author Lelouch
 * This patch adds a hook to ApplyPowerAction so powers implementing OnApplyPowerActionSubscriber can trigger effects when ApplyPowerAction is called for Any character on the field.
 */
@SpirePatch(clz=AbstractPlayer.class, method="useCard",
	paramtypez = {AbstractCard.class, AbstractMonster.class, int.class})
public class CardTargetRandomizerPatch {
		

    public static SpireReturn<Void> Prefix(AbstractPlayer __instance, AbstractCard card, @ByRef AbstractMonster[] monsterTarget, int energyOnUse) {
    	if (AbstractDungeon.player != null) {
    		if (card.hasTag(SaiyajinCustomCardTags.RANDOMIZE_TARGET)) {
    			monsterTarget[0] = AbstractDungeon.getRandomMonster();
    		} else if (card.hasTag(SaiyajinCustomCardTags.RANDOMIZE_TARGET_ONCE)) {
    			monsterTarget[0] = AbstractDungeon.getRandomMonster();
    			card.tags.remove(SaiyajinCustomCardTags.RANDOMIZE_TARGET_ONCE);
    		}
    	}
		return SpireReturn.Continue();
	}

}
