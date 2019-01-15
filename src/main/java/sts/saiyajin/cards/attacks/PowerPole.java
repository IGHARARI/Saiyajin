package sts.saiyajin.cards.attacks;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class PowerPole extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.POWER_POLE);
	private static final int COST = 2;
	private static final int BASE_DAMAGE = 6;
	private static final int UPGRADED_DAMAGE = 2;
	
	public static final Logger logger = LogManager.getLogger(PowerPole.class);
	
	//I hate the english translation for goku's staff but I wan't people to recognize it.
	//Power pole sounds super dumb.
	public PowerPole() {
		super(CardNames.POWER_POLE, cardStrings.NAME, CardPaths.POWER_POLE, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.ALL_ENEMY);
		this.baseDamage = BASE_DAMAGE;
	}

	@Override
	public void upgrade() {
		if (!upgraded){
			upgradeName();
			upgradeDamage(UPGRADED_DAMAGE);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		int originalDamage = this.baseDamage;
		ArrayList<AbstractMonster> orderedMonsters = new ArrayList<AbstractMonster>();
		for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
			if (m.isDeadOrEscaped()) continue;
			orderedMonsters.add(m);
		}
		orderedMonsters.sort((m1,m2) -> Math.round((m1.drawX - m2.drawX)*100));
		
		for (AbstractMonster m : orderedMonsters) {
			logger.info("Monster " + m.name + " is at drawX: " + m.drawX);
			DamageInfo info = new DamageInfo(player, this.damage);
			info.applyPowers(player, m);
			AbstractDungeon.actionManager.addToBottom(new DamageAction(m, info, AttackEffect.SLASH_HORIZONTAL, true));
			this.baseDamage = this.baseDamage*2;
			applyPowers();
		}
		this.baseDamage = originalDamage;
		applyPowers();
	}

}
