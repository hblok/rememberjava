package com.rememberjava.graphics;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class LibGdxHelloWold implements ApplicationListener {

  private SpriteBatch batch;
  private BitmapFont font;

  private float angle;
  private int color;

  public static void main(String[] args) {
    LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
    cfg.title = "hello-world";
    cfg.width = 480;
    cfg.height = 320;

    new LwjglApplication(new LibGdxHelloWold(), cfg);
  }

  @Override
  public void create() {
    batch = new SpriteBatch();
    font = new BitmapFont();
    font.setColor(Color.RED);
  }

  @Override
  public void dispose() {
    batch.dispose();
    font.dispose();
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    Matrix4 rotate = new Matrix4();
    rotate.rotate(new Vector3(0, 0, 1), angle--);
    rotate.trn(200, 200, 0);
    batch.setTransformMatrix(rotate);

    batch.begin();
    font.setColor(new Color(color++ % 255));
    font.draw(batch, "Hello World", -40, 0);
    batch.end();
  }

  @Override
  public void resize(int width, int height) {}

  @Override
  public void pause() {}

  @Override
  public void resume() {}
}
