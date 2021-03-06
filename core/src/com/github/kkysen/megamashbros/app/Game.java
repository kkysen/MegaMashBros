package com.github.kkysen.megamashbros.app;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.github.kkysen.libgdx.util.Textures;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.libgdx.util.keys.User;
import com.github.kkysen.megamashbros.core.Player;
import com.github.kkysen.megamashbros.core.World;
import com.github.kkysen.megamashbros.players.Mario;

public class Game extends ApplicationAdapter {
    
    public static final String TITLE = "Super Smash Bros";
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 900;
    
    private static boolean isRunningFromJar() {
        return Game.class.getResource("Game.class").toString().startsWith("jar");
    }
    
    private static Path findAssets() {
        Path path = Paths.get("").toAbsolutePath();
        if (!isRunningFromJar()) {
            path = path.getParent();
        }
        return path.resolve("core").resolve("assets");
    }
    
    static final Path ASSETS = findAssets();
    
    public static FileHandle open(final Path path) {
        return Gdx.files.internal(path.toString());
    }
    
    public static FileHandle asset(final String path) {
        return open(ASSETS.resolve(path));
    }
    
    public static Game instance;
    
    public static float deltaTime;
    public static float speed = 1;
    
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer lineRenderer;
    
    public World world;
    
    private static final int numAIs = 1;
    
    private Player[] createPlayers(final int numAIs) {
        Player.numPlayers = 0;
        final Player[] players = new Player[numAIs + 1];
        players[0] = Mario.userControlled();
        for (int i = 1; i < players.length; i++) {
            players[i] = Mario.frozen();
            //players[i] = Mario.jumping();
            //players[i] = Mario.randomlyControlled();
            //players[i] = Mario.smart();
            //players[i] = (i & 1) == 1 ? Mario.randomlyControlled() : Mario.frozen();
        }
        return players;
    }
    
    private static final boolean useOptions = true;
    
    private Player[] createPlayers() {
        if (useOptions) {
            return PlayerFactory.fromJson();
        } else {
            return createPlayers(numAIs);
        }
    }
    
    private World createWorld() {
        final Texture background = new Texture(asset("background.jpg"));
        System.out.println(background.getHeight() + ", " + background.getWidth());
        final Sprite platform = new Sprite(new Texture(asset("platform.png")));
        return new World(WIDTH, HEIGHT, background, platform, createPlayers());
    }
    
    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_NONE);
        instance = this;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        batch = new SpriteBatch();
        lineRenderer = new ShapeRenderer();
        world = createWorld();
    }
    
    private static void readSpeed() {
        final Scanner in;
        try {
            in = new Scanner(ASSETS.resolve("speed.txt"));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        if (in.hasNextLine() && in.hasNextFloat()) {
            final float newSpeed = in.nextFloat();
            if (newSpeed != speed) {
                System.out.println("\tread maxSpeed = " + newSpeed);
            }
            speed = newSpeed;
        }
        in.close();
    }
    
    @Override
    public void render() {
        readSpeed();
        deltaTime = Gdx.graphics.getDeltaTime() * speed;
        
        if (KeyBinding.RESTART.isPressed(User.get())) {
            world.replacePlayers(createPlayers());
            world.gameOver = false;
        }
        
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        world.render(batch);
        batch.end();
        
        Gdx.gl20.glLineWidth(5);
        lineRenderer.setProjectionMatrix(camera.combined);
        lineRenderer.begin(ShapeType.Line);
        world.render(lineRenderer);
        lineRenderer.end();
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
        Textures.dispose();
        Mario.dispose();
    }
    
}
