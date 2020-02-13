package fr.themsou.entities;

import fr.themsou.main.MainLoopManager;
import fr.themsou.models.TexturedModel;
import fr.themsou.utils.Location;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Player extends Entity{

    private float speed = 5.6f / MainLoopManager.IPS;
    private float sensibility = 5;
    private boolean press_F5 = false;

    public Player(TexturedModel model, Location location, float scale){
        super(model, location, scale);
    }

    public void updateInputs() {

        float speed = this.speed;

        moveView(-(Mouse.getDX() / sensibility), -(Mouse.getDY() / sensibility), (Mouse.getDY() / sensibility));

        if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
            speed *= 10;
        }if(Keyboard.isKeyDown(Keyboard.KEY_E)){
            speed /= 10;
        }if(Keyboard.isKeyDown(Keyboard.KEY_Z) || Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)){
            moveForward(-speed);

        }if(Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
            moveForward(speed);

        }if(Keyboard.isKeyDown(Keyboard.KEY_Q) || Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
            moveSide(-speed);

        }if(Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
            moveSide(speed);

        }if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            moveHeight(-speed);

        }if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            moveHeight(speed);

        }if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)){
            moveView(0, 0, 1);

        }if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5)){
            moveView(0, 0, -1);

        }if(Keyboard.isKeyDown(Keyboard.KEY_F5)){
            press_F5 = true;
        }else if(press_F5){
            if(viewMode >= 2) viewMode = 0;
            else viewMode++;
            press_F5 = false;
        }

        // MACRO

        if(Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_C)){
            teleport(new Location(100, 0, 100));
        }

    }



}
