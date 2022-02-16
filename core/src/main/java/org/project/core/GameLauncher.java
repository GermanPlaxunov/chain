package org.project.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class GameLauncher extends ApplicationAdapter {
    private ArrayList<ModelInstance> instances = new ArrayList<>();

    private AssetManager assets;
    private ModelBatch modelBatch;
    private Boolean loading;

    private Scene scene;

    @Override
    public void create() {
        modelBatch = new ModelBatch();
        createAssetManager();
        loading = true;
    }

    @Override
    public void render() {
        if (loading && assets.update()) {
            download();
        }
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        if(scene != null) {
            if (scene.getCamera() != null) {
                modelBatch.begin(scene.getCamera());
                if (scene.getInstance() != null) {
                    scene.render(modelBatch);
                    modelBatch.render(instances, scene.getEnvironment());
                }
                modelBatch.end();
            }
        }
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

        scene = new Scene(assets.get("data/spacesphere.obj", Model.class));
        loading = false;
    }

    private void createAssetManager() {
        assets = new AssetManager();
        assets.load("data/ship.obj", Model.class);
        assets.load("data/block.obj", Model.class);
        assets.load("data/invader.obj", Model.class);
        assets.load("data/spacesphere.obj", Model.class);
    }

}
