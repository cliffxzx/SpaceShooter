package SpaceShooter;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.system.AppSettings;

public class Main extends SimpleApplication {
    private Hud hud;
    private Ship ship;
    private Asteroid asteroid;
    private BulletAppState bulletState;

    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        assetManager.registerLocator("assets/", FileLocator.class);

        bulletState = new BulletAppState();
        stateManager.attach(bulletState);

        hud = new Hud(guiViewPort, assetManager, inputManager, audioRenderer);
        ship = new Ship(rootNode, assetManager, inputManager, cam);
        asteroid = new Asteroid(rootNode, assetManager, 1, 5000);
    }

    @Override
    public void simpleUpdate(float tpf) {
        ship.update(tpf);
        asteroid.update(tpf);
        for(int w = 0; w < ship.bulletCount; ++w)
            for(int w1 = 0; w1 < asteroid.asteroidCount; ++w1) {
                if (ship.bullet.getChild(w).getWorldBound().intersects(asteroid.asteroids.getChild(w1).getWorldBound())) {
                    System.out.println("Collision bullets {" + w + "} asteroid {" + w1 + "}");

                    ship.bullet.detachChildAt(w);
                    asteroid.asteroids.detachChild(asteroid.asteroids.getChild(w1));

                    --w;
                    --ship.bulletCount;
                    --asteroid.asteroidCount;
                    hud.addScore(1);
                    break;
                }
            }
    }

    public static void main( String[] args ) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        settings.setWidth(1600);
        settings.setHeight(1000);
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }
}
