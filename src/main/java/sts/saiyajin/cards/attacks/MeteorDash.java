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

import sts.saiyajin.cards.tags.SaiyajinCustomCardTags;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;
import sts.saiyajin.utils.PowersHelper;

public class MeteorDash extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.METEOR_DASH);

	private static final int COST = 1;
	private static final int BASE_DAMAGE = 10;
	private static final int UPGRADE_DAMAGE = 3;
	
	public MeteorDash() {
		super(CardNames.METEOR_DASH, cardStrings.NAME, CardPaths.METEOR_DASH, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.ENEMY);
	    this.baseDamage = BASE_DAMAGE;
	    this.tags.add(SaiyajinCustomCardTags.COMBO_STARTER);
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
		DamageAction strikeAction = new DamageAction(monster, strikeDamage, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
		AbstractDungeon.actionManager.addToBottom(strikeAction);
		PowersHelper.startCombo();
	}

}
