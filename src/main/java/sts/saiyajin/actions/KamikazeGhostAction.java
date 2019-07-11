package sts.saiyajin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import sts.saiyajin.ui.vfx.GhostAttackBallEffect;

public class KamikazeGhostAction extends AbstractGameAction {
    
	int remainingGhosts;
	int baseDamage;
	int vulnAmount;
	
    public KamikazeGhostAction(int remainingGhosts, int baseDamage, int vulnAmount) {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.remainingGhosts = remainingGhosts;
        this.vulnAmount = vulnAmount;
        this.baseDamage = baseDamage;
    }
    
    @Override
    public void update() {
		AbstractMonster mon = AbstractDungeon.getRandomMonster();
		if (mon != null) {
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new GhostAttackBallEffect(source.hb.cX, source.hb.cY, mon.hb.cX, mon.hb.cY, 1), 0.1f));
			DamageInfo dinfo = new DamageInfo(source, baseDamage);
			dinfo.applyPowers(source, mon);
			AbstractDungeon.actionManager.addToBottom(new DamageAction(mon, dinfo, AbstractGameAction.AttackEffect.FIRE, true));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mon, source, new VulnerablePower(mon, vulnAmount, false), vulnAmount));
			if (remainingGhosts > 0) {
				remainingGhosts--;
				AbstractDungeon.actionManager.addToBottom(new KamikazeGhostAction(remainingGhosts, baseDamage, vulnAmount));
			}
		}
        isDone = true;
    }

}
