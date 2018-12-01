package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.abstracts.CustomCard;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.powers.Ki;
import sts.saiyajin.ui.CardPaths;

public class LastResort extends CustomCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.LAST_RESORT);

	private static final int COST = 2;
	private static final int UPGR_COST = 1;
	private static final int KI_FOR_STR = 5;
	private static final int UPGRADED_KI_FOR_STR = -1;
	
	public LastResort() {
		super(CardNames.LAST_RESORT, cardStrings.NAME, CardPaths.LAST_RESORT, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.ENEMY);
		this.baseMagicNumber = KI_FOR_STR;
		this.magicNumber = this.baseMagicNumber;
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
		Ki kiPower = (Ki) AbstractDungeon.player.getPower(Ki.POWER_ID);
		int strGain = kiPower.amount / magicNumber;
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new StrengthPower(player, strGain), strGain));
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new Ki(player, -kiPower.amount), -kiPower.amount));
	    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, Ki.POWER_ID));
	}

}
