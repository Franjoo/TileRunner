package com.tilerunner.gameobjects.decorations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;

/**
 * User: Franjo
 * Date: 04.12.13
 * Time: 19:52
 * Project: TileRunner
 */
public class Torch extends Decoration {

    private float x, y;
    private Texture texture;

    // particle effect
    private ParticleEffect effect;
    private ParticleEmitter emitter;

    public Torch(MapObject o) {
        super(o);

        parseProperties();

        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("particleEffects/fire.p"), Gdx.files.internal("particleEffects/particles/"));
        effect.setPosition(x, y);
        effect.start();

        texture = new Texture(Gdx.files.internal("decorations/torch.png"));


//        emitter = new ParticleEmitter();
//        emitter.setPosition(x, y);
    }

    private void parseProperties() {
        x = Float.parseFloat(mapObject.getProperties().get("x").toString());
        y = Float.parseFloat(mapObject.getProperties().get("y").toString());

        System.out.println(x + "  " + y);
    }

    @Override
    public void update(float delta) {
        effect.update(delta);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture,x - texture.getWidth()/2,y - texture.getHeight());
        effect.draw(batch);
    }


}
