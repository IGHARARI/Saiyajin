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
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;

import sts.saiyajin.cards.types.ComboFinisher;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class FinalFlash extends ComboFinisher {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.FINAL_FLASH);
	private static final int COST = 2;
	private static final int BASE_DAMAGE = 14; 
	private static final int UPGRADE_DAMAGE = 6; 
	private static final int KI_COST = 7; 
	
	final Logger logger = LogManager.getLogger(FinalFlash.class);
	
	public FinalFlash() {
		super(CardNames.FINAL_FLASH, cardStrings.NAME, CardPaths.FINAL_FLASH, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = BASE_DAMAGE;
		this.magicNumber = this.baseMagicNumber = this.kiRequired = KI_COST;
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
		DamageInfo damageInfo = new DamageInfo(player, this.damage, this.damageTypeForTurn);
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, damageInfo, AttackEffect.NONE));
		AbstractDungeon.effectList.add(new FlashAtkImgEffect(monster.hb.cX, monster.hb.cY, AttackEffect.FIRE));
	}

	@Override
	public void finisher(AbstractPlayer player, AbstractCreature monster, int comboStacks) {
		AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new FlyingOrbEffect(monster.hb.cX, monster.hb.cY), 0.4f));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, player, new StrengthPower(monster, -comboStacks), -comboStacks));
	}
}
