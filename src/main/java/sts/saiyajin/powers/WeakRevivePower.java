package sts.saiyajin.powers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import sts.saiyajin.ui.PowerPaths;
import sts.saiyajin.utils.PowerNames;

public class WeakRevivePower extends AbstractPower {

	public static final String POWER_ID = PowerNames.REVIVE;
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public static final Logger logger = LogManager.getLogger(WeakRevivePower.class);
	
	private int buffAmount = 0;

	public WeakRevivePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = -1;
		this.buffAmount = amount;
		this.type = AbstractPower.PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.SENZU_REVIVE_B), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.SENZU_REVIVE), 0, 0, 48, 48);
		this.canGoNegative = false;
	}
	
	@Override
	public int onAttacked(DamageInfo info, int damageAmount) {
		
		if (!this.owner.hasPower(PowerNames.REVIVE) || this.owner.currentHealth > damageAmount) return super.onAttacked(info, damageAmount);
		AbstractDungeon.actionManager.addToTop(new HealAction(owner, owner, owner.maxHealth));
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, owner, new StrengthPower(owner, buffAmount), buffAmount));
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, owner, new MetallicizePower(owner, buffAmount*3), buffAmount*3));
		AbstractDungeon.actionManager.addToTop(new GainBlockAction(owner, owner, buffAmount*3));
		AbstractDungeon.actionManager.addToTop(new StunMonsterAction((AbstractMonster) owner, owner, 1));
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, owner, new CantRevivePower(owner, 1), 1));
		for(AbstractPower power : this.owner.powers){
			AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(owner, owner, power));
		}
		int dmgReturn = 0;
		if (owner.currentHealth > 1){
			dmgReturn = owner.currentHealth -1;
		}
		AbstractDungeon.actionManager.addToTop(new WaitAction(1f));
		AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.COMMON);
		//TODO: CHECK IF ESCAPING GIVES RELIC ANYWAY
		return dmgReturn;
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
}