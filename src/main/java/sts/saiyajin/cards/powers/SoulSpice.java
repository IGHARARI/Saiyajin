package sts.saiyajin.cards.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.SoulSpicePower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class SoulSpice extends SaiyanCard
{
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.SOUL_SPICE);
	private static final int COST = 1;
	private static final int STR_GAIN = 1;
	private static final int UPGRADED_STR_GAIN = 1;
	
    
    public SoulSpice() {
		super(CardNames.SOUL_SPICE, cardStrings.NAME, CardPaths.SOUL_SPICE, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.POWER,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.SELF);
		this.baseMagicNumber = STR_GAIN;
		this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer player, final AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new SoulSpicePower(player, magicNumber), magicNumber));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new SoulSpice();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADED_STR_GAIN);
        }
    }
}