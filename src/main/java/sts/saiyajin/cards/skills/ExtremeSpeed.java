package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.types.KiCard;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.ui.CardPaths;

public class ExtremeSpeed extends KiCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.EXTREME_SPEED);

	private static final int COST = 1;
	private static final int KI_COST = 15;
	private static final int CARD_DRAW = 2;
	private static final int UPGRADED_CARD_DRAW = 1;
	
	
	public ExtremeSpeed() {
		super(CardNames.EXTREME_SPEED, cardStrings.NAME, CardPaths.EXTREME_SPEED, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.NONE);
		this.exhaust = true;
		baseMagicNumber = CARD_DRAW;
		magicNumber = CARD_DRAW;
		this.kiRequired = KI_COST;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADED_CARD_DRAW);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player, magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KiPower(player, -KI_COST), -KI_COST));
	}
}
