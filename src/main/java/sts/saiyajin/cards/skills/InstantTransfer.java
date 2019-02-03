package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import sts.saiyajin.cards.tags.SaiyajinCustomCardTags;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;
import sts.saiyajin.utils.PowersHelper;

public class InstantTransfer extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.TELEPORT);

	private static final int COST = 0;
	private static final int KI_CONSUMPTION = 9;
	private static final int VULNERABILITY_STACKS = 1;
	private static final int UPGRADE_VULNERABILITY_STACKS = 1;
	
	public InstantTransfer() {
		super(CardNames.TELEPORT, cardStrings.NAME, CardPaths.TELEPORT, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.ENEMY);
		
		this.kiRequired =  KI_CONSUMPTION;
		this.magicNumber = this.baseMagicNumber = VULNERABILITY_STACKS;
		this.tags.add(SaiyajinCustomCardTags.COMBO_STARTER);
		this.tags.add(SaiyajinCustomCardTags.COMBO_FOLLOW_UP);
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADE_VULNERABILITY_STACKS);
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
	    PowersHelper.startOrFollowUpCombo();
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new VulnerablePower(monster, this.magicNumber, false), this.magicNumber));
	    if (upgraded) AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player, 1));
	}

}
