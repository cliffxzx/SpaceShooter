package SpaceShooter;

import java.util.ArrayList;
import java.util.Random;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;

public class Asteroids {
  public Node node, rn;
  private AssetManager am;
  private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
  private float perSize, range;
  private Random random = new Random();

  Asteroids(Node _rn, AssetManager _am, float _ps, float _range) {
    rn = _rn; am = _am; perSize = _ps; range = _range;
    node = new Node("Asteroids");
    rn.attachChild(node);

    for(int w = (int)_ps; w < _range; w += _ps) {
      Vector3f posAndDirection = new Vector3f(random.nextInt(w), random.nextInt(w), random.nextInt(w));
      posAndDirection.multLocal(new Vector3f(random.nextFloat() * 2 - 1, random.nextFloat() * 2 - 1, random.nextFloat() * 2 - 1));
      Asteroid ast = new Asteroid(_am, (float)random.nextDouble(), random.nextInt(5000), posAndDirection);
      node.attachChild(ast.body);
      asteroids.add(ast);
    }

  }

  public void update(float tpf) {
    for(Asteroid ast: asteroids)
      ast.update(tpf);
  }
}