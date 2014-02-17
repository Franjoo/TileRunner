package test.rope;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * User: Franjo
 * Date: 26.11.13
 * Time: 19:43
 * Project: TileRunner
 */
public class Player {

    public float x, y;
    public float width, height;

    private Texture texture;

    private Rope rope;

    public Player() {
        x = 100;
        y = Gdx.graphics.getHeight() / 2;
        width = 32;
        height = 32;

        drawTexture();
        rope = new Rope(this);
    }

    private void drawTexture() {
        Pixmap pixmap = new Pixmap((int) width, (int) width, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fillCircle((int) width/2,(int) width/2, pixmap.getWidth()/2);
        texture = new Texture(pixmap);
    }


    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(texture, x - width/2, y - height/2);
        batch.end();

        rope.render(batch);
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            rope.rope();
        }

        rope.update(delta);
    }

}
