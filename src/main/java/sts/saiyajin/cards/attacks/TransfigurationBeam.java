package sts.saiyajin.cards.attacks;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

import sts.saiyajin.actions.TransfigurationBeamAction;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.ui.CardPaths;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;

public class TransfigurationBeam extends SaiyanCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.TRANSFIGURATION_BEAM);

	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;
	private static final int BASE_DAMAGE = 15;
	
	public TransfigurationBeam() {
		super(CardNames.TRANSFIGURATION_BEAM, cardStrings.NAME, CardPaths.TRANSFIGURATION_BEAM, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.RARE,
		        AbstractCard.CardTarget.ENEMY);
	    this.baseDamage = BASE_DAMAGE;
	    this.exhaust = true;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBaseCost(UPGRADED_COST);
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			this.initializeDescription();
			this.exhaust = false;
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		AbstractDungeon.actionManager.addToBottom(new VFXAction(player, new SmallLaserEffect(player.hb.cX, player.hb.cY, monster.hb.cX, monster.hb.cY), 0.1f));
		AbstractDungeon.actionManager.addToBottom(new TransfigurationBeamAction(monster, new DamageInfo(player, this.damage)));
	}

}
