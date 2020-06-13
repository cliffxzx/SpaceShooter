package SpaceShooter;

import com.jme3.asset.AssetManager;
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
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;

public class Ship {
  public Node ship, bullet;
  public int bulletCount = 0;
  public Camera cam;
  public CameraNode camNode;
  public float rotateSpeed, moveSpeed;
  private AssetManager am;
  private InputManager im;
  private InputListener il = new Input();
  private Node rn;
  private Vector3f direction = new Vector3f();

  Ship(Node _rn, AssetManager _am, InputManager _im, Camera _cam) {
    rn = _rn; am = _am; im = _im; cam = _cam;
    rotateSpeed = 10;
    moveSpeed = 10;

    loadModel();
    loadCam();
    bindInput();
  }

  public Spatial bullet(Vector3f pos, Vector3f dir, float speed) {
    Spatial body = am.loadModel("Models/Ship/test.j3o");

    body.setUserData("pos", pos);
    body.setUserData("dir", dir);
    body.setUserData("speed", speed);

    body.setLocalTranslation(pos);

    return body;
  }

  public void shoot() {
    Spatial blt = bullet(ship.getLocalTranslation(), cam.getDirection(), 500);

    bullet.attachChild(blt);

    ++bulletCount;
  }

  public void update(float tpf) {
    for(int w = 0; w < bulletCount; ++w) {
      Spatial blt = bullet.getChild(w);

      float speed = blt.getUserData("speed");
      Vector3f dir = blt.getUserData("dir");

      blt.move(dir.mult(speed * tpf));
    }
  }

  private void loadModel() {
    Spatial body = am.loadModel("Models/Ship/test.j3o");
    body.setName("body");

    ship = new Node("ship");
    ship.attachChild(body);

    bullet = new Node("bullet");

    rn.attachChild(ship);
    rn.attachChild(bullet);
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
    ship.attachChild(camNode);
    camNode.setLocalTranslation(new Vector3f(0, 15, -5));
    camNode.lookAt(ship.getLocalTranslation(), Vector3f.UNIT_Y);
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
          ship.move(direction);
          break;
        case "backward":
          direction.multLocal(-moveSpeed * 40f * tpf);
          ship.move(direction);
          break;
        case "left":
          direction.crossLocal(Vector3f.UNIT_Y).multLocal(-moveSpeed * 8f * tpf);
          ship.move(direction);
          break;
        case "right":
          direction.crossLocal(Vector3f.UNIT_Y).multLocal(moveSpeed * 8f * tpf);
          ship.move(direction);
          break;
        case "rotateLeft":
          ship.rotate(0, -rotateSpeed * 0.1f * tpf, 0);
          break;
        case "rotateRight":
          ship.rotate(0, rotateSpeed * 0.1f * tpf, 0);
          break;
        case "rotateUp":
          ship.rotate(-rotateSpeed * 0.1f * tpf, 0, 0);
          break;
        case "rotateDown":
          ship.rotate(rotateSpeed * 0.1f * tpf, 0, 0);
          break;
        case "shoot":
          shoot();
          break;
      }
    }

  }
}