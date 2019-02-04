package sts.saiyajin.cards.attacks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

import sts.saiyajin.cards.types.ComboFinisher;
import sts.saiyajin.powers.ConcussionPower;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class Makankosappo extends ComboFinisher {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.MAKANKOSAPPO);
	private static final int COST = 2;
	private static final int BASE_DAMAGE = 3; 
	private static final int BASE_HIT_TIMES = 7; 
	private static final int UPGRADE_DAMAGE = 1; 
	private static final int KI_COST = 15; 
	private static final int UPGRADED_KI_COST = -3; 
	
	final Logger logger = LogManager.getLogger(Makankosappo.class);
	
	public Makankosappo() {
		super(CardNames.MAKANKOSAPPO, cardStrings.NAME, CardPaths.MAKANKOSAPPO, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = BASE_DAMAGE;
		this.kiRequired = this.magicNumber = this.baseMagicNumber = KI_COST;
		this.misc = BASE_HIT_TIMES;
	}

	@Override
	public void upgrade() {
		if (!upgraded){
			upgradeName();
			upgradeDamage(UPGRADE_DAMAGE);
			upgradeMagicNumber(UPGRADED_KI_COST);
			upgradeKiRequired(UPGRADED_KI_COST);
		}
	}

	@Override
	public void finisher(AbstractPlayer player, AbstractCreature monster, int comboStacks) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new ConcussionPower(monster, comboStacks*2), comboStacks*2));
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		for(int i = 0; i < BASE_HIT_TIMES; i++) {
			DamageInfo damageInfo = new DamageInfo(player, this.damage, this.damageTypeForTurn);
			AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, damageInfo, AttackEffect.BLUNT_LIGHT, true));
			AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new SmallLaserEffect(player.hb.cX, player.hb.cY, monster.hb.cX, monster.hb.cY), 0.07f));
		}
	}
}
