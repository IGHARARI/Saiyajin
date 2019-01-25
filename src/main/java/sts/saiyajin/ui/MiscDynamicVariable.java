package sts.saiyajin.ui;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.DynamicVariable;

public class MiscDynamicVariable extends DynamicVariable {

	final static String MISC_VARIABLE_KEY = "Saiyan:Misc";
	
	@Override
	public String key() {
		return MISC_VARIABLE_KEY;
	}

	@Override
	public boolean isModified(AbstractCard card) {
		return false;
	}

	@Override
	public int value(AbstractCard card) {
		return card.misc;
	}

	@Override
	public int baseValue(AbstractCard card) {
		return card.misc;
	}

	@Override
	public boolean upgraded(AbstractCard card) {
		return false;
	}
}
