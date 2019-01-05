package sts.saiyajin.powers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import sts.saiyajin.cards.types.ComboFinisher;
import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.cards.utils.PowersHelper;
import sts.saiyajin.ui.PowerPaths;

public class ExtremeSpeedPower extends AbstractPower {

	public static final String POWER_ID = PowerNames.EXTREME_SPEED;
	private static final PowerStrings powerStrings =
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	final Logger logger = LogManager.getLogger(ExtremeSpeedPower.class);
	
	private int extraDraw = 0;
	public ExtremeSpeedPower(AbstractCreature owner) {
		this(owner, 0);
	}	
	public ExtremeSpeedPower(AbstractCreature owner, int extraDraw) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = 1;
		this.extraDraw = extraDraw;
		this.type = AbstractPower.PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.EXTREME_SPEED_B), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture(PowerPaths.EXTREME_SPEED), 0, 0, 48, 48);
		this.canGoNegative = false;
	}
	
	@Override
	public void onPlayCard(AbstractCard card, AbstractMonster m) {
		super.onPlayCard(card, m);
		if (card instanceof ComboFinisher) {
			int comboAmount = PowersHelper.getPlayerPowerAmount(ComboPower.POWER_ID);
			int draw = comboAmount/2 + extraDraw;
			if (draw > 0) {
				AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, draw));
			}
		}
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
}