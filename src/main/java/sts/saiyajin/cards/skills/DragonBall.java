package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.actions.DragonBallAction;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.ui.CardPaths;

public class DragonBall extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.DRAGON_BALL);

	private static final int COST = 1;
	
	
	public DragonBall() {
		super(CardNames.DRAGON_BALL, cardStrings.NAME, CardPaths.DRAGON_BALL, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_EXTRA_CARD_COLOR,
		        AbstractCard.CardRarity.SPECIAL,
		        AbstractCard.CardTarget.NONE);
		this.exhaust = true;
	}

	@Override
	public void upgrade() {
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new DragonBallAction());
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player, 1));
	}
}
