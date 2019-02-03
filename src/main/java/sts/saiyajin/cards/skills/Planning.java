package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.actions.PlanningAction;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.RetainOncePower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class Planning extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.PLANNING);

	private static final int COST = 1;
	private static final int UPGRADED_COST = 0;
	
//	private static final int KI_GAIN = 4;
//	private static final int UPGRADED_KI_GAIN = 4;
	
	public Planning() {
		super(CardNames.PLANNING, cardStrings.NAME, CardPaths.PLANNING, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.SELF);
		
//		this.magicNumber = this.baseMagicNumber = KI_GAIN;
		this.exhaust = true;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBaseCost(UPGRADED_COST);
//			upgradeMagicNumber(UPGRADED_KI_GAIN);
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.exhaust = false;
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
	    AbstractDungeon.actionManager.addToBottom(new PlanningAction(1));
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new RetainOncePower(player), 1));
	}

}
