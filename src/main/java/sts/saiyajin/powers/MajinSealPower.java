package sts.saiyajin.powers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.ui.PowerPaths;
import sts.saiyajin.utils.PowerNames;

public class MajinSealPower extends AbstractPower {
	public static final String POWER_ID = PowerNames.MAJIN_SEAL;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(PowerNames.MAJIN_SEAL);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	final Logger logger = LogManager.getLogger(MajinSealPower.class);
	private int attacksPlayedThisTurn;
	
	public MajinSealPower(AbstractCreature owner, final int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.MAJIN_SEAL_B), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.MAJIN_SEAL), 0, 0, 48, 48);
        this.isTurnBased = true;
		this.description = DESCRIPTIONS[0];
		this.type = PowerType.BUFF;
		this.attacksPlayedThisTurn = 0;
	}
  
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
  
	@Override
	public void atStartOfTurn() {
        this.flashWithoutSound();
        attacksPlayedThisTurn = 0;
	}
	
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (this.amount > 0 && card.type == CardType.ATTACK && attacksPlayedThisTurn < this.amount) {
            this.flash();
            ++attacksPlayedThisTurn;
            if (action.target != null) {
                action.target = AbstractDungeon.getRandomMonster();
            }
        }
    }
}