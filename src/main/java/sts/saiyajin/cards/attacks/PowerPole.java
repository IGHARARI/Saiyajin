package sts.saiyajin.cards.attacks;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.IronWaveEffect;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.BattleHelper;
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
		ArrayList<AbstractMonster> orderedMonsters = BattleHelper.getCurrentBattleMonstersSortedOnX(true);
		int attackMultiplier = 1;
		if (orderedMonsters.size() > 0) {
			AbstractMonster lastMonster = orderedMonsters.get(orderedMonsters.size()-1);
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new IronWaveEffect(player.hb.cX, player.hb.cY, lastMonster.hb.cX), 0.5f));
			for (AbstractMonster m : orderedMonsters) {
				DamageInfo tempInfo = new DamageInfo(player, this.baseDamage);
				tempInfo.applyPowers(player, m);
				DamageInfo actualDamageInfo = new DamageInfo(player, tempInfo.output * attackMultiplier);
				AbstractDungeon.actionManager.addToBottom(new DamageAction(m, actualDamageInfo, AttackEffect.SLASH_HORIZONTAL, true));
				attackMultiplier *= 2;
			}
		}
	}

}
