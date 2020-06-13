package SpaceShooter;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.EffectBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.Color;

public class Hud {
  private ViewPort guiViewPort;
  private AssetManager assetManager;
  private InputManager inputManager;
  private AudioRenderer audioRenderer;

  private Nifty nifty;

  private int score = 0;
  private TextRenderer scoreText;

  Hud(ViewPort _guiViewPort, AssetManager _assetManager, InputManager _inputManager, AudioRenderer _audioRenderer) {
    guiViewPort = _guiViewPort;
    assetManager = _assetManager;
    inputManager = _inputManager;
    audioRenderer = _audioRenderer;

    NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
    nifty = niftyDisplay.getNifty();
    guiViewPort.addProcessor(niftyDisplay);

    nifty.addScreen("main", load());
    nifty.gotoScreen("main");

    scoreText = nifty.getScreen("main").findElementById("score").getRenderer(TextRenderer.class);
  }

  public int addScore(int val) {
    score += val;
    scoreText.setText("  Score: " + score);
    return score;
  }

  private Screen load() {
    Screen screen = new ScreenBuilder("main") {{
      layer(new LayerBuilder("layer") {{
        backgroundColor(new Color(Color.BLACK, 0));
        childLayout(ChildLayoutType.Absolute);
        text(new TextBuilder("score") {{
          x("60px");
          y("40px");
          textVAlignTop();
          textHAlign(Align.Left);
          font("aurulent-sans-16.fnt");
          text("  Score: 0");
          color(Color.BLACK);
          backgroundColor(new Color(Color.WHITE, .5f));
          height("35px");
          width("140px");
          onActiveEffect(new EffectBuilder("textSize"){{
            effectParameter("startSize", "2");
          }});
        }});
      }});
    }}.build(nifty);

    return screen;
  }
}