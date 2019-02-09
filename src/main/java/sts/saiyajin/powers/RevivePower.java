package sts.saiyajin.powers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.InvinciblePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.Boot;

import sts.saiyajin.actions.DecreaseInvincibleAmountAction;
import sts.saiyajin.actions.ResetMonsterAttackPatternAction;
import sts.saiyajin.ui.PowerPaths;
import sts.saiyajin.utils.PowerNames;

public class RevivePower extends AbstractPower {

	public static final String POWER_ID = PowerNames.REVIVE;
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public static final Logger logger = LogManager.getLogger(RevivePower.class);
	
	public int strengthBuff;
	private boolean monsterRevived;

	public RevivePower(AbstractMonster owner, int strBuff) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = -1;
		this.type = AbstractPower.PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PowerPaths.SENZU_REVIVE_B), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PowerPaths.SENZU_REVIVE), 0, 0, 48, 48);
		this.canGoNegative = false;
		strengthBuff = strBuff;
		monsterRevived = false;
		updateDescription();
	}
	
	@Override
	public int onAttacked(DamageInfo info, int damageAmount) {
		AbstractMonster monster = (AbstractMonster) owner;
		if (monsterRevived) return 0; //prevent further damage until all actions resolve
		if (shouldApplyNormalDamage(monster, damageAmount)) return super.onAttacked(info, damageAmount);
		
		monsterRevived = true;
		int invincibleAmount = (monster.maxHealth / 3 ) +1;
		AbstractDungeon.actionManager.addToBottom(new WaitAction(1f));
		for(AbstractPower power : monster.powers){
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(monster, monster, power));
		}
		AbstractDungeon.actionManager.addToBottom(new HealAction(monster, monster, monster.maxHealth));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, monster, new CantRevivePower(monster, 1), 1));
		AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(monster, monster, 1));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, monster, new InvinciblePower(monster, invincibleAmount), invincibleAmount));
		AbstractDungeon.actionManager.addToBottom(new DecreaseInvincibleAmountAction(monster));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, monster, new StrengthPower(monster, strengthBuff), strengthBuff));
		AbstractDungeon.actionManager.addToBottom(new ResetMonsterAttackPatternAction(monster));
		monster.usePreBattleAction();
		monster.useUniversalPreBattleAction();
		int dmgReturn = 0;
		AbstractPlayer p = AbstractDungeon.player;
		
		if (monster.currentHealth > 1){
			dmgReturn = monster.currentHealth -1;
		}
		if (monster.currentHealth <= 5 && p != null && p.hasRelic(Boot.ID)) {
			dmgReturn = 0;
			//I have to return 0 if the player has the boot or the stupid boot will raise the value to 5 and kill the monster.
		}
		return dmgReturn;
	}
	
	private boolean shouldApplyNormalDamage(AbstractMonster monster, int damageAmount) {
		if (!monster.hasPower(PowerNames.REVIVE)) return true;
		AbstractPlayer p = AbstractDungeon.player;
		boolean isMonsterDying = (monster.currentHealth <= damageAmount) || 
			(monster.currentHealth <= 5 && p != null && p.hasRelic(Boot.ID) && damageAmount > 0);
		return !isMonsterDying;
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
}