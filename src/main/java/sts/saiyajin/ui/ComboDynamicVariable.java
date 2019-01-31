package sts.saiyajin.ui;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.DynamicVariable;
import sts.saiyajin.powers.ComboPower;
import sts.saiyajin.utils.PowersHelper;

public class ComboDynamicVariable extends DynamicVariable {

	final static String COMBO_VARIABLE_KEY = "Saiyan:C";
	
	@Override
	public String key() {
		return COMBO_VARIABLE_KEY;
	}

	@Override
	public boolean isModified(AbstractCard card) {
		return PowersHelper.getPlayerPowerAmount(ComboPower.POWER_ID) > 0;
	}

	@Override
	public int value(AbstractCard card) {
		return PowersHelper.getPlayerPowerAmount(ComboPower.POWER_ID);
	}

	@Override
	public int baseValue(AbstractCard card) {
		return PowersHelper.getPlayerPowerAmount(ComboPower.POWER_ID);
	}

	@Override
	public boolean upgraded(AbstractCard card) {
		return PowersHelper.getPlayerPowerAmount(ComboPower.POWER_ID) > 0;
	}
}
