package SpaceShooter;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;

public class Asteroid {
  public Spatial body;
  public float rotateSpeed, moveSpeed;
  private AssetManager am;
  private Vector3f direction;

  Asteroid(AssetManager _am, float _rs, float _ms, Vector3f _direction) {
    am = _am;
    rotateSpeed = _rs;
    moveSpeed = _ms;
    direction = _direction;

    loadModel();
    body.setLocalTranslation(direction);
  }

  private void loadModel() {
    body = am.loadModel("Models/Ship/test.j3o");
    Material mat = new Material(am, "Common/MatDefs/Misc/ShowNormals.j3md");
    body.setMaterial(mat);
    body.scale(10);
  }

  public void update(float tpf) {
    body.move(direction.mult(tpf * moveSpeed / 10000));
    body.rotate(rotateSpeed * tpf, rotateSpeed * tpf, rotateSpeed * tpf);
  }
}