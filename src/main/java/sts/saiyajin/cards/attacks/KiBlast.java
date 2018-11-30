package sts.saiyajin.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
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
import sts.saiyajin.powers.Ki;
import sts.saiyajin.ui.CardPaths;

public class KiBlast extends CustomCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.KI_BLAST);
	private static final int COST = 0;
	private static final int BASE_DAMAGE = 4; 
	private static final int UPGRADE_DAMAGE = 2; 
	private int BASE_KI_COST = 10;
	private int UPGRADED_KI_COST = 6;
	
	public KiBlast() {
		super(CardNames.KI_BLAST, cardStrings.NAME, CardPaths.KI_BLAST, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.BASIC,
		        AbstractCard.CardTarget.ALL_ENEMY);
		
		this.baseDamage = BASE_DAMAGE;
		this.isMultiDamage = true;
		this.baseMagicNumber = BASE_KI_COST;
		this.magicNumber = this.baseMagicNumber;
	}

	@Override
	public void upgrade() {
		if (!upgraded){
			upgradeName();
			upgradeDamage(UPGRADE_DAMAGE);
			upgradeMagicNumber(UPGRADED_KI_COST - BASE_KI_COST);
		}
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		Saiyajin kakarot = (Saiyajin) player;
		Ki kiPower = (Ki) kakarot.getPower(PowerNames.KI);
		int extraDamage = 0;
		if (kiPower.amount >= this.magicNumber){
			extraDamage = 2; 
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new Ki(player, -this.magicNumber), -this.magicNumber));
		}
		int damage = this.damage + extraDamage;
		int multiDamage[] = DamageInfo.createDamageMatrix(damage, true);
		DamageAllEnemiesAction damageAction = new DamageAllEnemiesAction(
				player, multiDamage, damageTypeForTurn, AttackEffect.SLASH_HORIZONTAL);
		AbstractDungeon.actionManager.addToBottom(damageAction);
	}

}
