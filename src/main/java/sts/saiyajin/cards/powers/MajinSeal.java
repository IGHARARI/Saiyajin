package sts.saiyajin.cards.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.KiRegenPower;
import sts.saiyajin.powers.MajinSealPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class MajinSeal extends SaiyanCard
{
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.MAJIN_SEAL);
	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;
	private static final int KI_REGEN = 5;
	private static final int UPGRADE_TO_KI_REGEN = 3;
	
    
    public MajinSeal() {
		super(CardNames.MAJIN_SEAL, cardStrings.NAME, CardPaths.MAJIN_SEAL, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.POWER,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.RARE,
		        AbstractCard.CardTarget.SELF);
		this.baseMagicNumber = KI_REGEN;
		this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public void use(final AbstractPlayer player, final AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KiRegenPower(player, magicNumber), magicNumber));
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new MajinSealPower(player, 1), 1));
    }
    

	@Override
    public AbstractCard makeCopy() {
        return new MajinSeal();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeMagicNumber(UPGRADE_TO_KI_REGEN);
            
        }
    }
}