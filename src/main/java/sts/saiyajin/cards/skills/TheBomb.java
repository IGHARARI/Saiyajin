package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.TheBombPower;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.ChaozIncreasePower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class TheBomb extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.THE_BOMB);
	private static final int COST = 1;
	private static final int BASE_DAMAGE = 8;
	private static final int DAMAGE_INCREASE = 1;
	private static final int UPGRADED_DAMAGE_INCREASE = 2;
	
	public TheBomb() {
		super(CardNames.THE_BOMB, cardStrings.NAME, CardPaths.THE_BOMB, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.RARE,
		        AbstractCard.CardTarget.ALL_ENEMY);
		this.exhaust = true;
		this.baseMagicNumber = BASE_DAMAGE;
		this.magicNumber = baseMagicNumber;
		this.misc = DAMAGE_INCREASE;
	}

	@Override
	public void upgrade() {
		if (!upgraded){
			upgradeName();
			this.misc = UPGRADED_DAMAGE_INCREASE;
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new TheBombPower(player, 3, this.magicNumber), 3));
		int dmgIncrease = upgraded? UPGRADED_DAMAGE_INCREASE : DAMAGE_INCREASE;
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ChaozIncreasePower(player, 3, dmgIncrease, this.uuid), 3));
	}

}
