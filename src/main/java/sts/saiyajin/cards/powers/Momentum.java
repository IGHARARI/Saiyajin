package sts.saiyajin.cards.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.MomentumPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class Momentum extends SaiyanCard
{
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.MOMENTUM);
	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;
	
    
    public Momentum() {
		super(CardNames.MOMENTUM, cardStrings.NAME, CardPaths.MOMENTUM, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.POWER,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.RARE,
		        AbstractCard.CardTarget.SELF);
    }
    
    @Override
    public void use(final AbstractPlayer player, final AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new MomentumPower(player, 1), 1));
    }
    

	@Override
    public AbstractCard makeCopy() {
        return new Momentum();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            this.isInnate = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}