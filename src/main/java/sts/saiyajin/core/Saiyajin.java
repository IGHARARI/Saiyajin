package sts.saiyajin.core;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.abstracts.CustomPlayer;
import sts.saiyajin.cards.attacks.Strike;
import sts.saiyajin.ui.Orb;
import sts.saiyajin.utils.CardColors;
import sts.saiyajin.utils.CardNames;
import sts.saiyajin.utils.CardProperties;
import sts.saiyajin.utils.RelicNames;



public class Saiyajin extends CustomPlayer {

	private static final int MAX_HP = 50;
	private static final int STARTING_HP = MAX_HP;
	private static final int STARTING_GOLD = 77;
	private static final int BASE_HAND_SIZE = 6;
	private static final int ASCENSION_MAX_HP_LOSS = 5;
	private static final int BASE_KI = 20;
	private int maxKi;
	
	final Logger logger = LogManager.getLogger(Saiyajin.class);
	
	
	public Saiyajin(String name){
		super(name, SaiyajinPlayer.SAIYAJIN, Orb.TEXTURES, Orb.VFX, Orb.LAYER_SPEED, null, null);
		
//		currentKi = BASE_KI;
		maxKi = BASE_KI;
		
	    this.dialogX = (this.drawX + 10.0F * Settings.scale); // set location for text bubbles
	    this.dialogY = (this.drawY + 280.0F * Settings.scale); // you can just copy these values


	    
	    int ENERGY_PER_TURN = 3; // how much energy you get every turn
	    String MARISA_SHOULDER_2 = "img/char/Marisa/shoulder2.png"; // shoulder2 / shoulder_1
	    String MARISA_SHOULDER_1 = "img/char/Marisa/shoulder1.png"; // shoulder1 / shoulder_2
	    String MARISA_CORPSE = "img/char/Marisa/fallen.png"; // dead corpse
//	    String skeletonAtlas = "img/char/Marisa/MarisaModel_v02.atlas";// Marisa_v0 / MarisaModel_v02
//	    String skeletonJSON = "img/char/Marisa/MarisaModel_v02.json";
	    String skeletonAtlas = "img/char/Goku/goku.atlas";// Marisa_v0 / MarisaModel_v02
	    String skeletonJSON = "img/char/Goku/goku.json";
	    String animation = "animtion0";
	    
	    initializeClass(
	        null,
	        MARISA_SHOULDER_2, // required call to load textures and setup energy/loadout
	        MARISA_SHOULDER_1,
	        MARISA_CORPSE,
	        getLoadout(),
	        20.0F, -10.0F, 220.0F, 290.0F,
	        new EnergyManager(ENERGY_PER_TURN)
	    );

	    loadAnimation(skeletonAtlas, skeletonJSON, 1.0F);
	    // if you're using modified versions of base game animations or made animations in spine make sure to include this bit and the following lines
	    AnimationState.TrackEntry e = this.state.setAnimation(0, animation, true);
	    e.setTime(e.getEndTime() * MathUtils.random());
	    e.setTimeScale(1.0F);
	}
	
	@Override
	public ArrayList<String> getStartingDeck() {
		ArrayList<String> starterDeck = new ArrayList<String>();
		starterDeck.add(CardNames.STRIKE);
		starterDeck.add(CardNames.STRIKE);
		starterDeck.add(CardNames.STRIKE);
		starterDeck.add(CardNames.STRIKE);
		starterDeck.add(CardNames.DEFEND);
		starterDeck.add(CardNames.DEFEND);
		starterDeck.add(CardNames.DEFEND);
		starterDeck.add(CardNames.DEFEND);
		starterDeck.add(CardNames.KI_BLAST);
		starterDeck.add(CardNames.KAIO_KEN);
		starterDeck.add(CardNames.RUTHLESS_BLOW);
		
		return starterDeck;
	}
	@Override
	public ArrayList<String> getStartingRelics() {
		UnlockTracker.markRelicAsSeen(RelicNames.SAIYAN_SOUL);
		UnlockTracker.markRelicAsSeen(RelicNames.SAIYAN_HEART);

		ArrayList<String> starterRelics = new ArrayList<String>();
	    starterRelics.add(RelicNames.SAIYAN_SOUL);
	    starterRelics.add(RelicNames.SAIYAN_HEART);
	    return starterRelics;
	}
	@Override
	public CharSelectInfo getLoadout() {
		UIStrings charDescription = CardCrawlGame.languagePack.getUIString("character_description");
	    return new CharSelectInfo(
	    	charDescription.TEXT[0],
	    	charDescription.EXTRA_TEXT[0],
	        STARTING_HP,
	        MAX_HP,
	        0,
	        STARTING_GOLD,
	        BASE_HAND_SIZE,
	        this,
	        getStartingRelics(),
	        getStartingDeck(),
	        false
	    );
	}
	@Override
	public String getTitle(PlayerClass playerClass) {
		UIStrings charDescription = CardCrawlGame.languagePack.getUIString("character_description");
		return charDescription.TEXT[0];
	}
	@Override
	public CardColor getCardColor() {
		return CardColors.SAIYAN_CARD_COLOR;
	}
	@Override
	public Color getCardRenderColor() {
		return CardProperties.SAIYAN_CARD_RENDER_COLOR;
	}
	@Override
	public AbstractCard getStartCardForEvent() {
		return new Strike();
	}
	@Override
	public Color getCardTrailColor() {
		return CardProperties.SAIYAN_CARD_RENDER_COLOR;
	}
	@Override
	public int getAscensionMaxHPLoss() {
		return ASCENSION_MAX_HP_LOSS;
	}
	@Override
	public BitmapFont getEnergyNumFont() {
		return FontHelper.energyNumFontRed;
	}
	@Override
	public void doCharSelectScreenSelectEffect() {
	    CardCrawlGame.sound.playA("ATTACK_MAGIC_BEAM_SHORT", MathUtils.random(-0.2F, 0.2F));
	    CardCrawlGame.screenShake.shake(
	        ScreenShake.ShakeIntensity.MED,
	        ScreenShake.ShakeDur.SHORT,
	        false
	    );
	}
	@Override
	public String getCustomModeCharacterButtonSoundKey() {
		return "ATTACK_MAGIC_BEAM_SHORT";
	}
	@Override
	public String getLocalizedCharacterName() {
		UIStrings charDescription = CardCrawlGame.languagePack.getUIString("character_name");
		return charDescription.TEXT[0];
	}
	@Override
	public AbstractPlayer newInstance() {
		return new Saiyajin(getLocalizedCharacterName());
	}
	@Override
	public String getSpireHeartText() {
		UIStrings charDescription = CardCrawlGame.languagePack.getUIString("HeartKillText");
		return charDescription.TEXT[0];
	}
	@Override
	public Color getSlashAttackColor() {
		return CardProperties.SAIYAN_CARD_RENDER_COLOR;
	}
	@Override
	public AttackEffect[] getSpireHeartSlashEffect() {
	    return new AttackEffect[]{
	            AttackEffect.BLUNT_LIGHT,
	            AttackEffect.BLUNT_LIGHT,
	            AttackEffect.BLUNT_LIGHT,
	            AttackEffect.BLUNT_LIGHT,
	            AttackEffect.BLUNT_HEAVY,
	            AttackEffect.SLASH_DIAGONAL,
	            AttackEffect.FIRE,
	        };
	}
	@Override
	public String getVampireText() {
	    return com.megacrit.cardcrawl.events.city.Vampires.DESCRIPTIONS[1];
	}

	public int getMaxKi() {
		return maxKi;
	}

	public void setMaxKi(int maxKi) {
		this.maxKi = maxKi;
	}
}
