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
import sts.saiyajin.powers.RevivePower;
import sts.saiyajin.ui.CardPaths;

public class SenzuBean extends CustomCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.SENZU_BEAN);

	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;
	private static final int REVIVE_BUFF = 5;
	
	public SenzuBean() {
		super(CardNames.SENZU_BEAN, cardStrings.NAME, CardPaths.SENZU_BEAN, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.RARE,
		        AbstractCard.CardTarget.ENEMY);
		this.baseMagicNumber = REVIVE_BUFF;
		this.magicNumber = baseMagicNumber;
		this.exhaust = true;
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
		if (!monster.hasPower(PowerNames.CANT_REVIVE) && !monster.hasPower(PowerNames.REVIVE)){
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new RevivePower(monster, this.magicNumber), 1));
		}
	}

}
