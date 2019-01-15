package sts.saiyajin.cards.powers;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.special.Training;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class HyperbolicTimeChamber extends SaiyanCard
{
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.HYPER_TIME_CHAMBER);
	private static final int COST = 3;
	private static final int UPGRADED_COST = 2;
	
    
    public HyperbolicTimeChamber() {
		super(CardNames.HYPER_TIME_CHAMBER, cardStrings.NAME, CardPaths.HYPER_TIME_CHAMBER, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.POWER,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.RARE,
		        AbstractCard.CardTarget.SELF);
    }
    
    @Override
    public void use(final AbstractPlayer player, final AbstractMonster m) {
//    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new TrainingPower(player)));
    	AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new Training(2)));
    	for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
    		if (mon.isDeadOrEscaped()) continue;
    		AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(mon, player, 2));
    	}
    	
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new HyperbolicTimeChamber();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
        }
    }
}