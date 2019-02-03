package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.powers.StrengthPower;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class ThirstForFight extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.THIRST_FOR_FIGHT);

	private static final int COST = 1;
	private static final int DRAW = 1;
	
	
	public ThirstForFight() {
		super(CardNames.THIRST_FOR_FIGHT, cardStrings.NAME, CardPaths.THIRST_FOR_FIGHT, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.ENEMY);
		this.exhaust = true;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.exhaust = false;
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player, DRAW));
		int buffsCounter = 0;
		for (AbstractPower power : monster.powers){
			if (power.type.equals(PowerType.BUFF)) buffsCounter++;
		}
		if (buffsCounter > 0){
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new StrengthPower(player, buffsCounter), buffsCounter));
		}
	}
}
