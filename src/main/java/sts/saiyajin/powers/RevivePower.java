package sts.saiyajin.powers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.InvinciblePower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import sts.saiyajin.actions.DecreaseInvincibleAmountAction;
import sts.saiyajin.ui.PowerPaths;
import sts.saiyajin.utils.PowerNames;

public class RevivePower extends AbstractPower {

	public static final String POWER_ID = PowerNames.REVIVE;
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public static final Logger logger = LogManager.getLogger(RevivePower.class);
	
	public  int strengthBuff;

	public RevivePower(AbstractMonster owner, int strBuff) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = -1;
		this.type = AbstractPower.PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.SENZU_REVIVE_B), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.SENZU_REVIVE), 0, 0, 48, 48);
		this.canGoNegative = false;
		strengthBuff = strBuff;
		updateDescription();
	}
	
	@Override
	public int onAttacked(DamageInfo info, int damageAmount) {
		AbstractMonster monster = (AbstractMonster) owner;
		if (!monster.hasPower(PowerNames.REVIVE) || monster.currentHealth > damageAmount) return super.onAttacked(info, damageAmount);
		int invincibleAmount = (monster.maxHealth / 3 ) +1;
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monster, monster, new StrengthPower(monster, strengthBuff), strengthBuff));
		AbstractDungeon.actionManager.addToTop(new DecreaseInvincibleAmountAction(monster));
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monster, monster, new InvinciblePower(monster, invincibleAmount), invincibleAmount));
		AbstractDungeon.actionManager.addToTop(new StunMonsterAction(monster, monster, 1));
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monster, monster, new CantRevivePower(monster, 1), 1));
		AbstractDungeon.actionManager.addToTop(new HealAction(monster, monster, monster.maxHealth));
		for(AbstractPower power : monster.powers){
			AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(monster, monster, power));
		}
		int dmgReturn = 0;
		if (monster.currentHealth > 1){
			dmgReturn = monster.currentHealth -1;
		}
		AbstractDungeon.actionManager.addToTop(new WaitAction(1f));
		return dmgReturn;
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
}