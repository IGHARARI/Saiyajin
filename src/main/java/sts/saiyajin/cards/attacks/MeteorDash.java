package sts.saiyajin.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.core.Saiyajin;
import sts.saiyajin.powers.ComboPower;
import sts.saiyajin.ui.CardPaths;

public class MeteorDash extends CustomCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.METEOR_DASH);

	private static final int COST = 1;
	private static final int BASE_DAMAGE = 7;
	private static final int UPGRADE_DAMAGE = 2;
	
	public MeteorDash() {
		super(CardNames.METEOR_DASH, cardStrings.NAME, CardPaths.METEOR_DASH, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.ENEMY);
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
		Saiyajin kakarot = (Saiyajin) player;
		DamageInfo strikeDamage = new DamageInfo(player, this.damage, this.damageTypeForTurn);
		DamageAction strikeAction = new DamageAction(monster, strikeDamage, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
		AbstractDungeon.actionManager.addToBottom(strikeAction);
		ComboPower comboPower = (ComboPower) kakarot.getPower(PowerNames.COMBO);
		if (comboPower == null){
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ComboPower(player, 1), 1));
		}
	}

}
