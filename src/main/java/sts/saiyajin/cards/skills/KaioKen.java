package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import basemod.abstracts.CustomCard;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.powers.Ki;
import sts.saiyajin.ui.CardPaths;

public class KaioKen extends CustomCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.KAIO_KEN);

	private static final int COST = 0;
	private static final int KI_GAIN = 20;
	private final static int VULNERABILITY_RECEIVED = 1;
	private final static int UPGRADED_VULNERABILITY_RECEIVED = 0;
	
	
	public KaioKen() {
		super(CardNames.KAIO_KEN, cardStrings.NAME, CardPaths.KAIO_KEN, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.SELF);
		this.exhaust = true;
		this.baseMagicNumber = VULNERABILITY_RECEIVED;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADED_VULNERABILITY_RECEIVED - VULNERABILITY_RECEIVED);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new Ki(player, KI_GAIN), KI_GAIN));
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new VulnerablePower(player, this.magicNumber, false), this.magicNumber));
	    
	    
	}
}
