package sts.saiyajin.cards.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.powers.KiRegenPower;
import sts.saiyajin.powers.SuperSaiyanThreeDebuffPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class SuperSaiyan3Form extends SaiyanCard
{
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.SUPER_SAIYAN_THREE_FORM);
	private static final int COST = 2;
	private static final int BASE_KI_REGEN = 10;
	private static final int UPGRADED_KI_REGEN = 4;
	private static final int BASE_STR = 5;
	private static final int UPGRADED_STR = 2;
	private static final int BASE_TURNS_DURATION = 5;
	private static final int UPGRADED_TURNS_DURATION = 7;
	
    
    public SuperSaiyan3Form() {
		super(CardNames.SUPER_SAIYAN_THREE_FORM, cardStrings.NAME, CardPaths.SUPER_SAIYAN_THREE_FORM, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.POWER,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.RARE,
		        AbstractCard.CardTarget.SELF);
		this.baseMagicNumber = BASE_STR;
		this.magicNumber = this.baseMagicNumber;
		this.kiVariable = BASE_KI_REGEN;
		this.misc = BASE_TURNS_DURATION;
    }
    
    @Override
    public void use(final AbstractPlayer player, final AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KiRegenPower(player, this.kiVariable), this.kiVariable));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KiPower(player, this.kiVariable), this.kiVariable));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new StrengthPower(player, this.magicNumber), this.magicNumber));
		for (int i = 0; i < misc; i++) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new SuperSaiyanThreeDebuffPower(player, 1), 1));
		}
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new SuperSaiyan3Form();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADED_STR);
            this.upgradeKiVariable(UPGRADED_KI_REGEN);
            this.misc = UPGRADED_TURNS_DURATION;
        }
    }
}