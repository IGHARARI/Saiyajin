package sts.saiyajin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ResetMonsterAttackPatternAction extends AbstractGameAction {
    
	private AbstractMonster monster;
	
    public ResetMonsterAttackPatternAction(AbstractMonster monster) {
        this.setValues(monster, monster);
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.monster = monster;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST) {
    		if (!monster.moveHistory.isEmpty()) {
    			monster.nextMove = monster.moveHistory.get(0);
    		}
        }
        this.tickDuration();
    }

}
