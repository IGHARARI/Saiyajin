package sts.saiyajin.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.ui.CardPaths;

public class DrainingStrike extends CustomCard {

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.DRAINING_STRIKE);
	private static final int COST = 2;
	private static final int BASE_DAMAGE = 8;
	private static final int UPGRADED_DAMAGE = 10;
	
	public DrainingStrike() {
		super(CardNames.DRAINING_STRIKE, cardStrings.NAME, CardPaths.DRAINING_STRIKE, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.ATTACK,
		        CardColors.SAIYAN_CARD_COLOR,
		        AbstractCard.CardRarity.UNCOMMON,
		        AbstractCard.CardTarget.ALL_ENEMY);
		
		this.baseDamage = BASE_DAMAGE;
		this.isMultiDamage = true;
	}

	@Override
	public void upgrade() {
		if (!upgraded){
			upgradeName();
			upgradeDamage(UPGRADED_DAMAGE - BASE_DAMAGE);
		}
	}

	@Override
	public void use(AbstractPlayer player, AbstractMonster monster) {
		int unblockedDamageInstances = 0;
        final AbstractMonster[] monsters = new AbstractMonster[AbstractDungeon.getCurrRoom().monsters.monsters.size()];
        AbstractDungeon.getCurrRoom().monsters.monsters.toArray(monsters);
        for (int i = 0; i < monsters.length; ++i) {
            if (monsters[i] != null && !monsters[i].isDying) {
                if (!monsters[i].isEscaping) {
                    int tmp = this.multiDamage[i];
                    tmp -= monsters[i].currentBlock;
                    if (tmp > 0) {
                    	unblockedDamageInstances++;
                    }
                }
            }
        }
		
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player, multiDamage, damageTypeForTurn, AttackEffect.BLUNT_LIGHT));
		if (unblockedDamageInstances > 0){
            AbstractDungeon.player.gainEnergy(unblockedDamageInstances);
            AbstractDungeon.actionManager.updateEnergyGain(unblockedDamageInstances);
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                c.triggerOnGainEnergy(unblockedDamageInstances, true);
            }
		}
	}

}
