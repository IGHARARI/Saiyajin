package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import sts.saiyajin.actions.InsertCardsIntoDeckAction;
import sts.saiyajin.actions.MedicineAction;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.ui.CardPaths;

public class Medicine extends CustomCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.MEDICINE);

	private static final int COST = 1;
	private static final int HEAL = 2;
	private static final int UPGRADED_HEAL = 2;
	
	
	public Medicine() {
		super(CardNames.MEDICINE, cardStrings.NAME, CardPaths.MEDICINE, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_EXTRA_CARD_COLOR,
		        AbstractCard.CardRarity.RARE,
		        AbstractCard.CardTarget.NONE);
		baseMagicNumber = HEAL;
		magicNumber = baseMagicNumber;
		this.exhaust = true;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADED_HEAL);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractCard c = this.makeCopy();
		CardGroup cg = new CardGroup(CardGroupType.UNSPECIFIED);
		cg.addToBottom(c);
		AbstractDungeon.actionManager.addToBottom(new HealAction(player, player, magicNumber));
        AbstractDungeon.actionManager.addToBottom(new MedicineAction());
        AbstractDungeon.actionManager.addToBottom(new InsertCardsIntoDeckAction(cg));
        
        
	}
}
