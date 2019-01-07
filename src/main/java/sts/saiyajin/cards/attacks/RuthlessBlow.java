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
import com.megacrit.cardcrawl.powers.VulnerablePower;

import sts.saiyajin.cards.types.ComboFinisher;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.cards.utils.PowersHelper;
import sts.saiyajin.powers.ComboPower;
import sts.saiyajin.ui.CardPaths;

public class RuthlessBlow extends ComboFinisher {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.RUTHLESS_BLOW);

	private static final int COST = 1;
	private static final int BASE_DAMAGE = 8;
	private static final int UPGRADE_DAMAGE = 4;
	
	public RuthlessBlow() {
		super(CardNames.RUTHLESS_BLOW, cardStrings.NAME, CardPaths.RUTHLESS_BLOW, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.BASIC,
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
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
		int comboChain = PowersHelper.getPlayerPowerAmount(ComboPower.POWER_ID);
		if (comboChain > 0) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new VulnerablePower(monster, comboChain, false), comboChain));
		}
	}

	@Override
	public void updatedComboChain() {
	}

	@Override
	public void resetComboChain() {
	}

}
