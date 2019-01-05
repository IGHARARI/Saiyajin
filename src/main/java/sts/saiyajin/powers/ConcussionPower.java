package sts.saiyajin.powers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.ui.PowerPaths;

public class ConcussionPower
  extends AbstractPower
{
	public static final String POWER_ID = PowerNames.CONCUSSION;
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(PowerNames.CONCUSSION);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	final Logger logger = LogManager.getLogger(ConcussionPower.class);
	
	public ConcussionPower(AbstractCreature owner, final int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.CONCUSSION_B), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.CONCUSSION), 0, 0, 48, 48);
        this.isTurnBased = true;
		this.description = DESCRIPTIONS[0];
		this.type = PowerType.DEBUFF;
	}
  
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
  
	@Override
	public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flashWithoutSound();
            AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH, true));
        }
	}
	
    @Override
    public void atEndOfRound() {
        stackPower(1);
    }
}