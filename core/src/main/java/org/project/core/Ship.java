package org.project.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Ship {
    private ModelInstance instance;
    private Vector3 position;
    private Vector3 cameraPosition;
    private float rotationX;
    private float rotationY;
    private float rotationZ;
    private float speed;
    private float deltaX;
    private float deltaZ;


    public Ship(ModelInstance instance, Vector3 position) {
        this.instance = instance;
        this.position = position;
        this.cameraPosition = new Vector3(position.x, position.y + 1, position.z + 3);
        this.instance.transform.setToTranslation(position);
        this.rotationX = 0;
        this.rotationY = 1;
        this.rotationZ = 0;
        this.speed = 10f;
    }

    public void checkMove() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            moveForward();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            rotateRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            rotateLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moveBack();
        }
        update();
    }

    private void update() {
        instance.transform.setToRotation(Vector3.Y, rotationY).trn(position);
        cameraPosition.set(position.x, position.y + 2, position.z + 4);
    }

    private void moveForward() {
        deltaX = Gdx.graphics.getDeltaTime() * speed * (float) (Math.sin((Math.PI * rotationY) / 180));
        deltaZ = Gdx.graphics.getDeltaTime() * speed * (float) (Math.sin((Math.PI * rotationY + 180) / 180));
        position.set(position.x + deltaX, position.y, position.z);
        position.set(position.x, position.y, position.z + deltaZ);
    }

    private void rotateRight() {
        rotationY -= 1.5f;
        if (rotationY <= 0f) {
            rotationY = 360f;
        }
    }

    private void rotateLeft() {
        rotationY += 1.5f;
        if (rotationY >= 360f) {
            rotationY = 1f;
        }
    }

    private void moveBack() {
        System.out.println(rotationY);
        position.set(position.x, position.y, position.z - 0.2f);
        position.set(position.x + 0.2f, position.y, position.z);
    }

}
