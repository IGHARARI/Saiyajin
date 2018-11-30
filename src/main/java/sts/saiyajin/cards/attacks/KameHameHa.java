package sts.saiyajin.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import sts.saiyajin.cards.types.ComboFinisher;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.core.Saiyajin;
import sts.saiyajin.powers.Combo;
import sts.saiyajin.powers.Ki;
import sts.saiyajin.ui.CardPaths;

public class KameHameHa extends ComboFinisher {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.KAME_HAME_HA);
	private static final int COST = 4;
	private static final int BASE_DAMAGE = 18; 
	private static final int UPGRADE_DAMAGE = 8; 
	private int KI_COST = 20; 
	
	public KameHameHa() {
		super(CardNames.KAME_HAME_HA, cardStrings.NAME, CardPaths.SAIYAN_STRIKE, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = BASE_DAMAGE;
	}

	@Override
	public void upgrade() {
		if (!upgraded){
			upgradeName();
			upgradeDamage(UPGRADE_DAMAGE);
			KI_COST = 15;
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		Saiyajin kakarot = (Saiyajin) player;
		Ki kiPower = (Ki) kakarot.getPower(PowerNames.KI);
		int kiToUse = Math.max(Math.min(kiPower.amount, KI_COST), 1);
		int damage = this.damage * (KI_COST/kiToUse);
		DamageInfo damageInfo = new DamageInfo(player, damage, this.damageTypeForTurn);
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, damageInfo, AttackEffect.BLUNT_HEAVY));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new Ki(player, -kiToUse), -kiToUse));

		Combo comboPower = (Combo) kakarot.getPower(PowerNames.COMBO);
		if (comboPower != null) {
			int comboToConsume = Math.min(comboPower.amount, this.cost);
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new Combo(player, -comboToConsume), -comboToConsume));
		}
		this.modifyCostForCombat(COST - this.cost);
	}

	@Override
	public void updatedComboChain(int amount) {
		this.modifyCostForCombat(-amount);
	}
}
