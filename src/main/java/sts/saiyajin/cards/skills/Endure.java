package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.powers.Ki;
import sts.saiyajin.ui.CardPaths;

public class Endure extends CustomCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.ENDURE);

	private static final int COST = 3;
	private static final int UPGRADED_COST = 2;
	
	
	public Endure() {
		super(CardNames.ENDURE, cardStrings.NAME, CardPaths.ENDURE, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.SELF);
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBaseCost(UPGRADED_COST);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		Ki kiPower = (Ki) AbstractDungeon.player.getPower(PowerNames.KI);
	    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, kiPower.amount));
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new Ki(player, -kiPower.amount), -kiPower.amount));
	    
	}
}
