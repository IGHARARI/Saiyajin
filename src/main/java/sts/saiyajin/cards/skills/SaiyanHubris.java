package sts.saiyajin.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpeechBubble;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class SaiyanHubris extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.SAIYAN_HUBRIS);

	private static final int COST = 0;
	private static final int KI_GAIN_PER_ENEMY = 5;
	private final static int UPGRADED_KI_GAIN = 3;
	private final static String ONE_LINER = cardStrings.EXTENDED_DESCRIPTION[0];
	
	
	public SaiyanHubris() {
		super(CardNames.SAIYAN_HUBRIS, cardStrings.NAME, CardPaths.SAIYAN_HUBRIS, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.SKILL,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.SELF);
		this.baseMagicNumber = KI_GAIN_PER_ENEMY;
		magicNumber = baseMagicNumber;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADED_KI_GAIN);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		int livingMonsters = 0;
		for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
			if (!m.isDeadOrEscaped()) livingMonsters++;
		}
		if (livingMonsters > 2) {
			AbstractDungeon.effectList.add(new SpeechBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0f, ONE_LINER, true));
		}
		int kiGain =  magicNumber * livingMonsters;
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KiPower(player, kiGain), kiGain));
	}
}
