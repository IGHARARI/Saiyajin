package sts.saiyajin.cards.special;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardNames;

public class KiBurn extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.KI_BURN);

	private static final int COST = -2;
	public static final Logger logger = LogManager.getLogger(KiBurn.class);
	
	public KiBurn() {
		this(8);
	}
	
	public KiBurn(Integer kiToBurn) {
		super(CardNames.KI_BURN, cardStrings.NAME, CardPaths.KI_BURN, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.STATUS,
		        CardColor.COLORLESS,
		        AbstractCard.CardRarity.SPECIAL,
		        AbstractCard.CardTarget.NONE);
		this.baseMagicNumber = this.magicNumber = kiToBurn;
		this.isEthereal = true;
	}
	
	@Override
	public void triggerWhenDrawn() {
		super.triggerWhenDrawn();
		AbstractPlayer player = AbstractDungeon.player;
		AbstractDungeon.actionManager.addToTop(new ReducePowerAction(player, player, KiPower.POWER_ID, magicNumber));
	}
}
