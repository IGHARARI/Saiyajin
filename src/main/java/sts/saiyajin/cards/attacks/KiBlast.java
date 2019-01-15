package sts.saiyajin.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;
import sts.saiyajin.utils.PowersHelper;

public class KiBlast extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.KI_BLAST);
	private static final int COST = 0;
	private static final int BASE_DAMAGE = 4; 
	private static final int UPGRADE_DAMAGE = 2; 
	private static final int BASE_KI_COST = 8;
	private static final int UPGRADED_KI_COST = -4;
	
	public KiBlast() {
		super(CardNames.KI_BLAST, cardStrings.NAME, CardPaths.KI_BLAST, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.BASIC,
		        AbstractCard.CardTarget.ALL_ENEMY);
		
		this.baseDamage = BASE_DAMAGE;
		this.isMultiDamage = true;
		this.baseMagicNumber = BASE_KI_COST;
		this.magicNumber = this.baseMagicNumber;
	}

	@Override
	public void upgrade() {
		if (!upgraded){
			upgradeName();
			upgradeDamage(UPGRADE_DAMAGE);
			upgradeMagicNumber(UPGRADED_KI_COST);
		}
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		int kiPower = PowersHelper.getPlayerPowerAmount(KiPower.POWER_ID);
		int extraDamage = 0;
		if (kiPower >= this.magicNumber){
			extraDamage = 2; 
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(player, player, KiPower.POWER_ID, magicNumber));
		}
		int originalDamage = this.baseDamage;
		this.baseDamage += extraDamage;
		applyPowers();
		calculateCardDamage(null);
		DamageAllEnemiesAction damageAction = new DamageAllEnemiesAction(player, this.multiDamage, damageTypeForTurn, AttackEffect.SLASH_HORIZONTAL);
		AbstractDungeon.actionManager.addToBottom(damageAction);
		for (AbstractMonster m : AbstractDungeon.getMonsters().monsters){
			if (m.isDeadOrEscaped()) continue;
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.06f));
		}
		PowersHelper.startCombo();
		
		this.baseDamage = originalDamage;
	}

}
