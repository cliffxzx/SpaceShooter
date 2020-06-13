package SpaceShooter;

import java.util.Random;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Asteroid {
  public Node rn, asteroids;
  public float asteroidCount = 0;
  private AssetManager am;
  private float perSize, range;
  private Random random = new Random();

  Asteroid(Node _rn, AssetManager _am, float _ps, float _range) {
    rn = _rn; am = _am; perSize = _ps; range = _range;
    asteroids = new Node("asteroid");
    rn.attachChild(asteroids);

    for(int w = (int)(range / -2.0f); w < range / 2; w += _ps) {
      Vector3f posAndDirection = new Vector3f(random.nextInt((int)_ps) + w, random.nextInt((int)_ps) + w, random.nextInt((int)_ps) + w);
      posAndDirection.multLocal(new Vector3f(random.nextFloat() * 2 - 1, random.nextFloat() * 2 - 1, random.nextFloat() * 2 - 1));
      asteroids.attachChild(asteroid((float)random.nextDouble(), random.nextInt(10), posAndDirection));
      ++asteroidCount;
    }

  }

  public Spatial asteroid(float rotate, float speed, Vector3f dir) {
    Spatial body = am.loadModel("Models/Ship/test.j3o");

    body.setMaterial(new Material(am, "Common/MatDefs/Misc/ShowNormals.j3md"));

    body.setUserData("rotate", rotate);
    body.setUserData("speed", speed);
    body.setUserData("dir", dir);
    body.setLocalTranslation(dir);
    body.scale((float)Math.random() * 50.f);

    return body;
  }

  public void update(float tpf) {
    for(int w = 0; w < asteroidCount; ++w) {
      Spatial ast = asteroids.getChild(w);
      Vector3f dir = ast.getUserData("dir");
      float speed = ast.getUserData("speed");
      float rotate = ast.getUserData("rotate");
      ast.move(dir.mult(tpf * speed));
      ast.rotate(rotate * tpf, rotate * tpf, rotate * tpf);
    }
  }
}