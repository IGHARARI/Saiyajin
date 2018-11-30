package sts.saiyajin.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.types.ComboFinisher;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.cards.utils.DescriptionStrings;
import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.powers.Ki;
import sts.saiyajin.ui.CardPaths;

public class KameHameHa extends ComboFinisher {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.KAME_HAME_HA);
	private static final int COST = 3;
	private static final int BASE_DAMAGE = 16; 
	private static final int UPGRADE_DAMAGE = 8; 
	private static final int KI_COST = 20; 
	private static final int UPGRADED_KI_COST = -10; 
	
	public KameHameHa() {
		super(CardNames.KAME_HAME_HA, cardStrings.NAME, CardPaths.SAIYAN_STRIKE, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = BASE_DAMAGE;
		this.baseMagicNumber = KI_COST;
		this.magicNumber = this.baseMagicNumber;
	}

	@Override
	public void upgrade() {
		if (!upgraded){
			upgradeName();
			upgradeDamage(UPGRADE_DAMAGE);
			upgradeMagicNumber(UPGRADED_KI_COST);
		}
	}
	
	@Override
	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		boolean canUse = super.canUse(p, m);
		if (!canUse) return false;
		if (AbstractDungeon.player.hasPower(PowerNames.KI)){
			Ki kiPower = (Ki) AbstractDungeon.player.getPower(PowerNames.KI);
			if (kiPower.amount >= this.magicNumber) return true;
		}
		this.cantUseMessage = DescriptionStrings.KI_CARD_CANT_USE;
		return false;
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		DamageInfo damageInfo = new DamageInfo(player, this.damage, this.damageTypeForTurn);
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, damageInfo, AttackEffect.BLUNT_HEAVY));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new Ki(player, -this.magicNumber), -this.magicNumber));
	}

	@Override
	public void updatedComboChain(int amount) {
		this.modifyCostForCombat(-amount);
	}

	@Override
	public void resetComboChain() {
		int costDiff = COST - this.costForTurn; 
		this.modifyCostForCombat(costDiff);
	}
}
