package org.project.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import lombok.Getter;

@Getter
public class Scene {
    private CameraInputController cameraController;
    private InstancesPool instancesPool;
    private PerspectiveCamera camera;
    private Environment environment;
    private ModelInstance instance;

    public Scene(Model model){
        this.instance = new ModelInstance(model);
        this.instancesPool = new InstancesPool();
        createEnvironment();
        createCamera();
    }

    public void render(ModelBatch batch){
        batch.render(instance, environment);
        batch.render(instancesPool.getInstances("ship"), environment);
        batch.render(instancesPool.getInstances("block"), environment);
        batch.render(instancesPool.getInstances("invader"), environment);
        cameraController.update();
    }


    private void createEnvironment(){
        this.environment = new Environment();
        var color = new Color(0.8f, 0.8f, 0.8f, 1f);
        var direction = new Vector3(-1f, -0.8f, -1f);
        this.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        this.environment.add(new DirectionalLight().set(color, direction));
    }

    private void createCamera(){
        var position = new Vector3(0f, 7f, 10f);
        var target = new Vector3(0f, 0f, 0f);
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(position);
        camera.lookAt(target);
        camera.near = 1f;
        camera.far = 300f;
        cameraController = new CameraInputController(camera);
        Gdx.input.setInputProcessor(cameraController);
        camera.update();
    }

}
