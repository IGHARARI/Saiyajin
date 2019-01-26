package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;

import sts.saiyajin.actions.MedicineAction;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class Medicine extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.MEDICINE);

	private static final int COST = 1;
	private static final int HEAL = 2;
	private static final int UPGRADED_HEAL = 1;
	
	
	public Medicine() {
		super(CardNames.MEDICINE, cardStrings.NAME, CardPaths.MEDICINE, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.RARE,
		        AbstractCard.CardTarget.NONE);
		baseMagicNumber = HEAL;
		magicNumber = baseMagicNumber;
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
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new RegenPower(player, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new MedicineAction());
	}
}
