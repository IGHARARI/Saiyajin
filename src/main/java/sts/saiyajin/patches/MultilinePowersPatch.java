package sts.saiyajin.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import sts.saiyajin.core.Saiyajin;

@SpirePatch(
		clz=AbstractCreature.class,
		method="renderPowerIcons"
)
public class MultilinePowersPatch
{
	private static int MAX_PER_ROW = 10;
	private static float offsetY = 0;
	private static int count = 0;
	private static boolean doingAmounts = false;

	@SuppressWarnings("unused")
	public static float getOffsetY()
	{
		return offsetY;
	}

	@SuppressWarnings("unused")
	public static void incrementOffsetY(float[] offsetX)
	{
		++count;
		if (count == MAX_PER_ROW) {
			count = 0;
			offsetY -= 38 * Settings.scale;
			offsetX[0] = ((doingAmounts ? 0 : 10) - 48) * Settings.scale;
		}
	}

	public static void Prefix(AbstractCreature __instance, SpriteBatch sb, float x, float y)
	{
		offsetY = 0;
		count = 0;
		doingAmounts = false;
		MAX_PER_ROW = Math.max((__instance.powers.size()+1)/2, 7);
	}

	@SpireInsertPatch(
			rloc=9
	)
	public static void Insert(AbstractCreature __instance, SpriteBatch sb, float x, float y)
	{
		offsetY = 0;
		count = 0;
		doingAmounts = true;
	}

	public static ExprEditor Instrument()
	{
		return new ExprEditor() {
			@Override
			public void edit(MethodCall m) throws CannotCompileException
			{
				if (m.getMethodName().equals("renderIcons")) {
					m.replace("{if ("+AbstractDungeon.class.getName()+".player instanceof "+Saiyajin.class.getName()+"){" +
							"$3 += " + MultilinePowersPatch.class.getName() + ".getOffsetY();" +
							"$proceed($$);" +
							"float[] offsetArr = new float[]{offset};" +
							MultilinePowersPatch.class.getName() + ".incrementOffsetY(offsetArr);" +
							"offset = offsetArr[0];" +
							"} else {$proceed($$);}}");
				} else if (m.getMethodName().equals("renderAmount")) {
					m.replace("{if ("+AbstractDungeon.class.getName()+".player instanceof "+Saiyajin.class.getName()+"){" +
							"$3 += " + MultilinePowersPatch.class.getName() + ".getOffsetY();" +
							"$proceed($$);" +
							"float[] offsetArr = new float[]{offset};" +
							MultilinePowersPatch.class.getName() + ".incrementOffsetY(offsetArr);" +
							"offset = offsetArr[0];" +
							"} else {$proceed($$);}}");
				}
			}
		};
	}
}