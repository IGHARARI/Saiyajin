package sts.saiyajin.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.ui.CardPaths;

public class Strike extends CustomCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.STRIKE);

	private static final int COST = 1;
	private static final int BASE_DAMAGE = 6;
	private static final int UPGRADE_DAMAGE = 3;
	
	public Strike() {
		super(CardNames.STRIKE, cardStrings.NAME, CardPaths.SAIYAN_STRIKE, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.BASIC,
		        AbstractCard.CardTarget.ENEMY);
	    this.tags.add(BaseModCardTags.BASIC_STRIKE);
	    this.baseDamage = BASE_DAMAGE;
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
		DamageInfo strikeDamage = new DamageInfo(player, this.damage, this.damageTypeForTurn);
		DamageAction strikeAction = new DamageAction(monster, strikeDamage, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
		AbstractDungeon.actionManager.addToBottom(strikeAction);
	}

}
