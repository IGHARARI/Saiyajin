package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.KiBarrierPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;
import sts.saiyajin.utils.PowersHelper;

public class KiExplosion extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.KI_EXPLOSION);

	private static final int COST = 2;
	private static final int KI_COST = 10;
	private static final int BASE_THORNS = 3;
	private static final int UPGRADED_THORNS = 2;
	private static final int BASE_BLOCK = 16;
	private static final int UPGRADE_BLOCK = 6;
	
	public KiExplosion() {
		super(CardNames.KI_EXPLOSION, cardStrings.NAME, CardPaths.KI_EXPLOSION, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.SELF);
	    this.baseBlock = BASE_BLOCK;
	    this.kiRequired = KI_COST;
	    this.magicNumber = this.baseMagicNumber = BASE_THORNS;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBlock(UPGRADE_BLOCK);
			upgradeMagicNumber(UPGRADED_THORNS);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		GainBlockAction block = new GainBlockAction(player, player, this.block);
	    AbstractDungeon.actionManager.addToBottom(block);
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KiBarrierPower(player, this.magicNumber), this.magicNumber));
		PowersHelper.comboFollowUp();
	}

}
