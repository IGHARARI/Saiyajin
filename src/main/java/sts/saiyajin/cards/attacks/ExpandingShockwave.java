package sts.saiyajin.cards.attacks;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.tags.SaiyajinCustomCardTags;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.BattleHelper;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;
import sts.saiyajin.utils.PowersHelper;

public class ExpandingShockwave extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.EXPANDING_SHOCKWAVE);

	private static final int COST = 1;
	private static final int BASE_DAMAGE = 5;
	private static final int UPGRADE_DAMAGE = 2;
	
	public ExpandingShockwave() {
		super(CardNames.EXPANDING_SHOCKWAVE, cardStrings.NAME, CardPaths.EXPANDING_SHOCKWAVE, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.ENEMY);
	    this.baseDamage = BASE_DAMAGE;
	    this.isMultiDamage = true;
	    this.tags.add(SaiyajinCustomCardTags.COMBO_FOLLOW_UP);
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_DAMAGE);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		int[] damageValues = this.multiDamage;
		int mainMonsterIndex = AbstractDungeon.getMonsters().monsters.indexOf(monster);
		if (mainMonsterIndex == -1) {
			logger.info("Target Monster not found when using expanding shockwave. wat.");
			return;
		}
		
		
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, damageValues[mainMonsterIndex]), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5f));
		ArrayList<AbstractMonster> orderedMonsters = BattleHelper.getCurrentBattleMonstersSortedDistanceTo(monster, true);
		for (AbstractMonster m : orderedMonsters) {
			if (m == monster) continue;
			int index = AbstractDungeon.getMonsters().monsters.indexOf(m);
			AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(player, damageValues[index] *2), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
		}
		PowersHelper.comboFollowUp();
	}

}
