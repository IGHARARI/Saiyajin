package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.RetainOncePower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class RemoveWeights extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.REMOVE_WEIGHTS);

	private static final int COST = 0;
	private static final int TEMP_STR = 2;
	private static final int UPGR_TEMP_STR = 1;
	
	
	public RemoveWeights() {
		super(CardNames.REMOVE_WEIGHTS, cardStrings.NAME, CardPaths.REMOVE_WEIGHTS, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.SELF);
		magicNumber = baseMagicNumber = TEMP_STR;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGR_TEMP_STR);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new StrengthPower(player, magicNumber), magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new LoseStrengthPower(player, magicNumber), magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new RetainOncePower(player), 1));
	}
}
