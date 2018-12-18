package sts.saiyajin.core;

import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.OnPowersModifiedSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostPowerApplySubscriber;
import sts.saiyajin.cards.attacks.BackAttack;
import sts.saiyajin.cards.attacks.BigBangAttack;
import sts.saiyajin.cards.attacks.ConcussiveBlow;
import sts.saiyajin.cards.attacks.DoubleMasenko;
import sts.saiyajin.cards.attacks.DrainingStrike;
import sts.saiyajin.cards.attacks.Flurry;
import sts.saiyajin.cards.attacks.GenkiDama;
import sts.saiyajin.cards.attacks.KameHameHa;
import sts.saiyajin.cards.attacks.KiBlast;
import sts.saiyajin.cards.attacks.Kienzan;
import sts.saiyajin.cards.attacks.MeteorDash;
import sts.saiyajin.cards.attacks.Strike;
import sts.saiyajin.cards.powers.BurningSoul;
import sts.saiyajin.cards.powers.GreatApeForm;
import sts.saiyajin.cards.powers.MonkeyTail;
import sts.saiyajin.cards.powers.SuperSaiyanForm;
import sts.saiyajin.cards.powers.TurtleShell;
import sts.saiyajin.cards.skills.CcCapsule;
import sts.saiyajin.cards.skills.Defend;
import sts.saiyajin.cards.skills.DragonBall;
import sts.saiyajin.cards.skills.DragonRadar;
import sts.saiyajin.cards.skills.Endure;
import sts.saiyajin.cards.skills.ExtremeSpeed;
import sts.saiyajin.cards.skills.Fly;
import sts.saiyajin.cards.skills.FullMoon;
import sts.saiyajin.cards.skills.InstantTransfer;
import sts.saiyajin.cards.skills.KaioKen;
import sts.saiyajin.cards.skills.KiExplosion;
import sts.saiyajin.cards.skills.LastResort;
import sts.saiyajin.cards.skills.Medicine;
import sts.saiyajin.cards.skills.PowerUp;
import sts.saiyajin.cards.skills.Quickening;
import sts.saiyajin.cards.skills.SaiyanHubris;
import sts.saiyajin.cards.skills.Scouter;
import sts.saiyajin.cards.skills.SenzuBean;
import sts.saiyajin.cards.skills.SolarFlare;
import sts.saiyajin.cards.skills.Taunt;
import sts.saiyajin.cards.skills.ThirstForFight;
import sts.saiyajin.cards.types.ComboFinisher;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.cards.utils.RelicNames;
import sts.saiyajin.powers.ComboPower;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.relics.SaiyanBlood;
import sts.saiyajin.ui.CharacterSelection;

@SpireInitializer
public class SaiyaMod implements 
	EditStringsSubscriber,
	EditCharactersSubscriber,
	EditCardsSubscriber,
	EditRelicsSubscriber,
	OnPowersModifiedSubscriber,
	PostBattleSubscriber,
	OnStartBattleSubscriber,
	PostPowerApplySubscriber,
	EditKeywordsSubscriber
	{

	public static final Logger logger = LogManager.getLogger(SaiyaMod.class);
	
    public SaiyaMod() {
    	BaseMod.subscribe(this);
    	Color someColor = CardHelper.getColor(100f, 10f, 30.0f);
    	String ATTACK_CC = "img/512/bg_attack_MRS_s.png";
    	String SKILL_CC = "img/512/bg_skill_MRS_s.png";
    	String POWER_CC = "img/512/bg_power_MRS_s.png";
    	String ENERGY_ORB_CC = "img/512/cardOrb.png";

    	String ATTACK_CC_PORTRAIT = "img/1024/bg_attack_MRS.png";
    	String SKILL_CC_PORTRAIT = "img/1024/bg_skill_MRS.png";
    	String POWER_CC_PORTRAIT = "img/1024/bg_power_MRS.png";
    	String ENERGY_ORB_CC_PORTRAIT = "img/1024/cardOrb.png";
    	
        BaseMod.addColor(
        		CardColors.SAIYAN_CARD_COLOR,
        		someColor,
        		someColor,
        		someColor,
        		someColor,
        		someColor,
        		someColor,
        		someColor,
                ATTACK_CC,
                SKILL_CC,
                POWER_CC,
                ENERGY_ORB_CC,
                ATTACK_CC_PORTRAIT,
                SKILL_CC_PORTRAIT,
                POWER_CC_PORTRAIT,
                ENERGY_ORB_CC_PORTRAIT
            );
        BaseMod.addColor(
        		CardColors.SAIYAN_EXTRA_CARD_COLOR,
        		someColor,
        		someColor,
        		someColor,
        		someColor,
        		someColor,
        		someColor,
        		someColor,
                ATTACK_CC,
                SKILL_CC,
                POWER_CC,
                ENERGY_ORB_CC,
                ATTACK_CC_PORTRAIT,
                SKILL_CC_PORTRAIT,
                POWER_CC_PORTRAIT,
                ENERGY_ORB_CC_PORTRAIT
            );
    }

    public static void initialize() {
        new SaiyaMod();
    }

    public void receiveEditCharacters() {
    	logger.info("Start editCharacters: ");

        logger.info("Adding character:  " + SaiyajinPlayer.SAIYAJIN.toString());
        BaseMod.addCharacter(
            new Saiyajin("Weakling"),
            CharacterSelection.SAIYAN_CHARACTER_BUTTON,
            CharacterSelection.SAIYAN_PORTRAIT,
            SaiyajinPlayer.SAIYAJIN
        );
        logger.info("done editing characters");
	}
    
    public void receiveEditCards() {
    	logger.info("Adding cards for the Saiyan");
    	/**
    	 * Starter cards
    	 */
        BaseMod.addCard(new Strike());
        UnlockTracker.unlockCard(CardNames.STRIKE);
        BaseMod.addCard(new Defend());
        UnlockTracker.unlockCard(CardNames.DEFEND);
        BaseMod.addCard(new KiBlast());
        UnlockTracker.unlockCard(CardNames.KI_BLAST);
        BaseMod.addCard(new KaioKen());
        UnlockTracker.unlockCard(CardNames.KAIO_KEN);
        
        /**
         * Common cards
         */
        BaseMod.addCard(new PowerUp());
        UnlockTracker.unlockCard(CardNames.POWER_UP);
        BaseMod.addCard(new KiExplosion());
        UnlockTracker.unlockCard(CardNames.KI_EXPLOSION);
        BaseMod.addCard(new BurningSoul());
        UnlockTracker.unlockCard(CardNames.BURNING_SOUL);
        BaseMod.addCard(new KameHameHa());
        UnlockTracker.unlockCard(CardNames.KAME_HAME_HA);
        BaseMod.addCard(new MeteorDash());
        UnlockTracker.unlockCard(CardNames.METEOR_DASH);
        BaseMod.addCard(new Flurry());
        UnlockTracker.unlockCard(CardNames.FLURRY);
        BaseMod.addCard(new DoubleMasenko());
        UnlockTracker.unlockCard(CardNames.DOUBLE_MASENKO);
        BaseMod.addCard(new DrainingStrike());
        UnlockTracker.unlockCard(CardNames.DRAINING_STRIKE);
        BaseMod.addCard(new ConcussiveBlow());
        UnlockTracker.unlockCard(CardNames.CONCUSSIVE_BLOW);
        BaseMod.addCard(new Quickening());
        UnlockTracker.unlockCard(CardNames.QUICKENING);
        BaseMod.addCard(new TurtleShell());
        UnlockTracker.unlockCard(CardNames.TURTLE_SHELL);
        BaseMod.addCard(new Scouter());
        UnlockTracker.unlockCard(CardNames.SCOUTER);
        BaseMod.addCard(new CcCapsule());
        UnlockTracker.unlockCard(CardNames.CC_CAPSULE);
        BaseMod.addCard(new ExtremeSpeed());
        UnlockTracker.unlockCard(CardNames.EXTREME_SPEED);
        BaseMod.addCard(new BigBangAttack());
        UnlockTracker.unlockCard(CardNames.BIG_BANG_ATTACK);
        BaseMod.addCard(new BackAttack());
        UnlockTracker.unlockCard(CardNames.BACK_ATTACK);
        BaseMod.addCard(new SaiyanHubris());
        UnlockTracker.unlockCard(CardNames.SAIYAN_HUBRIS);
        
        /**
         * Uncommon cards
         */
        BaseMod.addCard(new Endure());
        UnlockTracker.unlockCard(CardNames.ENDURE);
        BaseMod.addCard(new InstantTransfer());
        UnlockTracker.unlockCard(CardNames.TELEPORT);
        BaseMod.addCard(new ThirstForFight());
        UnlockTracker.unlockCard(CardNames.THIRST_FOR_FIGHT);
        BaseMod.addCard(new SuperSaiyanForm());
        UnlockTracker.unlockCard(CardNames.SUPER_SAIYAN_FORM);
        BaseMod.addCard(new LastResort());
        UnlockTracker.unlockCard(CardNames.LAST_RESORT);
        BaseMod.addCard(new Fly());
        UnlockTracker.unlockCard(CardNames.FLY);
        BaseMod.addCard(new Kienzan());
        UnlockTracker.unlockCard(CardNames.KIENZAN);
        BaseMod.addCard(new DragonRadar());
        UnlockTracker.unlockCard(CardNames.DRAGON_RADAR);

        /**
         * RARE CARDS
         */
        BaseMod.addCard(new Medicine());
        UnlockTracker.unlockCard(CardNames.MEDICINE);
        BaseMod.addCard(new GenkiDama());
        UnlockTracker.unlockCard(CardNames.GENKI_DAMA);
        BaseMod.addCard(new MonkeyTail());
        UnlockTracker.unlockCard(CardNames.MONKEY_TAIL);
        BaseMod.addCard(new Taunt());
        UnlockTracker.unlockCard(CardNames.TAUNT);
        BaseMod.addCard(new SolarFlare());
        UnlockTracker.unlockCard(CardNames.SOLAR_FLARE);
        BaseMod.addCard(new SenzuBean());
        UnlockTracker.unlockCard(CardNames.SENZU_BEAN);
        
        /**
         * UNOBTAINABLE CARDS
         */
        BaseMod.addCard(new GreatApeForm());
        UnlockTracker.unlockCard(CardNames.GREAT_APE_FORM);
        BaseMod.addCard(new FullMoon());
        UnlockTracker.unlockCard(CardNames.FULL_MOON);
        BaseMod.addCard(new DragonBall());
        UnlockTracker.unlockCard(CardNames.DRAGON_BALL);
        
    }
    
    public void receiveEditStrings() {

        String relicStrings, cardStrings, powerStrings, uiStrings;

        //	ASSUME THAT ("lang == eng");
          relicStrings = Gdx.files.internal("localization/saiyajin_relics.json")
              .readString(String.valueOf(StandardCharsets.UTF_8));
          BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);

          cardStrings = Gdx.files.internal("localization/saiyajin_cards.json")
              .readString(String.valueOf(StandardCharsets.UTF_8));
          BaseMod.loadCustomStrings(CardStrings.class, cardStrings);

          powerStrings = Gdx.files.internal("localization/saiyajin_powers.json")
              .readString(String.valueOf(StandardCharsets.UTF_8));
          BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);

          uiStrings = Gdx.files.internal("localization/saiyajin_UI.json")
        		  .readString(String.valueOf(StandardCharsets.UTF_8));
          BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
    }

	public void receiveEditRelics() {
	    BaseMod.addRelicToCustomPool(
	        new SaiyanBlood(),
	        CardColors.SAIYAN_CARD_COLOR
	    );
	}

	@Override
	public void receivePowersModified() {
		if (AbstractDungeon.player.hasRelic(RelicNames.SAIYAN_BLOOD)){
			((SaiyanBlood)AbstractDungeon.player.getRelic(RelicNames.SAIYAN_BLOOD)).powersWereModified();
		}
	}

	@Override
	public void receivePostBattle(AbstractRoom r) {
		if (AbstractDungeon.player.hasRelic(RelicNames.SAIYAN_BLOOD)){
			((SaiyanBlood)AbstractDungeon.player.getRelic(RelicNames.SAIYAN_BLOOD)).battleEnd();
		}
	}

	@Override
	public void receiveOnBattleStart(AbstractRoom r) {
		Saiyajin kakarot = (Saiyajin) AbstractDungeon.player;
		KiPower kiPower = new KiPower(kakarot, kakarot.getMaxKi());
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(kakarot, kakarot, kiPower, kakarot.getMaxKi()));
		
	}

	@Override
	public void receivePostPowerApplySubscriber(AbstractPower power, AbstractCreature arg1, AbstractCreature arg2) {
		if (power instanceof ComboPower){
			logger.info("ON APPLY COMBO AMOUNT: " + power.amount);
			for (AbstractCard c : AbstractDungeon.player.hand.group) {
				if (c instanceof ComboFinisher) ((ComboFinisher) c).updatedComboChain();
			}
			for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
				if (c instanceof ComboFinisher) ((ComboFinisher) c).updatedComboChain();
			}
			for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
				if (c instanceof ComboFinisher) ((ComboFinisher) c).updatedComboChain();
			}
		}
		
	}

	@Override
	public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal("localization/saiyajin_keywords.json").readString(StandardCharsets.UTF_8.name());
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
	}

}