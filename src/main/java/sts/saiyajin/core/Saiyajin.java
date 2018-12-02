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
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.abstracts.CustomPlayer;
import sts.saiyajin.cards.attacks.Strike;
import sts.saiyajin.cards.utils.CardColors;
import sts.saiyajin.cards.utils.CardNames;
import sts.saiyajin.cards.utils.CardProperties;
import sts.saiyajin.cards.utils.RelicNames;
import sts.saiyajin.ui.Orb;



public class Saiyajin extends CustomPlayer {

	private static final int STARTING_HP = 40;
	private static final int MAX_HP = 40;
	private static final int STARTING_GOLD = 15;
	private static final int HAND_SIZE = 6;
	private static final int ASCENSION_MAX_HP_LOSS = 5;
	private static final int BASE_KI = 20;
	private int maxKi;
	
	final Logger logger = LogManager.getLogger(Saiyajin.class);
	
	
	public Saiyajin(String name){
		super(name, SaiyajinPlayer.SAIYAJIN, Orb.TEXTURES, Orb.VFX, Orb.LAYER_SPEED, null, null);
		
//		currentKi = BASE_KI;
		maxKi = BASE_KI;
		
	    this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
	    this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values


	    
	    int ENERGY_PER_TURN = 3; // how much energy you get every turn
	    String MARISA_SHOULDER_2 = "img/char/Marisa/shoulder2.png"; // shoulder2 / shoulder_1
	    String MARISA_SHOULDER_1 = "img/char/Marisa/shoulder1.png"; // shoulder1 / shoulder_2
	    String MARISA_CORPSE = "img/char/Marisa/fallen.png"; // dead corpse
	    String MARISA_SKELETON_ATLAS = "img/char/Marisa/MarisaModel_v02.atlas";// Marisa_v0 / MarisaModel_v02
	    String MARISA_SKELETON_JSON = "img/char/Marisa/MarisaModel_v02.json";
	    String MARISA_ANIMATION = "Idle";
	    
	    initializeClass(
	        null,
	        MARISA_SHOULDER_2, // required call to load textures and setup energy/loadout
	        MARISA_SHOULDER_1,
	        MARISA_CORPSE,
	        getLoadout(),
	        20.0F, -10.0F, 220.0F, 290.0F,
	        new EnergyManager(ENERGY_PER_TURN)
	    );

	    loadAnimation(MARISA_SKELETON_ATLAS, MARISA_SKELETON_JSON, 1.0F);
	    // if you're using modified versions of base game animations or made animations in spine make sure to include this bit and the following lines
	    AnimationState.TrackEntry e = this.state.setAnimation(0, MARISA_ANIMATION, true);
	    e.setTime(e.getEndTime() * MathUtils.random());
	    e.setTimeScale(1.0F);
	}
	
	public AbstractRelic getSaiyanBlood(){
		for(AbstractRelic relic : this.relics){
			if (relic.relicId.equals(RelicNames.SAIYAN_BLOOD))
				return relic;
		}
		logger.warn("Tried to find saiyan blood in relics but was unsuccessful");
		return null;
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
		
		return starterDeck;
	}
	@Override
	public ArrayList<String> getStartingRelics() {
		UnlockTracker.markRelicAsSeen(RelicNames.SAIYAN_BLOOD);

		ArrayList<String> starterRelics = new ArrayList<String>();
	    starterRelics.add(RelicNames.SAIYAN_BLOOD);
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
	        HAND_SIZE,
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
		return com.megacrit.cardcrawl.events.beyond.SpireHeart.DESCRIPTIONS[10];
	}
	@Override
	public Color getSlashAttackColor() {
		return CardProperties.SAIYAN_CARD_RENDER_COLOR;
	}
	@Override
	public AttackEffect[] getSpireHeartSlashEffect() {
	    return new AttackEffect[]{
	            AttackEffect.SLASH_HEAVY,
	            AttackEffect.FIRE,
	            AttackEffect.SLASH_DIAGONAL,
	            AttackEffect.SLASH_HEAVY,
	            AttackEffect.FIRE,
	            AttackEffect.SLASH_DIAGONAL
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
