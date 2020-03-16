package fr.themsou.movements;

import fr.themsou.constants.Direction;
import fr.themsou.entities.livingEntity.LivingEntity;
import fr.themsou.entities.livingEntity.ocularEntity.OcularEntity;
import fr.themsou.entities.livingEntity.ocularEntity.Player;
import fr.themsou.utils.Maths;

public class MovementManager {

    private boolean isJumping = false;
    private float jumpDistance = 0;
    private float jumpCoolDown = 0;

    private boolean isFalling = false;
    private float fallDistance = 0;

    private boolean isSneaking = false;

    private boolean isFliying = false;
    private boolean hasCollide = true;

    private Player player;

    public MovementManager(Player player){
        this.player = player;
    }



    public void update(){

        float terrainHeight = CollisionCalculator.getTerrainHeight(player.location.getX(), player.location.getZ());

        if(player.location.getY() > terrainHeight){
            if(!isJumping){
                if(!isFalling){
                    if(player.location.getY() - terrainHeight < 0.1f){
                        player.location.setY(terrainHeight);
                    }else startFall();
                }else{
                    continueFall();
                }
            }else{
                continueJump();
            }

        }else if(player.location.getY() < terrainHeight){
            player.location.setY(terrainHeight);
            if(isFalling){
                stopFall();
            }else if(isJumping){
                continueJump();
            }else if(jumpCoolDown >= 0){
                jumpCoolDown--;
            }

        }else{
            if(isFalling){
                stopFall();
            }else if(isJumping){
                continueJump();
            }else if(jumpCoolDown > 0){
                jumpCoolDown--;
            }
        }

    }

    public void walk(Direction direction, float distance){
        float speed = LivingEntity.DEFAULT_WALK_SPEED * getPlayer().getSpeedFactor();
        if(direction == Direction.FORWARD){
            getPlayer().moveForward(distance * speed);

        }else if(direction == Direction.BACKWARD){
            getPlayer().moveForward(-distance * speed);

        }else if(direction == Direction.RIGHT){
            getPlayer().moveSide(distance * speed);

        }else if(direction == Direction.LEFT){
            getPlayer().moveSide(-distance * speed);

        }
    }

    public void jump(){

        if(!isJumping && !isFalling){
            if(jumpCoolDown <= 0){
                startJump();
            }
        }

    }

    public void sneak(){
        isSneaking = true;
    }
    public void unsneak(){
        isSneaking = false;
    }

    public void startFall(){
        fallDistance = 0;
        isFalling = true;
    }
    public void continueFall(){
        float fallSpeed = LivingEntity.DEFAULT_WALK_SPEED * Maths.clamp(fallDistance, 0.8f, 5f);
        getPlayer().moveHeight(-fallSpeed);
        fallDistance += fallSpeed;
    }
    public void stopFall(){
        isFalling = false;
        jumpCoolDown = LivingEntity.DEFAULT_JUMP_COOLDOWN;
        // Call Fall Event
    }


    public void startJump(){
        jumpDistance = 0;
        isJumping = true;
        // Call jump event
    }
    public void continueJump(){

        if(jumpDistance < player.getJumpDistance()){
            float jumpSpeed = LivingEntity.DEFAULT_WALK_SPEED * Maths.clamp(player.getJumpDistance() - (jumpDistance+1), 0.8f, 5f);
            player.moveHeight(jumpSpeed);
            jumpDistance += jumpSpeed;
        }else{
            stopJump();
        }

    }
    public void stopJump(){
        isJumping = false;
        jumpCoolDown = LivingEntity.DEFAULT_JUMP_COOLDOWN;
    }

    public void moveView(int yaw, float pitch, float roll){
        float sensibility = OcularEntity.DEFAULT_SENSIBILITY / 50 * getPlayer().getSensibilityFactor();
        getPlayer().moveView(-yaw/sensibility, -pitch/sensibility, -roll/sensibility);
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
}
