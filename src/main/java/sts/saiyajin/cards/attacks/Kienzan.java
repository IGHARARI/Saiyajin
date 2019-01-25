package sts.saiyajin.cards.attacks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class Kienzan extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.KIENZAN);
	private static final int COST = 1;
	private static final int BASE_DAMAGE = 15; 
	private static final int UPGRADE_DAMAGE = 6; 
	private static final int KI_COST = 20; 
	private static final int UPGRADED_KI_COST = -5; 
	
	final Logger logger = LogManager.getLogger(Kienzan.class);
	
	public Kienzan() {
		super(CardNames.KIENZAN, cardStrings.NAME, CardPaths.KIENZAN, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.ENEMY);
		
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
			upgradeMagicNumber(UPGRADED_KI_COST);
			upgradeKiRequired(UPGRADED_KI_COST);
		}
	}
	
	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new MindblastEffect(player.dialogX, player.dialogY, player.flipHorizontal), 0.1f));
		DamageInfo damageInfo = new DamageInfo(player, this.damage, this.damageTypeForTurn);
		AbstractDungeon.actionManager.addToBottom(new LoseHPAction(monster, player, damageInfo.output, AttackEffect.SLASH_HORIZONTAL));
		AbstractDungeon.effectList.add(new FlashAtkImgEffect(monster.hb.cX, monster.hb.cY, AttackEffect.SLASH_DIAGONAL));
		AbstractDungeon.effectList.add(new FlashAtkImgEffect(monster.hb.cX, monster.hb.cY, AttackEffect.SLASH_VERTICAL));
	}
}
