package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.RevivePower;
import sts.saiyajin.powers.WeakRevivePower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;
import sts.saiyajin.utils.PowerNames;

public class SenzuBean extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.SENZU_BEAN);

	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;
	private static final int REVIVE_BUFF = 5;
	
	public SenzuBean() {
		super(CardNames.SENZU_BEAN, cardStrings.NAME, CardPaths.SENZU_BEAN, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.RARE,
		        AbstractCard.CardTarget.ENEMY);
		this.baseMagicNumber = REVIVE_BUFF;
		this.magicNumber = baseMagicNumber;
		this.exhaust = true;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBaseCost(UPGRADED_COST);
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			initializeDescription();
			upgradeMagicNumber(-1);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		if (!monster.hasPower(MinionPower.POWER_ID) && !monster.hasPower(PowerNames.CANT_REVIVE) && !monster.hasPower(PowerNames.REVIVE)){
			if (!this.upgraded){
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new RevivePower(monster, this.magicNumber), 1));
			} else {
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new WeakRevivePower(monster, this.magicNumber), 1));
			}
		}
	}

}
