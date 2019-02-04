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
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
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
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnPowersModifiedSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostPowerApplySubscriber;
import sts.saiyajin.cards.attacks.AugmentedBlast;
import sts.saiyajin.cards.attacks.BackAttack;
import sts.saiyajin.cards.attacks.BigBangAttack;
import sts.saiyajin.cards.attacks.ConcussiveBlow;
import sts.saiyajin.cards.attacks.DoubleMasenko;
import sts.saiyajin.cards.attacks.DragonFist;
import sts.saiyajin.cards.attacks.DrainingStrike;
import sts.saiyajin.cards.attacks.ExpandingShockwave;
import sts.saiyajin.cards.attacks.FinalFlash;
import sts.saiyajin.cards.attacks.Flurry;
import sts.saiyajin.cards.attacks.GenkiDama;
import sts.saiyajin.cards.attacks.KameHameHa;
import sts.saiyajin.cards.attacks.KiBlast;
import sts.saiyajin.cards.attacks.KiStrike;
import sts.saiyajin.cards.attacks.Kienzan;
import sts.saiyajin.cards.attacks.Makankosappo;
import sts.saiyajin.cards.attacks.MeteorDash;
import sts.saiyajin.cards.attacks.PowerPole;
import sts.saiyajin.cards.attacks.ReverberatingForce;
import sts.saiyajin.cards.attacks.RuthlessBlow;
import sts.saiyajin.cards.attacks.SpiritSword;
import sts.saiyajin.cards.attacks.Strike;
import sts.saiyajin.cards.attacks.TransfigurationBeam;
import sts.saiyajin.cards.powers.BurningSoul;
import sts.saiyajin.cards.powers.Furor;
import sts.saiyajin.cards.powers.GreatApeForm;
import sts.saiyajin.cards.powers.HyperbolicTimeChamber;
import sts.saiyajin.cards.powers.MajinSeal;
import sts.saiyajin.cards.powers.Momentum;
import sts.saiyajin.cards.powers.MonkeyTail;
import sts.saiyajin.cards.powers.PlotArmor;
import sts.saiyajin.cards.powers.PowerStance;
import sts.saiyajin.cards.powers.SoulSpice;
import sts.saiyajin.cards.powers.SuperSaiyan3Form;
import sts.saiyajin.cards.powers.SuperSaiyanForm;
import sts.saiyajin.cards.powers.TurtleShell;
import sts.saiyajin.cards.powers.WarmUp;
import sts.saiyajin.cards.skills.Afterimage;
import sts.saiyajin.cards.skills.CcCapsule;
import sts.saiyajin.cards.skills.Defend;
import sts.saiyajin.cards.skills.DragonBall;
import sts.saiyajin.cards.skills.DragonRadar;
import sts.saiyajin.cards.skills.Endure;
import sts.saiyajin.cards.skills.Expend;
import sts.saiyajin.cards.skills.ExtremeSpeed;
import sts.saiyajin.cards.skills.Fly;
import sts.saiyajin.cards.skills.FullMoon;
import sts.saiyajin.cards.skills.Honing;
import sts.saiyajin.cards.skills.InstantTransfer;
import sts.saiyajin.cards.skills.KaioKen;
import sts.saiyajin.cards.skills.KiExplosion;
import sts.saiyajin.cards.skills.LastResort;
import sts.saiyajin.cards.skills.Medicine;
import sts.saiyajin.cards.skills.MindBoom;
import sts.saiyajin.cards.skills.Overexert;
import sts.saiyajin.cards.skills.Pacing;
import sts.saiyajin.cards.skills.Planning;
import sts.saiyajin.cards.skills.PowerUp;
import sts.saiyajin.cards.skills.PressOn;
import sts.saiyajin.cards.skills.Quickening;
import sts.saiyajin.cards.skills.Rekindle;
import sts.saiyajin.cards.skills.RemoveWeights;
import sts.saiyajin.cards.skills.Reset;
import sts.saiyajin.cards.skills.SaiyanHubris;
import sts.saiyajin.cards.skills.Scouter;
import sts.saiyajin.cards.skills.SenzuBean;
import sts.saiyajin.cards.skills.SolarFlare;
import sts.saiyajin.cards.skills.Taunt;
import sts.saiyajin.cards.skills.TheBomb;
import sts.saiyajin.cards.skills.ThirstForFight;
import sts.saiyajin.cards.skills.Twize;
import sts.saiyajin.cards.skills.UltraInstinct;
import sts.saiyajin.cards.special.KiBurn;
import sts.saiyajin.cards.special.Training;
import sts.saiyajin.cards.types.ComboFinisher;
import sts.saiyajin.cards.types.SaiyanCard;
import sts.saiyajin.powers.ComboPower;
import sts.saiyajin.powers.KiPower;
import sts.saiyajin.relics.SaiyanHeart;
import sts.saiyajin.relics.SaiyanSoul;
import sts.saiyajin.ui.CharacterSelection;
import sts.saiyajin.ui.ComboDynamicVariable;
import sts.saiyajin.ui.KiReqDynamicVariable;
import sts.saiyajin.ui.KiVarDynamicVariable;
import sts.saiyajin.ui.MiscDynamicVariable;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;
import sts.saiyajin.utils.PowerNames;
import sts.saiyajin.utils.RelicNames;

@SpireInitializer
public class SaiyaMod implements 
	EditStringsSubscriber,
	EditCharactersSubscriber,
	EditCardsSubscriber,
	EditRelicsSubscriber,
	OnStartBattleSubscriber,
	PostPowerApplySubscriber,
	EditKeywordsSubscriber,
	PostBattleSubscriber,
	OnPowersModifiedSubscriber,
	OnCardUseSubscriber
//	RenderSubscriber,
//	PostInitializeSubscriber
	{
	
	
//	private static KiEnergyCounter kiCounter;
	
	public static final String MOD_ID = "Saiyan:";
	public static final Logger logger = LogManager.getLogger(SaiyaMod.class);
    public SaiyaMod() {

    	BaseMod.subscribe(this);
    	Color someColor = CardHelper.getColor(100f, 10f, 30.0f);
    	String ATTACK_CC = "img/512/bg_attack_saiyan_s.png";
    	String SKILL_CC = "img/512/bg_skill_saiyan_s.png";
    	String POWER_CC = "img/512/bg_power_saiyan_s.png";
    	String ENERGY_ORB_CC = "img/UI/cardOrb/cardOrb.png";

    	String ATTACK_CC_PORTRAIT = "img/1024/bg_attack_saiyan.png";
    	String SKILL_CC_PORTRAIT = "img/1024/bg_skill_saiyan.png";
    	String POWER_CC_PORTRAIT = "img/1024/bg_power_saiyan.png";
    	String ENERGY_ORB_CC_PORTRAIT = "img/UI/cardOrb/cardOrb_p.png";

    	String CARD_DESCRIPTION_ORB = "img/UI/cardOrb/descriptionOrb.png";
    	
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
                ENERGY_ORB_CC_PORTRAIT,
                CARD_DESCRIPTION_ORB
            );
//        BaseMod.addColor(
//        		CardColors.SAIYAN_EXTRA_CARD_COLOR,
//        		someColor,
//        		someColor,
//        		someColor,
//        		someColor,
//        		someColor,
//        		someColor,
//        		someColor,
//                ATTACK_CC,
//                SKILL_CC,
//                POWER_CC,
//                ENERGY_ORB_CC,
//                ATTACK_CC_PORTRAIT,
//                SKILL_CC_PORTRAIT,
//                POWER_CC_PORTRAIT,
//                ENERGY_ORB_CC_PORTRAIT
//            );
    }

    public static void initialize() {
        new SaiyaMod();
    }

//    @Override
//    public void receivePostInitialize() {
//        // Mod badge
////        Texture badgeTexture = new Texture(Gdx.files.internal("badge/YohaneModBadge.png"));
////        ModPanel settingsPanel = new ModPanel();
////        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
//    	kiCounter = new KiEnergyCounter(TexturesHelper.getTexture("img/custom_orb_large.png"));
//    }
//    
    public void receiveEditCharacters() {
        logger.info("Adding character:  " + SaiyajinPlayer.SAIYAJIN.toString());
        BaseMod.addCharacter(
            new Saiyajin("Weakling"),
            CharacterSelection.SAIYAN_CHARACTER_BUTTON,
            CharacterSelection.SAIYAN_PORTRAIT,
            SaiyajinPlayer.SAIYAJIN
        );
	}
    
    public void receiveEditCards() {
    	/**
    	 * Add dynamic variables
    	 */
    	logger.info("Adding Saiyan Dynamic variables");
    	BaseMod.addDynamicVariable(new KiVarDynamicVariable());
    	BaseMod.addDynamicVariable(new KiReqDynamicVariable());
    	BaseMod.addDynamicVariable(new MiscDynamicVariable());
    	BaseMod.addDynamicVariable(new ComboDynamicVariable());
    	
    	
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
        BaseMod.addCard(new RuthlessBlow());
        UnlockTracker.unlockCard(CardNames.RUTHLESS_BLOW);
        
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
        BaseMod.addCard(new BigBangAttack());
        UnlockTracker.unlockCard(CardNames.BIG_BANG_ATTACK);
        BaseMod.addCard(new BackAttack());
        UnlockTracker.unlockCard(CardNames.BACK_ATTACK);
        BaseMod.addCard(new SaiyanHubris());
        UnlockTracker.unlockCard(CardNames.SAIYAN_HUBRIS);
        BaseMod.addCard(new DragonFist());
        UnlockTracker.unlockCard(CardNames.DRAGON_FIST);
        BaseMod.addCard(new Furor());
        UnlockTracker.unlockCard(CardNames.FUROR);
        BaseMod.addCard(new Planning());
        UnlockTracker.unlockCard(CardNames.PLANNING);
        BaseMod.addCard(new ExpandingShockwave());
        UnlockTracker.unlockCard(CardNames.EXPANDING_SHOCKWAVE);
        BaseMod.addCard(new PlotArmor());
        UnlockTracker.unlockCard(CardNames.PLOT_ARMOR);
        BaseMod.addCard(new RemoveWeights());
        UnlockTracker.unlockCard(CardNames.REMOVE_WEIGHTS);
        
        /**
         * Uncommon cards
         */
        BaseMod.addCard(new Makankosappo());
        UnlockTracker.unlockCard(CardNames.MAKANKOSAPPO);
        BaseMod.addCard(new ExtremeSpeed());
        UnlockTracker.unlockCard(CardNames.EXTREME_SPEED);
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
        BaseMod.addCard(new Honing());
        UnlockTracker.unlockCard(CardNames.HONING);
        BaseMod.addCard(new SpiritSword());
        UnlockTracker.unlockCard(CardNames.SPIRIT_SWORD);
        BaseMod.addCard(new Overexert());
        UnlockTracker.unlockCard(CardNames.OVEREXERT);
        BaseMod.addCard(new PowerStance());
        UnlockTracker.unlockCard(CardNames.POWER_STANCE);
        BaseMod.addCard(new Afterimage());
        UnlockTracker.unlockCard(CardNames.AFTERIMAGE);
        BaseMod.addCard(new PowerPole());
        UnlockTracker.unlockCard(CardNames.POWER_POLE);
        BaseMod.addCard(new UltraInstinct());
        UnlockTracker.unlockCard(CardNames.ULTRA_INSTINCT);
        BaseMod.addCard(new PressOn());
        UnlockTracker.unlockCard(CardNames.PRESS_ON);
        BaseMod.addCard(new Taunt());
        UnlockTracker.unlockCard(CardNames.TAUNT);
        BaseMod.addCard(new SoulSpice());
        UnlockTracker.unlockCard(CardNames.SOUL_SPICE);
        BaseMod.addCard(new ReverberatingForce());
        UnlockTracker.unlockCard(CardNames.REVERBERATING_FORCE);
        BaseMod.addCard(new KiStrike());
        UnlockTracker.unlockCard(CardNames.KI_STRIKE);
        BaseMod.addCard(new Pacing());
        UnlockTracker.unlockCard(CardNames.PACING);
        BaseMod.addCard(new Reset());
        UnlockTracker.unlockCard(CardNames.RESET);
        BaseMod.addCard(new Twize());
        UnlockTracker.unlockCard(CardNames.TWIZE);
        BaseMod.addCard(new FinalFlash());
        UnlockTracker.unlockCard(CardNames.FINAL_FLASH);
        BaseMod.addCard(new AugmentedBlast());
        UnlockTracker.unlockCard(CardNames.AUGMENTED_BLAST);
        BaseMod.addCard(new WarmUp());
        UnlockTracker.unlockCard(CardNames.WARM_UP);
        BaseMod.addCard(new Expend());
        UnlockTracker.unlockCard(CardNames.EXPEND);

        /**
         * RARE CARDS
         */
        BaseMod.addCard(new SuperSaiyan3Form());
        UnlockTracker.unlockCard(CardNames.SUPER_SAIYAN_THREE_FORM);
        BaseMod.addCard(new HyperbolicTimeChamber());
        UnlockTracker.unlockCard(CardNames.HYPER_TIME_CHAMBER);
        BaseMod.addCard(new Medicine());
        UnlockTracker.unlockCard(CardNames.MEDICINE);
        BaseMod.addCard(new GenkiDama());
        UnlockTracker.unlockCard(CardNames.GENKI_DAMA);
        BaseMod.addCard(new MonkeyTail());
        UnlockTracker.unlockCard(CardNames.MONKEY_TAIL);
        BaseMod.addCard(new SolarFlare());
        UnlockTracker.unlockCard(CardNames.SOLAR_FLARE);
        BaseMod.addCard(new SenzuBean());
        UnlockTracker.unlockCard(CardNames.SENZU_BEAN);
        BaseMod.addCard(new MajinSeal());
        UnlockTracker.unlockCard(CardNames.MAJIN_SEAL);
        BaseMod.addCard(new Rekindle());
        UnlockTracker.unlockCard(CardNames.REKINDLE);
        BaseMod.addCard(new TheBomb());
        UnlockTracker.unlockCard(CardNames.THE_BOMB);
        BaseMod.addCard(new TransfigurationBeam());
        UnlockTracker.unlockCard(CardNames.TRANSFIGURATION_BEAM);
        BaseMod.addCard(new Momentum());
        UnlockTracker.unlockCard(CardNames.MOMENTUM);
        BaseMod.addCard(new MindBoom());
        UnlockTracker.unlockCard(CardNames.MIND_BOOM);
        
        /**
         * UNOBTAINABLE CARDS
         */
        BaseMod.addCard(new GreatApeForm());
        UnlockTracker.unlockCard(CardNames.GREAT_APE_FORM);
        BaseMod.addCard(new FullMoon());
        UnlockTracker.unlockCard(CardNames.FULL_MOON);
        BaseMod.addCard(new DragonBall());
        UnlockTracker.unlockCard(CardNames.DRAGON_BALL);
        BaseMod.addCard(new Training());
        UnlockTracker.unlockCard(CardNames.TRAINING);
        BaseMod.addCard(new KiBurn());
        UnlockTracker.unlockCard(CardNames.KI_BURN);
        
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
		BaseMod.addRelicToCustomPool(new SaiyanHeart(), CardColors.SAIYAN_CARD_COLOR);
	    BaseMod.addRelicToCustomPool(new SaiyanSoul(), CardColors.SAIYAN_CARD_COLOR);
	}

	@Override
	public void receivePowersModified() {
		if (AbstractDungeon.player.hasRelic(RelicNames.SAIYAN_HEART)){
			((SaiyanHeart)AbstractDungeon.player.getRelic(RelicNames.SAIYAN_HEART)).powersWereModified();
		}
	}

	@Override
	public void receivePostBattle(AbstractRoom r) {
		if (AbstractDungeon.player.hasRelic(RelicNames.SAIYAN_HEART)){
			((SaiyanHeart)AbstractDungeon.player.getRelic(RelicNames.SAIYAN_HEART)).battleEnd();
		}
	}

	@Override
	public void receiveOnBattleStart(AbstractRoom r) {
		if (AbstractDungeon.player instanceof Saiyajin) {
			Saiyajin kakarot = (Saiyajin) AbstractDungeon.player;
			KiPower kiPower = new KiPower(kakarot, kakarot.getMaxKi());
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(kakarot, kakarot, kiPower, kakarot.getMaxKi()));
		}
	}

	@Override
	public void receivePostPowerApplySubscriber(AbstractPower power, AbstractCreature arg1, AbstractCreature arg2) {
		if (power instanceof ComboPower){
			for (AbstractCard c : AbstractDungeon.player.hand.group) {
				if (c instanceof ComboFinisher) ((ComboFinisher) c).onComboUpdated();
			}
			for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
				if (c instanceof ComboFinisher) ((ComboFinisher) c).onComboUpdated();
			}
			for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
				if (c instanceof ComboFinisher) ((ComboFinisher) c).onComboUpdated();
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

	@Override
	public void receiveCardUsed(AbstractCard cardPlayed) {
		if (cardPlayed instanceof SaiyanCard) {
			SaiyanCard saiyanCard = (SaiyanCard)cardPlayed;
			if(isCardConsumingKi(saiyanCard)){
				AbstractPlayer player = AbstractDungeon.player;
				AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(player, player, KiPower.POWER_ID, saiyanCard.kiRequired));
			}
		}
	}
	
	private boolean isCardConsumingKi(SaiyanCard cardPlayed) {
		return (cardPlayed.kiRequired > 0 && !cardPlayed.freeToPlayOnce && !AbstractDungeon.player.hasPower(PowerNames.PRESS_ON));
	}
	
//    @Override
//    public void receiveRender(SpriteBatch sb) {
//        if (AbstractDungeon.player != null && CardCrawlGame.dungeon != null && AbstractDungeon.player.hasPower(KiPower.POWER_ID) && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
//            if (!AbstractDungeon.isScreenUp) {
//            	kiCounter.update();
//                kiCounter.render(sb);
//            }
//        }
//    }

}