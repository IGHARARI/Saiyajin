package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import sts.saiyajin.cards.special.KiBurn;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;
import sts.saiyajin.utils.PowerNames;
import sts.saiyajin.utils.PowersHelper;

public class Overexert extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.OVEREXERT);

	private static final int COST = 1;
	private static final int BASE_STR_GAIN = 2;
	private static final int KI_GAIN = 20;
	private static final int UPGRADE_STR_GAIN = 1;
	private static final int KI_BURNS = 2;
	
	
	public Overexert() {
		super(CardNames.OVEREXERT, cardStrings.NAME, CardPaths.OVEREXERT, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = BASE_STR_GAIN;
		this.kiVariable = KI_GAIN;
		this.exhaust = true;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADE_STR_GAIN);
		}
	}
	
	@Override
	public void hover() {
		super.hover();
		this.baseBlock = PowersHelper.getPlayerPowerAmount(PowerNames.KI);
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KiPower(player, this.kiVariable), this.kiVariable));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new StrengthPower(player, magicNumber), magicNumber));
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new KiBurn(), KI_BURNS, true, false));
	}
}
