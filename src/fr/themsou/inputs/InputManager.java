package fr.themsou.inputs;

import fr.themsou.constants.Direction;
import fr.themsou.movements.MovementManager;
import fr.themsou.utils.Location;
import fr.themsou.utils.SimpleCallBack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class InputManager {


    private MovementManager movementManager;
    private InputEvents inputEvents;

    public InputManager(MovementManager movementManager){
        this.movementManager = movementManager;
        this.inputEvents = new InputEvents();
    }

    public void updateInputs() {

        float speedFactor = 1;
        if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
            speedFactor *= 10;
        }if(Keyboard.isKeyDown(Keyboard.KEY_E)){
            speedFactor /= 10;
        }

        movementManager.moveView(Mouse.getDX(), Mouse.getDY(), 0);

        if(Keyboard.isKeyDown(Keyboard.KEY_Z) || Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)){
            movementManager.walk(Direction.FORWARD, speedFactor);

        }if(Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
            movementManager.walk(Direction.BACKWARD, speedFactor);

        }if(Keyboard.isKeyDown(Keyboard.KEY_Q) || Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
            movementManager.walk(Direction.LEFT, speedFactor);

        }if(Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
            movementManager.walk(Direction.RIGHT, speedFactor);

        }

        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            movementManager.jump();

        }

        inputEvents.updateEvents();

        inputEvents.registerKey(Keyboard.KEY_LSHIFT, new SimpleCallBack<Boolean>() {
            @Override public void call(Boolean press) {
                if(press){
                    movementManager.sneak();
                }else{
                    movementManager.unsneak();
                }
            }
        });

        // MACRO

        inputEvents.registerKey(Keyboard.KEY_C, new SimpleCallBack<Boolean>() {
            @Override public void call(Boolean press) {
                if(press){
                    if(Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)){
                        movementManager.getPlayer().teleport(new Location(100, 0, 100));
                    }
                }
            }
        });

        inputEvents.registerKey(Keyboard.KEY_F5, new SimpleCallBack<Boolean>() {
            @Override public void call(Boolean press) {
                if(press){
                    movementManager.getPlayer().switchViewMode();
                }
            }
        });

        movementManager.update();

    }

}
