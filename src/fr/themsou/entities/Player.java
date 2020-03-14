package fr.themsou.entities;

import fr.themsou.main.Main;
import fr.themsou.main.MainLoopManager;
import fr.themsou.models.TexturedModel;
import fr.themsou.utils.Location;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Player extends OcularEntity{

    private final float speed = 5.6f / MainLoopManager.IPS;
    private final float jumpMaxCoolDown = 0.3f * MainLoopManager.IPS;


    private float sensibility = 5;
    private boolean press_F5 = false;
    private boolean jump = false;
    private float jumpDistance = 0.01f;

    private float jumpCoolDown = 0;
    private float fallDistance = 0.01f;

    public Player(TexturedModel model, Location location, float scale){
        super(model, location, scale);
    }

    public void updateInputs() {

        moveView(-(Mouse.getDX() / sensibility), -(Mouse.getDY() / sensibility), (Mouse.getDY() / sensibility));

        float speed = this.speed;

        System.out.println(jumpCoolDown);
        if(jump){
            speed *= 1.2;
        }else if(jumpCoolDown > 0){
            jumpCoolDown --;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
                speed *= 10;
        }if(Keyboard.isKeyDown(Keyboard.KEY_E)){
            speed /= 10;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_Z) || Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)){
            moveForward(-speed);

        }if(Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
            moveForward(speed);

        }if(Keyboard.isKeyDown(Keyboard.KEY_Q) || Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
            moveSide(-speed);

        }if(Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
            moveSide(speed);

        }/*if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            moveHeight(-speed);
        }*/
        if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)){
            moveView(0, 0, 1);

        }if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5)){
            moveView(0, 0, -1);

        }

        for(Terrain terrain : Main.mainLoop.terrains){
            if(terrain.isIn(location.getX(), location.getZ())){
                float height = terrain.getHeight(location.getX(), location.getZ());
                if(location.getY() > height){
                    if(!jump){
                        float fallSpeed = this.speed*clamp((fallDistance+1), 0.3f, 1.5f);
                        moveHeight(-fallSpeed);
                        fallDistance += fallSpeed;

                    }else fallDistance = 0.01f;
                }else{
                    fallDistance = 0.01f;
                    if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && jumpCoolDown <= 0){
                        jump = true;
                        jumpCoolDown = jumpMaxCoolDown;
                    }
                }if(location.getY() < height){
                    location.setY(height);
                }
            }
        }

        if(jump){
            if(jumpDistance < 1.5f){
                float jumpSpeed = this.speed/clamp((jumpDistance), 1, 3);
                moveHeight(jumpSpeed);
                jumpDistance += jumpSpeed;
            }else{
                jump = false;
                jumpDistance = 0.01f;
            }
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_F5)){
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

    private float clamp(float number, float min, float max){
        return Math.max(min, Math.min(max, number));
    }

}
