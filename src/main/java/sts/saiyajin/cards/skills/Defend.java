package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.BaseModCardTags;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;
import sts.saiyajin.utils.PowersHelper;

public class Defend extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.DEFEND);

	private static final int COST = 1;
	private static final int BASE_BLOCK = 5;
	private static final int UPGRADE_BLOCK = 2;
	private static final int KI_BLOCK_BONUS = 2;
	private static final int KI_BLOCK_BONUS_REQUIREMENT = 3;
	public Defend() {
		super(CardNames.DEFEND, cardStrings.NAME, CardPaths.SAIYAN_DEFEND, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.BASIC,
		        AbstractCard.CardTarget.SELF);
	    this.tags.add(BaseModCardTags.BASIC_DEFEND);
	    this.baseBlock = BASE_BLOCK;
	    this.baseMagicNumber = KI_BLOCK_BONUS_REQUIREMENT;
	    this.magicNumber = baseMagicNumber;
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
		int kiPower = PowersHelper.getPlayerPowerAmount(KiPower.POWER_ID);
		boolean useKiBonus = kiPower >= KI_BLOCK_BONUS_REQUIREMENT;
		Defend helperCard = (Defend) this.makeStatEquivalentCopy();
		if (useKiBonus) {
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(player, player, KiPower.POWER_ID, this.magicNumber));
			helperCard.baseBlock += KI_BLOCK_BONUS;
		}
		helperCard.applyPowersToBlock();
		GainBlockAction block = new GainBlockAction(player, player, helperCard.block);
	    AbstractDungeon.actionManager.addToBottom(block);
	}

}
