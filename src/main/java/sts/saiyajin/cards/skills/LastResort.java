package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.powers.KiRegenPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;
import sts.saiyajin.utils.PowersHelper;

public class LastResort extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.LAST_RESORT);

	private static final int COST = 2;
	private static final int UPGR_COST = 1;
	private static final int KI_FOR_STR = 6;
	private static final int UPGRADED_KI_FOR_STR = -1;
	
	public LastResort() {
		super(CardNames.LAST_RESORT, cardStrings.NAME, CardPaths.LAST_RESORT, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.SELF);
		this.baseMagicNumber = KI_FOR_STR;
		this.magicNumber = this.baseMagicNumber;
		exhaust = true;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBaseCost(UPGR_COST);
			upgradeMagicNumber(UPGRADED_KI_FOR_STR);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		int kiPower = PowersHelper.getPlayerPowerAmount(KiPower.POWER_ID);
		int strGain = kiPower / magicNumber;
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new StrengthPower(player, strGain), strGain));
	    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, KiPower.POWER_ID));
	    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, KiRegenPower.POWER_ID));
	    if (kiPower>0)
	    	AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(player, player, KiPower.POWER_ID, kiPower));
	}

}
