package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class KaioKen extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.KAIO_KEN);

	private static final int COST = 0;
	private static final int KI_GAIN = 20;
	private final static int UPGRADED_KI_GAIN = 10;
	private final static int VULNERABILITY = 1;
	
	
	public KaioKen() {
		super(CardNames.KAIO_KEN, cardStrings.NAME, CardPaths.KAIO_KEN, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.BASIC,
		        AbstractCard.CardTarget.SELF);
		this.exhaust = true;
		this.magicNumber = this.baseMagicNumber = KI_GAIN;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADED_KI_GAIN);
			rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			initializeDescription();
			exhaust = false;
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KiPower(player, magicNumber), magicNumber));
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new VulnerablePower(player, VULNERABILITY, false), VULNERABILITY));
	}
}
