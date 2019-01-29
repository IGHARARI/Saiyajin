package sts.saiyajin.cards.types;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;

public abstract class ComboFinisher extends SaiyanCard {

	public ComboFinisher(String id, String name, String img, int cost, String rawDescription, CardType type,
			CardColor color, CardRarity rarity, CardTarget target) {
		super(id, name, img, cost, rawDescription, type, color, rarity, target);
	}
	
	public void onComboUpdated() {};
	
	public void onComboRemoved() {};

	public abstract void finisher(AbstractPlayer player, AbstractCreature monster, int comboStacks);

}
