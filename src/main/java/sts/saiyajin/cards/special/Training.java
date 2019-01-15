package sts.saiyajin.cards.special;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardNames;

public class Training extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.TRAINING);

	private static final int COST = -2;
	public static final Logger logger = LogManager.getLogger(Training.class);
	
	public Training() {
		this(2);
	}
	
	public Training(Integer turnsLeft) {
		super(CardNames.TRAINING, cardStrings.NAME, CardPaths.TRAINING, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.STATUS,
		        CardColor.COLORLESS,
		        AbstractCard.CardRarity.SPECIAL,
		        AbstractCard.CardTarget.NONE);
		this.baseMagicNumber = turnsLeft;
		this.magicNumber = turnsLeft;
		this.retain = true;
	}

	@Override
	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		return false;
	}
	
	@Override
	public void triggerOnExhaust() {
		if (this.magicNumber > 0) {
			AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new Training(this.magicNumber)));
		}
	}
	
	@Override
	public void triggerOnManualDiscard() {
		AbstractDungeon.actionManager.addToBottom(new WaitAction(1.0f));
		AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(AbstractDungeon.player.hand, AbstractDungeon.player.discardPile, c -> c == this));
	}
	
	@Override
	public void triggerOnEndOfTurnForPlayingCard() {
		retain = true;
		upgradeMagicNumber(-1);
		if (magicNumber <= 0) {
			AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
			return;
		}
	}
	
	@Override
	public void triggerOnEndOfPlayerTurn() {
	}
	
	@Override
	public boolean canPlay(AbstractCard card) {
		if (super.canPlay(card)) {
			if (isCardTargettingEnemy(card)) {
				card.cantUseMessage = cardStrings.UPGRADE_DESCRIPTION;
				return false;
			}
			return true;
		}
		return false;
	}
	
	private boolean isCardTargettingEnemy(AbstractCard card) {
		return card.target.equals(CardTarget.ALL_ENEMY) || card.target.equals(CardTarget.ENEMY) 
				|| card.target.equals(CardTarget.ALL) || card.target.equals(CardTarget.SELF_AND_ENEMY);
	}

	@Override
	public void upgrade() {
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
	}
}
