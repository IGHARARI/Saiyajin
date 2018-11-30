package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.powers.Ki;
import sts.saiyajin.ui.CardPaths;

public class Defend extends CustomCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.DEFEND);

	private static final int COST = 1;
	private static final int BASE_BLOCK = 6;
	private static final int UPGRADE_BLOCK = 2;
	private static final int KI_BLOCK_BONUS = 2;
	private static final int KI_BLOCK_BONUS_REQUIREMENT = 10;
	public Defend() {
		super(CardNames.DEFEND, cardStrings.NAME, CardPaths.SAIYAN_DEFEND, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.BASIC,
		        AbstractCard.CardTarget.SELF);
	    this.tags.add(BaseModCardTags.BASIC_DEFEND);
	    this.baseBlock = BASE_BLOCK;
	    this.baseMagicNumber = KI_BLOCK_BONUS_REQUIREMENT;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBlock(UPGRADE_BLOCK);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		Ki kiPower = (Ki) player.getPower(PowerNames.KI);
		int blockAmount = this.block;
		if (kiPower != null && kiPower.amount >= 10) blockAmount += KI_BLOCK_BONUS;
		GainBlockAction block = new GainBlockAction(player, player, blockAmount);
	    AbstractDungeon.actionManager.addToBottom(block);
	}

}
