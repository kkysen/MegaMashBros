package com.github.kkysen.supersmashbros.app;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.kkysen.supersmashbros.core.World;
import com.github.kkysen.supersmashbros.players.Mario;

public class SuperSmashBros extends ApplicationAdapter {
    
    public static final String TITLE = "Super Smash Bros";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    
    private static final Path ASSETS = Paths.get("").toAbsolutePath().getParent()
            .resolve("core/assets");
    
    public static FileHandle open(final Path path) {
        return Gdx.files.internal(path.toString());
    }
    
    public static FileHandle asset(final String path) {
        return open(ASSETS.resolve(path));
    }
    
    private OrthographicCamera camera;
    private SpriteBatch batch;
    
    private World world;
    
    private World createWorld() {
        System.out.println(ASSETS.toAbsolutePath());
        final Texture background = new Texture(asset("background.jpg"));
        final Sprite platform = new Sprite(new Texture(asset("platform.png")));
        return new World(WIDTH, HEIGHT, background, platform, Mario.userControlled(),
                Mario.randomlyControlled());
    }
    
    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        batch = new SpriteBatch();
        world = createWorld();
        Gdx.app.setLogLevel(Application.LOG_ERROR);
    }
    
    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        //batch.setProjectionMatrix(camera.combined);
        batch.begin();
        world.render(batch);
        batch.end();
        if (world.gameOver) {
            pause();
        }
        //        pause();
        //        try {
        //            System.in.read();
        //        } catch (final IOException e) {
        //            // TODO Auto-generated catch block
        //            e.printStackTrace();
        //        }
        //        resume();
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
    }
    
}
