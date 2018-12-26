package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.actions.InsertCardsIntoDeckAction;
import sts.saiyajin.actions.InsertCardsIntoDiscardAction;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.ui.CardPaths;

public class DragonRadar extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.DRAGON_RADAR);

	private static final int COST = 2;
	private static final int UPGRADED_COST = 0;
	
	
	public DragonRadar() {
		super(CardNames.DRAGON_RADAR, cardStrings.NAME, CardPaths.DRAGON_RADAR, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.SELF);
		this.exhaust = true;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeBaseCost(UPGRADED_COST);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		DragonBall dbc = new DragonBall();
		CardGroup toDraw = new CardGroup(CardGroupType.UNSPECIFIED);
		CardGroup toDiscard = new CardGroup(CardGroupType.UNSPECIFIED);
		for(int i = 0; i < 3; i++) {
			AbstractCard c = dbc.makeCopy();
			toDraw.addToBottom(c);
		}
		for(int i = 0; i < 4; i++) {
			AbstractCard c = dbc.makeCopy();
			toDiscard.addToBottom(c);
		}
		AbstractDungeon.actionManager.addToBottom(new InsertCardsIntoDeckAction(toDraw));
		AbstractDungeon.actionManager.addToBottom(new InsertCardsIntoDiscardAction(toDiscard));
	}
}
