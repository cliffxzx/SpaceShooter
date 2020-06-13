package SpaceShooter;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.jfx.injme.JmeFxContainer;

public class Main extends SimpleApplication {
    private Ship ship;
    private Asteroid asteroid;
    private BulletAppState bulletState;
    private JmeFxContainer container;

    private void loadJfx() {
        container = JmeFxContainer.install(this, getGuiNode());

    }

    @Override
    public void simpleInitApp() {
        loadJfx();

        flyCam.setEnabled(false);
        assetManager.registerLocator("assets/", FileLocator.class);

        bulletState = new BulletAppState();
        stateManager.attach(bulletState);

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
                    break;
                }
            }
    }

    public static void main( String[] args ) {
        Main app = new Main();
        app.start();
    }
}
