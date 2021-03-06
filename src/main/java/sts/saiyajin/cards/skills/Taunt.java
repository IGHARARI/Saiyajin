package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.ReflectionPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class Taunt extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.TAUNT);

	private static final int COST = 1;
	private static final int UPGRADED_COST = 0;
	private static final int STR_GAIN = 2;
	
	
	public Taunt() {
		super(CardNames.TAUNT, cardStrings.NAME, CardPaths.TAUNT, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.ENEMY);
		this.baseMagicNumber = STR_GAIN;
		this.magicNumber = STR_GAIN;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBaseCost(UPGRADED_COST);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new StrengthPower(monster, this.magicNumber),  this.magicNumber));
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ReflectionPower(player, 2),  2));
	}

}
