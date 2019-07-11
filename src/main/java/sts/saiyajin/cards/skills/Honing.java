package sts.saiyajin.cards.skills;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.actions.HoningAction;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class Honing extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.HONING);

	private static final int COST = 1;
	
	
	public Honing() {
		super(CardNames.HONING, cardStrings.NAME, CardPaths.HONING, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.SELF);
		this.exhaust = true;
		this.baseMagicNumber = 1;
		this.magicNumber = baseMagicNumber;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(1);
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			initializeDescription();
			this.exhaust = false;
			ExhaustiveVariable.setBaseValue(this, 2);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		int extraKiRegen = 1;
		if (upgraded) extraKiRegen++;
    	AbstractDungeon.actionManager.addToBottom(new HoningAction(magicNumber, extraKiRegen));
	}
}
