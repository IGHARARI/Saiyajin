package sts.saiyajin.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;
import sts.saiyajin.utils.PowerNames;
import sts.saiyajin.utils.PowersHelper;

public class Flurry extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.FLURRY);

	private static final int COST = 1;
	private static final int BASE_DAMAGE = 2;
	private static final int UPGRADE_DAMAGE = 1;
	private static final int BASE_KI_CONSUMPTION_FOR_BONUS = 5;
	private static final int UPGRADED_KI_CONSUMPTION_FOR_BONUS = -1;
	
	public Flurry() {
		super(CardNames.FLURRY, cardStrings.NAME, CardPaths.FLURRY, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.ENEMY);
	    this.baseDamage = BASE_DAMAGE;
	    this.magicNumber = this.baseMagicNumber = BASE_KI_CONSUMPTION_FOR_BONUS;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_DAMAGE);
			upgradeMagicNumber(UPGRADED_KI_CONSUMPTION_FOR_BONUS);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		int kiPower = PowersHelper.getPlayerPowerAmount(PowerNames.KI);
		DamageInfo perHitDamage = new DamageInfo(player, this.damage, this.damageTypeForTurn);
		if (kiPower >= this.magicNumber){
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(player, player, KiPower.POWER_ID, this.magicNumber));
			AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, perHitDamage, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		}
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, perHitDamage, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, perHitDamage, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, perHitDamage, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		PowersHelper.comboFollowUp();
	}

}
