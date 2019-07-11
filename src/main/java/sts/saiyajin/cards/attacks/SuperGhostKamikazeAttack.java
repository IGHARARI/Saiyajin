package sts.saiyajin.cards.attacks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.actions.KamikazeGhostAction;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class SuperGhostKamikazeAttack extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.GHOST_ATTACK);
	private static final int COST = 3;
	private static final int BASE_DAMAGE = 7; 
	private static final int UPGRADE_DAMAGE = 2;
	private static final int NUMBER_OF_HITS = 9;
	private static final int VULN_PER_HIT = 1;
	
	private static final int KI_COST = 50; 
	
	final Logger logger = LogManager.getLogger(SuperGhostKamikazeAttack.class);
	
	public SuperGhostKamikazeAttack() {
		super(CardNames.GHOST_ATTACK, cardStrings.NAME, CardPaths.GHOST_ATTACK, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.RARE,
		        AbstractCard.CardTarget.ALL_ENEMY);
		
		this.baseDamage = BASE_DAMAGE;
		this.magicNumber = this.baseMagicNumber = VULN_PER_HIT;
		this.kiRequired = KI_COST;
		this.misc = NUMBER_OF_HITS;
		this.exhaust = true;
	}

	@Override
	public void upgrade() {
		if (!upgraded){
			upgradeName();
			upgradeDamage(UPGRADE_DAMAGE);
		}
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new KamikazeGhostAction(this.misc, baseDamage, magicNumber));
	}
}
