package SpaceShooter;

import java.util.ArrayList;
import java.util.Queue;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;

public class Ship {
  public Node node;
  public Spatial body;
  public Camera cam;
  public CameraNode camNode;
  public float rotateSpeed, moveSpeed;
  private AssetManager am;
  private InputManager im;
  private InputListener il = new Input();
  private Node rn;
  private Vector3f direction = new Vector3f();
  private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

  Ship(Node _rn, AssetManager _am, InputManager _im, Camera _cam) {
    rn = _rn; am = _am; im = _im; cam = _cam;
    rotateSpeed = 10;
    moveSpeed = 10;

    loadModel();
    loadCam();
    bindInput();
  }

  public void shoot() {
    Bullet blt = new Bullet(am, cam.getDirection().normalize().multLocal(1), node.getLocalTranslation());
    rn.attachChild(blt.body);
    bullets.add(blt);
  }

  public void update(float tpf) {
    for(Bullet blt: bullets)
      blt.update(tpf);
  }

  private void loadModel() {
    body = am.loadModel("Models/Ship/test.j3o");

    node = new Node("ship");
    node.attachChild(body);

    rn.attachChild(node);
  }

  private void bindInput() {
    im.addMapping("forward", new KeyTrigger(KeyInput.KEY_W));
    im.addMapping("backward", new KeyTrigger(KeyInput.KEY_S));
    im.addMapping("left", new KeyTrigger(KeyInput.KEY_A));
    im.addMapping("right", new KeyTrigger(KeyInput.KEY_D));
    im.addMapping("shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    im.addMapping("rotateLeft", new MouseAxisTrigger(MouseInput.AXIS_X, false));
    im.addMapping("rotateRight", new MouseAxisTrigger(MouseInput.AXIS_X, true));
    im.addMapping("rotateUp", new MouseAxisTrigger(MouseInput.AXIS_Y, false));
    im.addMapping("rotateDown", new MouseAxisTrigger(MouseInput.AXIS_Y, true));

    im.addListener(il, "forward", "backward", "left", "right", "toggleRotate", "rotateLeft", "rotateRight", "rotateUp", "rotateDown", "shoot");
  }

  private void loadCam() {
    camNode = new CameraNode("ShipCam", cam);
    camNode.setControlDir(ControlDirection.SpatialToCamera);
    node.attachChild(camNode);
    camNode.setLocalTranslation(new Vector3f(0, 15, -5));
    camNode.lookAt(node.getLocalTranslation(), Vector3f.UNIT_Y);
    cam.setFrustumFar(100000f);
  }

  private class Input implements AnalogListener, ActionListener {

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
      im.setCursorVisible(false);
      if(isPressed) {
        switch(name) {

        }
      }
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
      direction.set(cam.getDirection()).normalizeLocal();
      switch(name) {
        case "forward":
          direction.multLocal(moveSpeed * 40f * tpf);
          node.move(direction);
          break;
        case "backward":
          direction.multLocal(-moveSpeed * 40f * tpf);
          node.move(direction);
          break;
        case "left":
          direction.crossLocal(Vector3f.UNIT_Y).multLocal(-moveSpeed * 8f * tpf);
          node.move(direction);
          break;
        case "right":
          direction.crossLocal(Vector3f.UNIT_Y).multLocal(moveSpeed * 8f * tpf);
          node.move(direction);
          break;
        case "rotateLeft":
          node.rotate(0, -rotateSpeed * 0.1f * tpf, 0);
          break;
        case "rotateRight":
          node.rotate(0, rotateSpeed * 0.1f * tpf, 0);
          break;
        case "rotateUp":
          node.rotate(-rotateSpeed * 0.1f * tpf, 0, 0);
          break;
        case "rotateDown":
          node.rotate(rotateSpeed * 0.1f * tpf, 0, 0);
          break;
        case "shoot":
          shoot();
          break;
      }
    }

  }
}