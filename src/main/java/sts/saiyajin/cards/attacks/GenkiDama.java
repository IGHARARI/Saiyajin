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
import com.megacrit.cardcrawl.powers.WeakPower;

import basemod.abstracts.CustomCard;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.core.Saiyajin;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.ui.CardPaths;

public class GenkiDama extends CustomCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.GENKI_DAMA);
	private static final int COST = 3;
	private static final int BASE_DAMAGE = 0;
	private static final int BASE_KI_MAX_CONSUME = 25;
	private static final int UPGRADED_KI_MAX_CONSUME = 40;
	
	public GenkiDama() {
		super(CardNames.GENKI_DAMA, cardStrings.NAME, CardPaths.SAIYAN_STRIKE, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.RARE,
		        AbstractCard.CardTarget.ALL_ENEMY);
		
		this.baseDamage = BASE_DAMAGE;
		this.baseMagicNumber = BASE_KI_MAX_CONSUME;
		this.magicNumber = this.baseMagicNumber;
	}

	@Override
	public void upgrade() {
		if (!upgraded){
			upgradeName();
			upgradeMagicNumber(UPGRADED_KI_MAX_CONSUME - BASE_KI_MAX_CONSUME);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		Saiyajin kakarot = (Saiyajin) player;
		KiPower kiPower = (KiPower) kakarot.getPower(PowerNames.KI);
		int kiToUse = Math.min(kiPower.amount, this.magicNumber);
		int multiDamage[] = DamageInfo.createDamageMatrix(kiToUse, true);
		
		DamageAllEnemiesAction damageAction = new DamageAllEnemiesAction(
				player, multiDamage, damageTypeForTurn, AttackEffect.SMASH);
		AbstractDungeon.actionManager.addToBottom(damageAction);
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KiPower(player, -kiPower.amount), -kiPower.amount));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new WeakPower(player, 2, false), 2));
	}

}
