package sts.saiyajin.cards.skills;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
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

public class SolarFlare extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.SOLAR_FLARE);

	private static final int COST = 2;
	private static final int KI_COST = 20;
	
	public SolarFlare() {
		super(CardNames.SOLAR_FLARE, cardStrings.NAME, CardPaths.SOLAR_FLARE, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.RARE,
		        AbstractCard.CardTarget.ENEMY);
		kiRequired = KI_COST;
		this.baseMagicNumber = KI_COST;
		this.magicNumber = this.baseMagicNumber;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			this.target = AbstractCard.CardTarget.ALL_ENEMY;
			initializeDescription();
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		if (!this.upgraded){
			AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(monster, player, 1));;
		} else {
			for (AbstractMonster groupMonster : AbstractDungeon.getMonsters().monsters){
				AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(groupMonster, player, 1));;
			}
		}
		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(player, player, KiPower.POWER_ID, magicNumber));
	}

}
