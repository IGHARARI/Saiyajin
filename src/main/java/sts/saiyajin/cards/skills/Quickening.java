package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import sts.saiyajin.cards.types.KiCard;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.ui.CardPaths;

public class Quickening extends KiCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.QUICKENING);

	private static final int COST = 1;
	private static final int KI_CONSUMPTION = 12;
	private static final int UPGRADED_KI_CONSUMPTION = -6;
	private static final int PLATED_ARMOR_AMOUNT = 2;
	private static final int UPGRADED_PLATED_ARMOR_AMOUNT = 1;
	
	public Quickening() {
		super(CardNames.QUICKENING, cardStrings.NAME, CardPaths.QUICKENING, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.ALL_ENEMY);
		this.baseMagicNumber = KI_CONSUMPTION;
		this.magicNumber = this.baseMagicNumber;
		this.kiRequired = KI_CONSUMPTION;
		this.block = PLATED_ARMOR_AMOUNT;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADED_KI_CONSUMPTION);
			upgradeBlock(UPGRADED_PLATED_ARMOR_AMOUNT);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new PlatedArmorPower(player, this.block)));
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, player, new VulnerablePower(mo, 1, false), 1, true, AbstractGameAction.AttackEffect.NONE));
        }
	}

}
