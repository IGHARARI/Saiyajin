package sts.saiyajin.cards.skills;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.ComboPower;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class Quickening extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.QUICKENING);

	private static final int COST = 0;
	private static final int KI_CONSUMPTION = 10;
	private static final int UPGRADED_KI_CONSUMPTION = -6;
	private static final int PLATED_ARMOR_AMOUNT = 2;
	private static final int UPGRADED_PLATED_ARMOR_AMOUNT = 1;
	private int platedArmor;
	
	final Logger logger = LogManager.getLogger(Quickening.class);
	
	public Quickening() {
		super(CardNames.QUICKENING, cardStrings.NAME, CardPaths.QUICKENING, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.SELF);
		this.baseMagicNumber = KI_CONSUMPTION;
		this.magicNumber = this.baseMagicNumber;
		this.kiRequired = KI_CONSUMPTION;
		this.platedArmor = PLATED_ARMOR_AMOUNT;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADED_KI_CONSUMPTION);
			upgradeKiRequired(UPGRADED_KI_CONSUMPTION);
			this.platedArmor += UPGRADED_PLATED_ARMOR_AMOUNT;
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new PlatedArmorPower(player, platedArmor), platedArmor));
	    AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(player, player, KiPower.POWER_ID, kiRequired));
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, player, new VulnerablePower(mo, 1, false), 1, true, AbstractGameAction.AttackEffect.NONE));
        }
        if(!AbstractDungeon.player.hasPower(ComboPower.POWER_ID)){
    	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ComboPower(player, 1), 1));
        }
	}

}
