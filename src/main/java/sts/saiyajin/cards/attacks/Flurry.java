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
import sts.saiyajin.powers.Combo;
import sts.saiyajin.powers.Ki;
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
		Ki kiPower = (Ki) kakarot.getPower(PowerNames.KI);
		int actualDamage = this.damage;
		if (kiPower.amount >= this.magicNumber){
			actualDamage += 1;
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new Ki(player, -this.magicNumber), -this.magicNumber));
		}
		DamageInfo perHitDamage = new DamageInfo(player, actualDamage, this.damageTypeForTurn);
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, perHitDamage, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, perHitDamage, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, perHitDamage, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
		Combo comboPower = (Combo) kakarot.getPower(PowerNames.COMBO);
		if (comboPower != null){
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new Combo(player, 1), 1));
		}
	}

}
