package sts.saiyajin.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import sts.saiyajin.cards.types.KiCard;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.powers.Ki;
import sts.saiyajin.ui.CardPaths;

public class BigBangAttack extends KiCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.BIG_BANG_ATTACK);
	private static final int COST = 1;
	private static final int BASE_DAMAGE = 12; 
	private static final int UPGRADE_DAMAGE = 4; 
	private int BASE_KI_COST = 10;
	private int UPGRADED_KI_COST = -2;
	
	public BigBangAttack() {
		super(CardNames.BIG_BANG_ATTACK, cardStrings.NAME, CardPaths.BIG_BANG_ATTACK, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.ENEMY);
		
		this.baseDamage = BASE_DAMAGE;
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
		AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new MindblastEffect(player.dialogX, player.dialogY, player.flipHorizontal), 0.1f));
		AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(player, this.damage), AbstractGameAction.AttackEffect.NONE));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new Ki(player, -this.magicNumber), -this.magicNumber));
	}

}
