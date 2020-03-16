package fr.themsou.entities.livingEntity.ocularEntity;

import fr.themsou.inputs.InputManager;
import fr.themsou.movements.MovementManager;
import fr.themsou.models.TexturedModel;
import fr.themsou.utils.Location;

public class Player extends OcularEntity {

    private float speedFactor = 1;
    private float jumpDistance = 1.5f;


    private InputManager inputManager;

    public Player(TexturedModel model, Location location, float scale){
        super(model, location, scale);
        MovementManager movementManager = new MovementManager(this);
        inputManager = new InputManager(movementManager);
    }

    public void updateInputs(){
        inputManager.updateInputs();
    }


    public float getSpeedFactor() {
        return speedFactor;
    }
    public void setSpeedFactor(float speedFactor){
        this.speedFactor = speedFactor;
    }
    public float getJumpDistance(){
        return jumpDistance;
    }
    public void setJumpDistance(float jumpDistance){
        this.jumpDistance = jumpDistance;
    }
}
