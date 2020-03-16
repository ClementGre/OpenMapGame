package fr.themsou.inputs;

import fr.themsou.utils.SimpleCallBack;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;

public class InputEvents {

    private ArrayList<Integer> keysDown = new ArrayList<>();
    private HashMap<Integer, SimpleCallBack<Boolean>> keysRegistered = new HashMap<>();

    public void updateEvents(){

        for(Integer key : keysRegistered.keySet()){

            if(Keyboard.isKeyDown(key)){
                if(!keysDown.contains(key)){
                    keysRegistered.get(key).call(true);
                    keysDown.add(key);
                }
            }else if(keysDown.contains(key)){
                keysRegistered.get(key).call(false);
                keysDown.remove(key);

            }
        }
    }

    public void registerKey(int key, SimpleCallBack<Boolean> callBack){
        keysRegistered.put(key, callBack);
    }

}
