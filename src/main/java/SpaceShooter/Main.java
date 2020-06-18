package SpaceShooter;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.system.AppSettings;

public class Main extends SimpleApplication {
    private Hud hud;
    private Ship ship;
    private Asteroid asteroid;
    private BulletAppState bulletState;
    private AudioNode audio_shoot;

    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        assetManager.registerLocator("assets/", FileLocator.class);

        bulletState = new BulletAppState();
        stateManager.attach(bulletState);

        loadAudio();

        hud = new Hud(guiViewPort, assetManager, inputManager, audioRenderer);
        ship = new Ship(rootNode, assetManager, inputManager, cam);
        asteroid = new Asteroid(rootNode, assetManager, 1, 1000);
    }

    @Override
    public void simpleUpdate(float tpf) {
        ship.update(tpf);
        asteroid.update(tpf);
        CollisionDetector.detect(ship, asteroid, hud);
    }

    private void loadAudio() {
        audio_shoot = new AudioNode(assetManager, "Sound/Environment/bg.ogg", DataType.Stream);
        audio_shoot.setPositional(false);
        audio_shoot.setLooping(true);
        audio_shoot.setVolume(0.5f);
        rootNode.attachChild(audio_shoot);
        audio_shoot.play();
      }

    public static void main( String[] args ) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        settings.setWidth(1200);
        settings.setHeight(800);
        settings.setTitle("Space Shooter");
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }
}
