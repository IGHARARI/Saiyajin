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
import sts.saiyajin.powers.Combo;
import sts.saiyajin.powers.Ki;
import sts.saiyajin.ui.CardPaths;

public class InstantTransfer extends KiCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.TELEPORT);

	private static final int COST = 0;
	private static final int KI_CONSUMPTION = 10;
	private static final int UPGRADED_KI_CONSUMPTION = 1;
	
	public InstantTransfer() {
		super(CardNames.TELEPORT, cardStrings.NAME, CardPaths.TELEPORT, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.ALL_ENEMY);
		this.baseMagicNumber = KI_CONSUMPTION;
		this.magicNumber = this.baseMagicNumber;
		this.kiRequired = KI_CONSUMPTION;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADED_KI_CONSUMPTION - KI_CONSUMPTION);
			this.kiRequired = UPGRADED_KI_CONSUMPTION;
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new Combo(player, 1), 1));
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new Ki(player, -this.magicNumber), -this.magicNumber));
	}

}
