package sts.saiyajin.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.types.ComboFinisher;
import sts.saiyajin.powers.ComboPower;
import sts.saiyajin.powers.ReverbHitPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;
import sts.saiyajin.utils.PowersHelper;

public class ReverberatingForce extends ComboFinisher {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.REVERBERATING_FORCE);

	private static final int COST = 2;
	private static final int BASE_DAMAGE = 7;
	private static final int UPGRADE_DAMAGE = 3;
	
	public ReverberatingForce() {
		super(CardNames.REVERBERATING_FORCE, cardStrings.NAME, CardPaths.REVERBERATING_FORCE, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.ENEMY);
		this.tags.add(CardTags.STRIKE);
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
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new ReverbHitPower(monster, this.damage), this.damage));
		int comboStacks = PowersHelper.getPlayerPowerAmount(ComboPower.POWER_ID);
		for (int i = 0; i < comboStacks; i++) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new ReverbHitPower(monster, this.damage), this.damage));
		}
	}

}
