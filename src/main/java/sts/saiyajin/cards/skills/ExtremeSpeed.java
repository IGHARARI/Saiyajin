package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.ExtremeSpeedPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class ExtremeSpeed extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.EXTREME_SPEED);

	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;
	private static final int UPGRADED_CARD_DRAW = 1;
	
	
	public ExtremeSpeed() {
		super(CardNames.EXTREME_SPEED, cardStrings.NAME, CardPaths.EXTREME_SPEED, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.POWER,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.NONE);
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBaseCost(UPGRADED_COST);
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		if (!this.upgraded) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ExtremeSpeedPower(player)));
		} else {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ExtremeSpeedPower(player, UPGRADED_CARD_DRAW)));
		}
	}
}
