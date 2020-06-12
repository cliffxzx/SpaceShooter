package SpaceShooter;

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

public class Bullet {
  public Spatial body;
  public float moveSpeed;
  private AssetManager am;
  private Vector3f direction;

  Bullet(AssetManager _am, Vector3f _direction, Vector3f _translation) {
    am = _am; direction = _direction;
    moveSpeed = 50000;

    loadModel();
    body.setLocalTranslation(_translation);
  }

  private void loadModel() {
    body = am.loadModel("Models/Ship/test.j3o");
  }

  public void update(float tpf) {
    body.move(direction.mult(moveSpeed / 100f * tpf));
  }
}