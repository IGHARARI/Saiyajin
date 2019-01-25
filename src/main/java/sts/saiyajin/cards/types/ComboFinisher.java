package sts.saiyajin.cards.types;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import sts.saiyajin.powers.ComboPower;
import sts.saiyajin.powers.MomentumPower;
import sts.saiyajin.utils.PowerNames;
import sts.saiyajin.utils.PowersHelper;

public abstract class ComboFinisher extends SaiyanCard {

	public ComboFinisher(String id, String name, String img, int cost, String rawDescription, CardType type,
			CardColor color, CardRarity rarity, CardTarget target) {
		super(id, name, img, cost, rawDescription, type, color, rarity, target);
	}
	
	public void onComboUpdated() {};
	
	public void onComboRemoved() {};

	@Override
	public void triggerOnCardPlayed(AbstractCard cardPlayed) {
		super.triggerOnCardPlayed(cardPlayed);
		AbstractPlayer player = AbstractDungeon.player;
		if(cardPlayed instanceof ComboFinisher && player.hasPower(PowerNames.COMBO)){
			double comboAmount = (double) PowersHelper.getPlayerPowerAmount(ComboPower.POWER_ID);
			int consumeAmount = player.hasPower(MomentumPower.POWER_ID) ? (int) Math.ceil(comboAmount/2) : (int) comboAmount;
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(player, player, PowerNames.COMBO, consumeAmount));
		}
	}
}
