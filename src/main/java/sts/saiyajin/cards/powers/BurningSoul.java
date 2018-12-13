package sts.saiyajin.cards.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.powers.BurningSoulPower;
import sts.saiyajin.ui.CardPaths;

public class BurningSoul extends CustomCard
{
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.BURNING_SOUL);
	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;
	private static final int BASE_KI_BARRIER = 3;
	private static final int UPGRADED_KI_BARRIER = 2;
    
    public BurningSoul() {
		super(CardNames.BURNING_SOUL, cardStrings.NAME, CardPaths.BURNING_SOUL, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.POWER,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
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
            this.upgradeMagicNumber(UPGRADED_KI_BARRIER);
        }
    }
}