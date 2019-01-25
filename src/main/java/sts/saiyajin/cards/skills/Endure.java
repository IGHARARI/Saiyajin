package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;
import sts.saiyajin.utils.PowerNames;
import sts.saiyajin.utils.PowersHelper;

public class Endure extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.ENDURE);

	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;
	
	
	public Endure() {
		super(CardNames.ENDURE, cardStrings.NAME, CardPaths.ENDURE, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.SELF);
		this.baseBlock = PowersHelper.getPlayerPowerAmount(PowerNames.KI);
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBaseCost(UPGRADED_COST);
		}
	}
	
	@Override
	public void applyPowers() {
		this.baseBlock = PowersHelper.getPlayerPowerAmount(PowerNames.KI);
		super.applyPowers();
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		int kiPower = PowersHelper.getPlayerPowerAmount(PowerNames.KI);
	    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
	    if (kiPower>0) {
	    	AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(player, player, KiPower.POWER_ID, kiPower));
	    }
	    
	}
}
