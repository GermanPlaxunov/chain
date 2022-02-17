package org.project.core;

import com.badlogic.gdx.graphics.g3d.ModelInstance;

import java.util.ArrayList;
import java.util.HashMap;

public class InstancesPool {
    private HashMap<String, ArrayList<ModelInstance>> instances = new HashMap<>();

    public ArrayList<ModelInstance> getInstances(String name){
        for(var entry : instances.entrySet()){
            if(entry.getKey().equals(name)){
                return entry.getValue();
            }
        }
        return null;
    }

    public void addInstance(String name, ModelInstance instance){
        this.instances.computeIfAbsent(name, k -> new ArrayList<>());
        this.instances.get(name).add(instance);
    }
}
