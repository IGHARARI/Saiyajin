package sts.saiyajin.powers;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import sts.saiyajin.ui.PowerPaths;
import sts.saiyajin.utils.PowerNames;

public class OnVictoryAddRewardPower extends AbstractPower implements InvisiblePower {

	public static final String POWER_ID = PowerNames.REWARD;
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	public OnVictoryAddRewardPower() {
		this.name = NAME;
		this.ID = POWER_ID;
		this.amount = 1;
		this.type = AbstractPower.PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PowerPaths.SENZU_REVIVE_B), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(PowerPaths.SENZU_REVIVE), 0, 0, 48, 48);
		this.canGoNegative = false;
		this.owner = AbstractDungeon.player;
		this.updateDescription();
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
	
	@Override
	public void onVictory() {
		AbstractRoom room = AbstractDungeon.getCurrRoom();
		if (room!= null && !room.smoked) {
			AbstractDungeon.getCurrRoom().addRelicToRewards(RelicTier.COMMON);
		}
	}
}