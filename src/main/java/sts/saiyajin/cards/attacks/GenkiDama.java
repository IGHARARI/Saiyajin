package sts.saiyajin.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;
import sts.saiyajin.utils.PowerNames;
import sts.saiyajin.utils.PowersHelper;

public class GenkiDama extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.GENKI_DAMA);
	private static final int COST = 2;
	private static final int WEAK_GAINED = 2;
	private static final int BASE_DAMAGE = 0;
	private static final int BASE_KI_MAX_CONSUME = 30;
	private static final int UPGRADED_KI_MAX_CONSUME = 30;
	
	public GenkiDama() {
		super(CardNames.GENKI_DAMA, cardStrings.NAME, CardPaths.GENKI_DAMA, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.RARE,
		        AbstractCard.CardTarget.ALL_ENEMY);
		
		this.baseDamage = BASE_DAMAGE;
		this.isMultiDamage = true;
		this.magicNumber = this.baseMagicNumber = BASE_KI_MAX_CONSUME;
		this.misc = WEAK_GAINED;
	}

	@Override
	public void upgrade() {
		if (!upgraded){
			upgradeName();
			upgradeMagicNumber(UPGRADED_KI_MAX_CONSUME);
		}
	}
	
	@Override
	public void applyPowers() {
		int kiPower = PowersHelper.getPlayerPowerAmount(PowerNames.KI);
		int kiToUse = Math.min(kiPower, this.magicNumber);
		this.baseDamage = kiToUse;
		super.applyPowers();
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		
		DamageAllEnemiesAction damageAction = new DamageAllEnemiesAction(
				player, this.multiDamage, damageTypeForTurn, AttackEffect.SMASH);
		AbstractDungeon.actionManager.addToBottom(damageAction);
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new WeakPower(player, WEAK_GAINED, false), WEAK_GAINED));
		int kiPower = PowersHelper.getPlayerPowerAmount(PowerNames.KI);
		int kiToUse = Math.min(kiPower, this.magicNumber);
		if (kiPower>0) AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(player, player, KiPower.POWER_ID, kiToUse));
	}

}
