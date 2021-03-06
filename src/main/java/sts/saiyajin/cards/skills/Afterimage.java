package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;

import sts.saiyajin.cards.tags.SaiyajinCustomCardTags;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;
import sts.saiyajin.utils.PowersHelper;

public class Afterimage extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.AFTERIMAGE);

	private static final int COST = 1;
	private static final int BASE_DAZES = 2;
	private static final int UPGRADE_DAZES = -1;
	
	
	public Afterimage() {
		super(CardNames.AFTERIMAGE, cardStrings.NAME, CardPaths.AFTERIMAGE, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.SELF);
		this.exhaust = true;
		this.baseMagicNumber = BASE_DAZES;
		this.magicNumber = baseMagicNumber;
		this.tags.add(SaiyajinCustomCardTags.COMBO_FOLLOW_UP);
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADE_DAZES);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new BufferPower(player, 1)));
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Dazed(), magicNumber, false, false));
		
		PowersHelper.comboFollowUp();
	}
}
