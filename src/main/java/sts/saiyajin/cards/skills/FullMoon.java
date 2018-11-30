package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.powers.FullMoonPower;
import sts.saiyajin.ui.CardPaths;

public class FullMoon extends CustomCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.FULL_MOON);

	private static final int COST = 0;
	
	
	public FullMoon() {
		super(CardNames.FULL_MOON, cardStrings.NAME, CardPaths.FULL_MOON, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.SELF);
		this.exhaust = true;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		if(!player.hasPower(PowerNames.FULL_MOON)){
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new FullMoonPower(player, 1), 1));
		}
	}
}
