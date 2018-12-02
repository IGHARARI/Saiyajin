package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import sts.saiyajin.powers.PlayerFlightPower;
import sts.saiyajin.ui.CardPaths;

public class Fly extends KiCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.FLY);

	private static final int COST = 1;
	private static final int FLY_AMOUNT = 2;
	private static final int KI_COST = 25;
	private static final int UPGRADED_KI_COST = -10;
	
	
	public Fly() {
		super(CardNames.FLY, cardStrings.NAME, CardPaths.FLY, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.SELF);
		baseMagicNumber = KI_COST;
		magicNumber = baseMagicNumber;
		kiRequired = baseMagicNumber;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADED_KI_COST);
			kiRequired = magicNumber;
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new PlayerFlightPower(player, FLY_AMOUNT), FLY_AMOUNT));
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KiPower(player, -kiRequired), -kiRequired));
	    
	}
}
