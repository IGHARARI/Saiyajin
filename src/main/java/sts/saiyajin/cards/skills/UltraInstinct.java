package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import sts.saiyajin.actions.UltraInstinctAction;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;
import sts.saiyajin.utils.PowerNames;
import sts.saiyajin.utils.PowersHelper;

public class UltraInstinct extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.ULTRA_INSTINCT);

	private static final int COST = -1;
	private static final int DODGDE_PER_ENERGY = 25;
	
	public UltraInstinct() {
		super(CardNames.ULTRA_INSTINCT, cardStrings.NAME, CardPaths.ULTRA_INSTINCT, COST, cardStrings.DESCRIPTION, 
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
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        int stacks = this.energyOnUse;
        if (upgraded) stacks++;
        AbstractDungeon.actionManager.addToBottom(new UltraInstinctAction(player, DODGDE_PER_ENERGY, this.freeToPlayOnce, stacks));
	}
}
