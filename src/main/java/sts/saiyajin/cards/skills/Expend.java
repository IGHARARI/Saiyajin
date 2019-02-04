package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class Expend extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.EXPEND);

	private static final int COST = 0;
	private static final int KI_COST = 10;
	private static final int ENERGY_GAIN_ON_USE = 1;
	
	
	public Expend() {
		super(CardNames.EXPEND, cardStrings.NAME, CardPaths.EXPEND, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.SELF);
		this.exhaust = true;
		this.magicNumber = baseMagicNumber = ENERGY_GAIN_ON_USE;
		this.kiRequired = KI_COST;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			initializeDescription();
			this.exhaust = false;
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));
	}
}
