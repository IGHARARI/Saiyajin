package sts.saiyajin.cards.powers;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.powers.GreatApePower;
import sts.saiyajin.powers.KiRegenPower;
import sts.saiyajin.ui.CardPaths;

public class GreatApeForm extends AbstractCard
{
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(CardNames.GREAT_APE_FORM);
	private static final int COST = 3;
	private static final int KI_REGEN = 15;
	private static final int PLATED_ARMOR = 8;
	private static final String NEED_FULL_MOON = "The Full Moon isn't out yet...";
	
    
    public GreatApeForm() {
		super(CardNames.GREAT_APE_FORM, cardStrings.NAME, CardPaths.GREAT_APE_FORM, COST, cardStrings.DESCRIPTION, 
		        AbstractCard.CardType.POWER,
		        CardColors.SAIYAN_EXTRA_CARD_COLOR,
		        AbstractCard.CardRarity.SPECIAL,
		        AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber;
    }
    
    @Override
    public boolean canUse(AbstractPlayer player, AbstractMonster monster) {
    	boolean canUse = super.canUse(player, monster);
    	if(!canUse) return canUse;
    	if (player.hasPower(PowerNames.FULL_MOON)){
    		return canUse;
    	}
    	this.cantUseMessage = NEED_FULL_MOON;
    	return false;
    }
    
    @Override
    public void use(final AbstractPlayer player, final AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new GreatApePower(player, 1), 1));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new KiRegenPower(player, KI_REGEN), KI_REGEN));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new PlatedArmorPower(player, PLATED_ARMOR), PLATED_ARMOR));
		AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(player.drawPile, player.exhaustPile, c -> c.type != CardType.ATTACK));
		AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(player.discardPile, player.exhaustPile, c -> c.type != CardType.ATTACK));
		AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(player.hand, player.exhaustPile, c -> c.type != CardType.ATTACK));
		
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new GreatApeForm();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }
}