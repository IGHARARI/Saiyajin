package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.actions.MindBoomAction;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.ConcussionPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class MindBoom extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.MIND_BOOM);

	private static final int COST = 1;
	private static final int BASE_CONCUSSION_MULTIPLIER = 2;
	private static final int UPGRADE_CONCUSSION_MULTIPLIER = 1;
	private static final int CONCUSSION_STACKS = 2;
	
	
	public MindBoom() {
		super(CardNames.MIND_BOOM, cardStrings.NAME, CardPaths.MIND_BOOM, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.RARE,
		        AbstractCard.CardTarget.ENEMY);
		this.exhaust = true;
		this.magicNumber = this.baseMagicNumber = BASE_CONCUSSION_MULTIPLIER;
		this.misc = CONCUSSION_STACKS;
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			this.upgradeMagicNumber(UPGRADE_CONCUSSION_MULTIPLIER);
			rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new ConcussionPower(monster, this.misc), this.misc));
		AbstractDungeon.actionManager.addToBottom(new MindBoomAction(monster, player, this.magicNumber));
	}
}
