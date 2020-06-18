package SpaceShooter;

import com.jme3.scene.Spatial;

public class CollisionDetector {
  public static void detect(Ship ship, Asteroid asteroid, Hud hud) {

    for (int w = 0; w < ship.bulletCount; ++w)
      for (int w1 = 0; w1 < asteroid.asteroidCount; ++w1) {
        Spatial shp = ship.bullet.getChild(w), ast = asteroid.asteroids.getChild(w1);

        if (shp.getWorldBound().intersects(ast.getWorldBound())) {
          System.out.println("Collision bullets {" + w + "} asteroid {" + w1 + "}");

          ship.destroy(w);
          asteroid.destroy(w1);
          hud.addScore(1);

          --w;
          break;
        }
      }
  }
}