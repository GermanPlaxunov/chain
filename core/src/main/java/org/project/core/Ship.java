package org.project.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import lombok.Data;

@Data
public class Ship {
    private ModelInstance instance;
    private Vector3 position;
    private Vector3 cameraPosition;
    private int rotationX;
    private int rotationY;
    private int rotationZ;


    public Ship(ModelInstance instance, Vector3 position){
        this.instance = instance;
        this.position = position;
        this.cameraPosition = new Vector3(position.x, position.y + 1, position.z + 3);
        this.instance.transform.setToTranslation(position);
        this.rotationX = 0;
        this.rotationY = 180;
        this.rotationZ = 0;
    }

    public void checkMove() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            moveForward();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            rotateRight();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            rotateLeft();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            moveBack();
        }
        update();
    }

    private void update(){
        instance.transform.setToRotation(Vector3.Y, rotationY).trn(position);
        cameraPosition.set(position.x, position.y + 2, position.z + 4);
    }

    private void moveForward() {
        position.set(position.x, position.y, position.z - 0.2f);
    }

    private void rotateRight() {
        rotationY--;
    }

    private void rotateLeft(){
        rotationY++;
    }

    private void moveBack(){
        position.set(position.x, position.y, position.z + 0.2f);
    }
}
