package sts.saiyajin.powers;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FlightPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import sts.saiyajin.utils.PowerNames;

public class PlayerFlightPower extends AbstractPower {

    public static final String POWER_ID = PowerNames.PLAYER_FLIGHT;
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = PlayerFlightPower.powerStrings.NAME;
        DESCRIPTIONS = PlayerFlightPower.powerStrings.DESCRIPTIONS;
    }
    
    public PlayerFlightPower(final AbstractCreature owner, final int amount) {
        this.name = FlightPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("flight");
        this.priority = 50;
    }
    
    @Override
    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_FLIGHT", 0.05f);
    }
    
    @Override
    public void updateDescription() {
        this.description = PlayerFlightPower.DESCRIPTIONS[0];
    }
    
    @Override
    public float atDamageFinalReceive(final float damage, final DamageInfo.DamageType type) {
        return this.calculateDamageTakenAmount(damage, type);
    }
    
    private float calculateDamageTakenAmount(final float damage, final DamageInfo.DamageType type) {
        if (type != DamageInfo.DamageType.HP_LOSS && type != DamageInfo.DamageType.THORNS) {
            return damage / 2.0f;
        }
        return damage;
    }
    
    @Override
    public int onAttacked(final DamageInfo info, final int damageAmount) {
        final Boolean willLive = this.calculateDamageTakenAmount(damageAmount, info.type) < this.owner.currentHealth;
        if (info.owner != null && info.output > 0 && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && willLive) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
        return damageAmount;
    }
    
    @Override
    public void onRemove() {
    	AbstractPlayer player = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new WeakPower(player, 1, true)));
    }

}