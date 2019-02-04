package sts.saiyajin.cards.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.PlotArmorPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class PlotArmor extends SaiyanCard {
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.PLOT_ARMOR);
	private static final int COST = 0;
	public static final int BLOCK_GAIN = 4;
    
    public PlotArmor() {
		super(CardNames.PLOT_ARMOR, cardStrings.NAME, CardPaths.PLOT_ARMOR, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.POWER,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.SELF);
		this.magicNumber = baseMagicNumber = BLOCK_GAIN;
    }

    @Override
    public AbstractCard makeCopy() {
        return new PlotArmor();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			initializeDescription();
			this.isInnate = true;
        }
    }   
    
    @Override
    public void use(final AbstractPlayer player, final AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new PlotArmorPower(player, 1), 1));
    }
    
}