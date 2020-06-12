package SpaceShooter;

import java.util.Timer;
import java.util.TimerTask;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.system.AppSettings;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

public class Main extends SimpleApplication {
    public static void main( String[] args ) {
        Main app = new Main();
        app.start();
    }

    private Spatial test;
    private Ship ship;
    private Asteroids asteroids;

    @Override
    public void simpleInitApp() {
        assetManager.registerLocator("assets/", FileLocator.class);
        flyCam.setEnabled(false);

        ship = new Ship(rootNode, assetManager, inputManager, cam);
        asteroids = new Asteroids(rootNode, assetManager, 2, 10000);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", assetManager.loadTexture("Interface/Logo/Monkey.jpg"));
        Geometry ground = new Geometry("ground", new Quad(50, 50));
        ground.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));
        ground.setLocalTranslation(-25, -1, 25);
        ground.setMaterial(mat);
        rootNode.attachChild(ground);
    }

    @Override
    public void simpleUpdate(float tpf) {
        ship.update(tpf);
        asteroids.update(tpf);
    }
}
