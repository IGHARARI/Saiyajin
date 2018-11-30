package sts.saiyajin.cards.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;

import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.powers.BurningSoulPower;
import sts.saiyajin.ui.CardPaths;

public class SuperSaiyanForm extends AbstractCard
{
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.SUPER_SAIYAN_FORM);
	private static final int COST = 2;
	private static final int BASE_KI_REGEN = 4;
	private static final int UPGRADED_KI_REGEN = 2;
	private int metallicizeAmount;
	private static final int BASE_METALLICIZE = 6;
	private static final int UPGRADED_METALLICIZE = 8;
	
    
    public SuperSaiyanForm() {
		super(CardNames.SUPER_SAIYAN_FORM, cardStrings.NAME, CardPaths.SUPER_SAIYAN_FORM, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.POWER,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.SELF);
		this.baseMagicNumber = BASE_KI_REGEN;
		this.magicNumber = this.baseMagicNumber;
		metallicizeAmount = BASE_METALLICIZE;
    }
    
    @Override
    public void use(final AbstractPlayer player, final AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new BurningSoulPower(player, this.magicNumber), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new MetallicizePower(player, this.metallicizeAmount), this.metallicizeAmount));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new SuperSaiyanForm();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADED_KI_REGEN);
            metallicizeAmount = UPGRADED_METALLICIZE;
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			initializeDescription();
        }
    }
}