package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.ui.CardPaths;

public class Scouter extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.SCOUTER);

	private static final int COST = 1;
	private static final int BASE_BLOCK = 4;
	private static final int UPGRADE_BLOCK = 3;
	private static final int EXTRA_BLOCK = 4;
	private static final int KI_CONSUMPTION = 8;
	
	
	public Scouter() {
		super(CardNames.SCOUTER, cardStrings.NAME, CardPaths.SCOUTER, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.ENEMY);
		kiRequired = KI_CONSUMPTION;
		baseMagicNumber = KI_CONSUMPTION;
		magicNumber = KI_CONSUMPTION;
		this.baseBlock = BASE_BLOCK;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBlock(UPGRADE_BLOCK);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(player, player, KiPower.POWER_ID, kiRequired));
	    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new NextTurnBlockPower(player, this.block, this.name), this.block));
	    if (monster != null && !(monster.intent == AbstractMonster.Intent.ATTACK || monster.intent == AbstractMonster.Intent.ATTACK_BUFF || monster.intent == AbstractMonster.Intent.ATTACK_DEBUFF || monster.intent == AbstractMonster.Intent.ATTACK_DEFEND)){
	    	AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, EXTRA_BLOCK));
		    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new NextTurnBlockPower(player, EXTRA_BLOCK, this.name), EXTRA_BLOCK));
	    }
	}
}
