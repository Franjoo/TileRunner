package test.rope;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.tilerunner.core.C;

/**
 * User: Franjo
 * Date: 26.11.13
 * Time: 19:56
 * Project: TileRunner
 */
public class Rope {

    private boolean isAttached;

    private TextureRegion region;
    private Texture texture;
    private Vector2 anchor;
    private Vector2 target;
    private Vector2 direction;
    private float alpha;
    private float l;
    private float h;

    private boolean left;


    private float oldVx, oldVy, oldVr;
    private float newVx, newVy, newVr;

    private float oldDx, oldDy;
    private float newDx, newDy;

    private Player player;

    public Rope(Player player) {
        this.player = player;

        anchor = new Vector2(0, Gdx.graphics.getHeight());
        target = new Vector2();
        direction = new Vector2();

        drawRopeTexture();
        drawAnchorTexture();
    }

    private void drawRopeTexture() {
        Pixmap pixmap = new Pixmap(3, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 1);
        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        region = new TextureRegion(new Texture(pixmap));
    }

    private void drawAnchorTexture() {
        Pixmap pixmap = new Pixmap(4, 4, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 1, 1);
        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        texture = new Texture(pixmap);
    }

    public void render(SpriteBatch batch) {
        batch.begin();

        // anchor
        batch.draw(texture, anchor.x, anchor.y);

        batch.draw(texture, target.x, target.y);

        batch.draw(region, anchor.x, anchor.y,
                0, 0,
                region.getRegionWidth(), region.getRegionHeight(),
                1, l / region.getRegionHeight(),
                alpha - 90, true
        );
        batch.end();
    }

    public void update(float delta) {

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

            rope(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

        }


        if (isAttached) {

            final float g = 9.81f;
            double T = 2 * Math.PI * Math.sqrt(l / g);
            double v = 2 * Math.PI * l / T;

            System.out.println("v: " + v);

//            final float h = anchor.y - player.y;
//            final float v = (float) Math.sqrt(2 * g * Math.abs(h));

//            System.out.println("v: " + v);


//            if (h < 0) {
//                left = !left;
//            }
//
//            if (!left) {
//                direction.setAngle(direction.angle() - v * delta);
//            } else {
//                direction.setAngle(direction.angle() + v * delta);
//            }
//
//
//            target.set(anchor.x + direction.x * l, anchor.y + direction.y * l);
//
//
//            alpha = direction.angle();
//
//            player.x = target.x;
//            player.y = target.y;
//
//            System.out.println(direction);

        }

    }

    private void rope(float x, float y) {
        anchor.set(x, y);
        direction.set(player.x - anchor.x, player.y - anchor.y);
        l = direction.len();
        direction.nor();
        alpha = direction.angle() + 90;

        System.out.println("alpha: " + alpha);

//        player.x = anchor.x + direction.x;
//        player.y = anchor.y + direction.y;

        left = false;
        isAttached = true;


    }

    public void rope() {
        if (!isAttached) {
            anchor.set(player.x + Gdx.graphics.getHeight() / 2, Gdx.graphics.getHeight());
            direction.set(player.x - anchor.x, player.y - anchor.y);
            l = direction.len();
            direction.nor();
            alpha = direction.angle();

            player.x = anchor.x + direction.x;
            player.y = anchor.y + direction.y;

            left = false;
            isAttached = true;
        }
    }
}
