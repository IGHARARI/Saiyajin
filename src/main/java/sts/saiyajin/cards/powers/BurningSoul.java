package sts.saiyajin.cards.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.powers.BurningSoulPower;
import sts.saiyajin.ui.CardPaths;

public class BurningSoul extends AbstractCard
{
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.BURNING_SOUL);
	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;
	private static final int BASE_KI_BARRIER = 5;
	private static final int UPGRADED_KI_BARRIER = 8;
    
    public BurningSoul() {
		super(CardNames.KI_EXPLOSION, cardStrings.NAME, CardPaths.KI_EXPLOSION, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.POWER,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.SELF);
		this.baseMagicNumber = BASE_KI_BARRIER;
		this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer player, final AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new BurningSoulPower(player, this.magicNumber), this.magicNumber));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new BurningSoul();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
            this.upgradeMagicNumber(UPGRADED_KI_BARRIER - BASE_KI_BARRIER);
        }
    }
}