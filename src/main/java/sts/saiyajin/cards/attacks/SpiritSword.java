package sts.saiyajin.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

import sts.saiyajin.cards.types.ComboFinisher;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.cards.utils.PowersHelper;
import sts.saiyajin.powers.ComboPower;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.ui.CardPaths;

public class SpiritSword extends ComboFinisher {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.SPIRIT_SWORD);
	private static final int COST = 1;
	private static final int BASE_DAMAGE = 7; 
	private static final int UPGRADE_DAMAGE = 3; 
	private int BASE_KI_COST = 10;
	private int UPGRADED_KI_COST = -5;
	
	public SpiritSword() {
		super(CardNames.SPIRIT_SWORD, cardStrings.NAME, CardPaths.SPIRIT_SWORD, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = BASE_DAMAGE;
		this.baseMagicNumber = BASE_KI_COST;
		this.magicNumber = this.baseMagicNumber;
		kiRequired = magicNumber;
	}

	@Override
	public void upgrade() {
		if (!upgraded){
			upgradeName();
			upgradeDamage(UPGRADE_DAMAGE);
			upgradeMagicNumber(UPGRADED_KI_COST);
			kiRequired = magicNumber;
		}
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(player, player, KiPower.POWER_ID, magicNumber));
		AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new SmallLaserEffect(player.hb.cX, player.hb.cY, monster.hb.cX, monster.hb.cY), 0.1f));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage), AbstractGameAction.AttackEffect.NONE));
		int comboAmount = PowersHelper.getPlayerPowerAmount(ComboPower.POWER_ID);
		int targetMonsterIdx = 0;
		if (comboAmount > 0) {
			for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
				if (m == monster) break;
				targetMonsterIdx++;
			}
			if (targetMonsterIdx+1 < AbstractDungeon.getMonsters().monsters.size()) {
				AbstractMonster mon2 = AbstractDungeon.getMonsters().monsters.get(targetMonsterIdx+1);
				AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new SmallLaserEffect(monster.hb.cX, monster.hb.cY, mon2.hb.cX, mon2.hb.cY), 0.1f));
				DamageInfo info = new DamageInfo(player, this.baseDamage * comboAmount);
				info.applyPowers(player, mon2);
				AbstractDungeon.actionManager.addToBottom(new DamageAction(mon2, info, AbstractGameAction.AttackEffect.NONE));
			}
		}
	}

	@Override
	public void updatedComboChain() {
	}

	@Override
	public void resetComboChain() {
	}

}
