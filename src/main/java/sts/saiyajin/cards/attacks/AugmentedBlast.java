package sts.saiyajin.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.actions.AugmentedBlastIncreaseAction;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;
import sts.saiyajin.utils.PowersHelper;

public class AugmentedBlast extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.AUGMENTED_BLAST);

	private static final int COST = 1;
	private static final int BASE_DAMAGE = 1;
	private static final int BASE_ENHANCE_DAMAGE = 1;
	private static final int UPGRADED_ENHANCE_DAMAGE = 1;
	
	public AugmentedBlast() {
		super(CardNames.AUGMENTED_BLAST, cardStrings.NAME, CardPaths.AUGMENTED_BLAST, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.ENEMY);
	    this.baseDamage = BASE_DAMAGE;
	    this.magicNumber = this.baseMagicNumber = BASE_ENHANCE_DAMAGE;
	    this.misc = BASE_DAMAGE; // MISC HOLDS THE TRUE BASE DAMAGE FOR THE CARD
	    this.exhaust = true;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADED_ENHANCE_DAMAGE);
		}
	}
	
	@Override
	public void applyPowers() {
		this.baseDamage = getConsumableKi();
		super.applyPowers();
		if (this.damage != this.misc) this.isDamageModified = true;
	}

	private int getConsumableKi() {
		int kiAmount = PowersHelper.getPlayerPowerAmount(KiPower.POWER_ID);
		return Math.min(kiAmount, this.misc);
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AttackEffect effect = AbstractGameAction.AttackEffect.BLUNT_LIGHT;
		if (this.damage > 15) effect = AbstractGameAction.AttackEffect.BLUNT_HEAVY;
		
		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(player, player, KiPower.POWER_ID, getConsumableKi()));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage, this.damageTypeForTurn), effect));
		AbstractDungeon.actionManager.addToBottom(new AugmentedBlastIncreaseAction(this.magicNumber, this.uuid));
	}

}
