package sts.saiyajin.cards.attacks;

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
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class DoubleMasenko extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.DOUBLE_MASENKO);
	private static final int COST = 1;
	private static final int BASE_DAMAGE = 10; 
	private static final int UPGRADE_DAMAGE = 4; 
	private static final int KI_COST = 12; 
	
	final Logger logger = LogManager.getLogger(DoubleMasenko.class);
	
	public DoubleMasenko() {
		super(CardNames.DOUBLE_MASENKO, cardStrings.NAME, CardPaths.DOUBLE_MASENKO, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.COMMON,
		        AbstractCard.CardTarget.ALL_ENEMY);
		
		this.baseDamage = BASE_DAMAGE;
		this.baseMagicNumber = KI_COST;
		this.magicNumber = this.baseMagicNumber;
		this.kiRequired = KI_COST;
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
		AbstractMonster mon1 = AbstractDungeon.getMonsters().getRandomMonster(true);
		AbstractMonster mon2 = AbstractDungeon.getMonsters().getRandomMonster(mon1, true);
		AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new SmallLaserEffect(player.hb.cX, player.hb.cY, mon1.hb.cX, mon1.hb.cY), 0.1f));
		DamageInfo info = new DamageInfo(player, this.baseDamage);
		info.applyPowers(player, mon1);
		AbstractDungeon.actionManager.addToBottom(new DamageAction(mon1, info, AttackEffect.SLASH_DIAGONAL));
		if (mon2 != null && mon2 != mon1){
			info = new DamageInfo(player, this.baseDamage);
			AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new SmallLaserEffect(player.hb.cX, player.hb.cY, mon2.hb.cX, mon2.hb.cY), 0.1f));
			info.applyPowers(player, mon2);
			AbstractDungeon.actionManager.addToBottom(new DamageAction(mon2, info, AttackEffect.SLASH_DIAGONAL));
			
		}
	}
}
