package org.project.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class GameLauncher extends ApplicationAdapter {
    private ArrayList<ModelInstance> instances = new ArrayList<>();

    private CameraInputController cameraController;
    private AssetManager assets;
    private PerspectiveCamera camera;
    private Environment environment;
    private ModelBatch modelBatch;
    private Boolean loading;

    private ModelInstance space;

    @Override
    public void create() {
        modelBatch = new ModelBatch();
        createEnvironment();
        createCamera();
        camera.update();
        createAssetManager();
        loading = true;
    }

    @Override
    public void render() {
        if (loading && assets.update()) {
            download();
        }
        cameraController.update();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        modelBatch.begin(camera);
        modelBatch.render(instances, environment);
        if (space != null) {
            modelBatch.render(space, environment);
        }
        modelBatch.end();
    }

    @Override
    public void dispose() {
    }

    private void download() {
        var ship = new ModelInstance(assets.get("data/ship.obj", Model.class));
        var blockModel = assets.get("data/block.obj", Model.class);
        var invaderModel = assets.get("data/invader.obj", Model.class);

        ship.transform.setToRotation(Vector3.Y, 180).trn(0, 0, 6f);
        instances.add(ship);

        for (var x = -5f; x <= 5f; x += 2f) {
            var block = new ModelInstance(blockModel);
            block.transform.setToTranslation(x, 0, 3f);
            instances.add(block);
        }

        for (var x = -10f; x <= 10f; x += 2f) {
            for (var z = -8f; z <= 0f; z += 2f) {
                var invader = new ModelInstance(invaderModel);
                invader.transform.setToTranslation(x, 0f, z);
                instances.add(invader);
            }
        }

        space = new ModelInstance(assets.get("data/spacesphere.obj", Model.class));
        loading = false;
    }

    private void createEnvironment() {
        var color = new Color(0.8f, 0.8f, 0.8f, 1f);
        var direction = new Vector3(-1f, -0.8f, -1f);
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(color, direction));
    }

    private void createCamera() {
        var position = new Vector3(0f, 7f, 10f);
        var target = new Vector3(-0f, 0f, 0f);
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cameraController = new CameraInputController(camera);
        camera.position.set(position);
        camera.lookAt(target);
        camera.near = 1f;
        camera.far = 300f;
        Gdx.input.setInputProcessor(cameraController);
    }

    private void createAssetManager() {
        assets = new AssetManager();
        assets.load("data/ship.obj", Model.class);
        assets.load("data/block.obj", Model.class);
        assets.load("data/invader.obj", Model.class);
        assets.load("data/spacesphere.obj", Model.class);
    }

}
