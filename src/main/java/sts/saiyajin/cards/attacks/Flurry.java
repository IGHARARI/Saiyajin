package sts.saiyajin.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
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
import sts.saiyajin.cards.utils.PowersHelper;
import sts.saiyajin.core.Saiyajin;
import sts.saiyajin.powers.ComboPower;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.ui.CardPaths;

public class Flurry extends CustomCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.FLURRY);

	private static final int COST = 1;
	private static final int BASE_DAMAGE = 2;
	private static final int UPGRADE_DAMAGE = 1;
	private static final int BASE_KI_CONSUMPTION_FOR_BONUS = 8;
	private static final int UPGRADED_KI_CONSUMPTION_FOR_BONUS = 5;
	
	public Flurry() {
		super(CardNames.FLURRY, cardStrings.NAME, CardPaths.FLURRY, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.ENEMY);
	    this.baseDamage = BASE_DAMAGE;
	    this.baseMagicNumber = BASE_KI_CONSUMPTION_FOR_BONUS;
	    magicNumber = baseMagicNumber;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_DAMAGE);
			upgradeMagicNumber(UPGRADED_KI_CONSUMPTION_FOR_BONUS - BASE_KI_CONSUMPTION_FOR_BONUS);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		Saiyajin kakarot = (Saiyajin) player;
		int kiPower = PowersHelper.getPlayerPowerAmount(PowerNames.KI);
		DamageInfo perHitDamage = new DamageInfo(player, this.damage, this.damageTypeForTurn);
		if (kiPower >= this.magicNumber){
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(player, player, KiPower.POWER_ID, this.magicNumber));
			AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, perHitDamage, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
			AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, perHitDamage, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		}
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, perHitDamage, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, perHitDamage, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, perHitDamage, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		if (kakarot.hasPower(ComboPower.POWER_ID)){
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ComboPower(player, 1), 1));
		}
	}

}
