package sts.saiyajin.cards.types;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import sts.saiyajin.cards.utils.DescriptionStrings;
import sts.saiyajin.cards.utils.PowerNames;
import sts.saiyajin.cards.utils.PowersHelper;

public abstract class KiCard extends CustomCard {

	public KiCard(String id, String name, String img, int cost, String rawDescription, CardType type,
			CardColor color, CardRarity rarity, CardTarget target) {
		super(id, name, img, cost, rawDescription, type, color, rarity, target);
	}
	
	protected int kiRequired = 0;
	
	@Override
	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		boolean canUse = super.canUse(p, m);
		if (!canUse) return false;
		if (AbstractDungeon.player.hasPower(PowerNames.KI)){
			int kiPower = PowersHelper.getPlayerPowerAmount(PowerNames.KI);
			if (kiPower >= kiRequired) return true;
		}
		this.cantUseMessage = DescriptionStrings.KI_CARD_CANT_USE;
		return false;
	}

}
