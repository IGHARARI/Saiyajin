package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.ui.CardPaths;

public class PowerUp extends CustomCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.POWER_UP);

	private static final int COST = 1;
	private static final int BASE_KI_GAIN = 10;
	private static final int UPGRADE_KI_GAIN = 5;
	private static final int BASE_BLOCK = 5;
	private static final int UPGRADE_BLOCK = 3;
	
	public PowerUp() {
		super(CardNames.POWER_UP, cardStrings.NAME, CardPaths.POWER_UP, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.SELF);
	    this.baseBlock = BASE_BLOCK;
	    this.baseMagicNumber = BASE_KI_GAIN;
	    this.magicNumber = this.baseMagicNumber	;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBlock(UPGRADE_BLOCK);
			upgradeMagicNumber(UPGRADE_KI_GAIN);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		GainBlockAction block = new GainBlockAction(player, player, this.block);
	    AbstractDungeon.actionManager.addToBottom(block);
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KiPower(player, magicNumber), magicNumber));
	}

}
