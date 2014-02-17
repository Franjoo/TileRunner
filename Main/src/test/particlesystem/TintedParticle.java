package test.particlesystem;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;
import com.tilerunner.gameobjects.world.Detector;

/**
 * User: Franjo
 * Date: 10.12.13
 * Time: 19:09
 * Project: TileRunner
 */
public class TintedParticle extends Sprite implements Pool.Poolable {

    public static final Texture texParticle = new Texture(Gdx.files.internal("particleEffects/particles/white.png"));
    public  Detector detector;

    public boolean active;

    public float lifeTime, time;

    public float vX;
    public float vY;
    private float aX, aY;

    public TintedParticle() {
        super(texParticle);
        active = false;
    }

    public TintedParticle(float lifeTime, float vX, float vY, float aX, float aY, float r, float g, float b, float a) {
        super(texParticle);

        this.lifeTime = lifeTime;
        this.vX = vX;
        this.vY = vY;
        this.aX = aX;
        this.aY = aY;

        setColor(r,g,b,a);

        active = false;
        time = 0;
    }

    public TintedParticle(float vX, float vY, float aX, float aY) {
        super(texParticle);

        this.vX = vX;
        this.vY = vY;
        this.aX = aX;
        this.aY = aY;


        active =  true;
        time = 0;
        detector = Detector.getInstance();
    }


    public void init(float vX, float vY, float aX, float aY) {
        this.vX = vX;
        this.vY = vY;
        this.aX = aX;
        this.aY = aY;
        active = true;

        Color c = getColor();
//        setColor(0.2f,0.1f,1, (float) (0.1f + Math.random()*0.9f));
        setColor(0.2f,0.1f,1, 0.3f);

        time = 0;
        lifeTime = (float) (3 + Math.random() * 3);
    }


    public void update(float delta) {
        time += delta;
        if(time >= lifeTime){
            active = false;
            return;
        }


//        setPosition(getX() + vX * delta, getY() + vY * delta);
        float oldX = vX;
        float oldY = vY;


        if (detector.isSolid(getX() + vX * delta, getY())) {
            vX = oldY;
            vX *= -1;

//            hits++;
        } else setX(getX() + vX * delta);
//                ;x += vx * deltaTime;

        if (detector.isSolid(getX(), getY() + vY * delta)) {
            vY = oldX;
            vY *= -1;
//            hits++;
        } else setY(getY() + oldY * delta);//y += vy *  deltaTime;


//        if (getX() < 0) vX *= -1;
//        else if (getX() > Gdx.graphics.getWidth() - getWidth()) vX *= -1;
//        if (getY() < 0) vY *= -1;
//        else if (getY() > Gdx.graphics.getHeight() - getHeight()) vY *= -1;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public void reset() {
        time = 0;
    }
}
