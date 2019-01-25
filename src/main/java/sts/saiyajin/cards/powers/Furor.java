package sts.saiyajin.cards.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.actions.FurorUpgradeAction;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.FurorPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class Furor extends SaiyanCard {
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.FUROR);
	private static final int COST = 1;
	private static final int STR_GAIN = 1;
	private static final int KI_GAIN = 8;
    
    public Furor() {
		super(CardNames.FUROR, cardStrings.NAME, CardPaths.FUROR, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.POWER,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.SELF);
		this.baseMagicNumber = STR_GAIN;
		this.magicNumber = baseMagicNumber;
		this.kiVariable = KI_GAIN;
    }
    
    @Override
    public void use(final AbstractPlayer player, final AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new FurorPower(player, 1), 1));
		if (upgraded) {
			AbstractDungeon.actionManager.addToBottom(new FurorUpgradeAction(kiVariable));
		}
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new Furor();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			initializeDescription();
        }
    }
}