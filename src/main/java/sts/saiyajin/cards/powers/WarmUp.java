package sts.saiyajin.cards.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.WarmUpPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class WarmUp extends SaiyanCard
{
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.WARM_UP);
	private static final int COST = 1;
	private static final int ENERGY_RETURN_PER_TURN = 1;
	private static final int UPG_ENERGY_RETURN_PER_TURN = 1;
	
    
    public WarmUp() {
		super(CardNames.WARM_UP, cardStrings.NAME, CardPaths.WARM_UP, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.POWER,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = ENERGY_RETURN_PER_TURN;
    }
    @Override
    public AbstractCard makeCopy() {
        return new WarmUp();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPG_ENERGY_RETURN_PER_TURN);
        }
    }
    
    @Override
    public void use(final AbstractPlayer player, final AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new WarmUpPower(player, magicNumber), magicNumber));
    }
}