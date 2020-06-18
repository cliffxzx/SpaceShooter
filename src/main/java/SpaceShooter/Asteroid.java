package SpaceShooter;

import java.util.Random;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Asteroid {
  public Node rn, asteroids;
  public float asteroidCount = 0;
  private AssetManager am;
  private AudioNode audio_bang;
  private ParticleEmitter fire;
  private Random random = new Random();

  Asteroid(Node _rn, AssetManager _am, float _ps, float _range) {
    rn = _rn; am = _am;

    loadAudio();
    loadExplosion();

    asteroids = new Node("asteroid");
    rn.attachChild(asteroids);

    for(int w = (int)(_range / -2.0f); w < _range / 2; w += _ps) {
      Vector3f posAndDirection = new Vector3f(random.nextInt((int)_ps) + w, random.nextInt((int)_ps) + w, random.nextInt((int)_ps) + w);
      posAndDirection.multLocal(new Vector3f(random.nextFloat() * 2 - 1, random.nextFloat() * 2 - 1, random.nextFloat() * 2 - 1));
      asteroids.attachChild(asteroid((float)random.nextDouble(), random.nextInt(10), posAndDirection));
      ++asteroidCount;
    }

  }

  public void destroy(int index) {
    audio_bang.playInstance();

    fire.setLocalTranslation(asteroids.getChild(index).getLocalTranslation());
    fire.setStartSize(asteroids.getChild(index).getLocalScale().lengthSquared() * 1000);
    fire.emitParticles(index);
    setTimeout(() -> fire.setStartSize(0), 1000);

    asteroids.detachChild(asteroids.getChild(index));
    --asteroidCount;
  }

  public static void setTimeout(Runnable runnable, int delay){
    new Thread(() -> {
        try {
            Thread.sleep(delay);
            runnable.run();
        }
        catch (Exception e){
            System.err.println(e);
        }
    }).start();
}

  public Spatial asteroid(float rotate, float speed, Vector3f dir) {
    int rd = random.nextInt(1) + 1;
    Spatial body = am.loadModel("Models/Asteroid/asteroid" + rd + ".j3o");

    Material mat = new Material(am, "Common/MatDefs/Light/Lighting.j3md");
    mat.setTexture("DiffuseMap", am.loadTexture("Textures/Asteroid/asteroid" + rd + ".jpg"));
    body.setMaterial(mat);

    body.setUserData("rotate", rotate);
    body.setUserData("speed", speed);
    body.setUserData("dir", dir);
    body.setLocalTranslation(dir);
    body.scale((float)Math.random() * 0.5f);

    return body;
  }

  public void loadExplosion() {
    fire = new ParticleEmitter("Emitter", Type.Triangle, 3000);
    Material mat_red = new Material(am, "Common/MatDefs/Misc/Particle.j3md");
    mat_red.setTexture("Texture", am.loadTexture("Effects/Explosion/flame.png"));
    fire.setMaterial(mat_red);
    fire.setImagesX(2); fire.setImagesY(2); // 2x2 texture animation
    fire.setEndColor(  new ColorRGBA(1f, 0f, 0f, 1f));   // red
    fire.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
    fire.getParticleInfluencer().setInitialVelocity(new Vector3f(0,2,0));
    fire.setEndSize(10f);
    fire.setGravity(0,0,0);
    fire.setLowLife(0f);
    fire.setHighLife(3f);
    fire.getParticleInfluencer().setVelocityVariation(30f);
    fire.killAllParticles();
    rn.attachChild(fire);
  }

  public void loadAudio() {
    audio_bang = new AudioNode(am, "Sound/Asteroid/explosion2.ogg", DataType.Buffer);
    audio_bang.setPositional(false);
    audio_bang.setLooping(false);
    audio_bang.setVolume(0.5f);
    rn.attachChild(audio_bang);
  }

  public void update(float tpf) {
    for(int w = 0; w < asteroidCount; ++w) {
      Spatial ast = asteroids.getChild(w);
      Vector3f dir = ast.getUserData("dir");
      float speed = ast.getUserData("speed");
      float rotate = ast.getUserData("rotate");
      ast.move(dir.mult(tpf * speed));
      ast.rotate(rotate * tpf, rotate * tpf, rotate * tpf);
      ast.updateModelBound();
    }
  }
}