package fr.themsou.entities.livingEntity.ocularEntity;

import fr.themsou.entities.livingEntity.LivingEntity;
import fr.themsou.models.TexturedModel;
import fr.themsou.movements.CollisionCalculator;
import fr.themsou.utils.Location;
import org.lwjgl.util.vector.Vector3f;

public class OcularEntity extends LivingEntity {

    public final static float DEFAULT_SENSIBILITY = 5;

    private int viewMode = 0;
    private float sensibilityFactor = 50;

    private float cameraDistance = 10;
    private float cameraHeight = 2;

    public OcularEntity(TexturedModel model, Location location, float scale) {
        super(model, location, scale);
    }

    public Location getViewLocation(){

        if(viewMode == 0){
            Vector3f back = getForward();
            Location newLoc = location.clone();
            newLoc.setPitch(viewPitch);
            newLoc.setYaw(-location.getYaw()+180);
            newLoc.increasePosition(-back.getX()/3, 2, -back.getZ()/3);

            return newLoc;
        }
        if(viewMode == 1){
            float distance = (float) (cameraDistance * Math.abs(Math.cos(Math.toRadians(viewPitch+15))));
            float height = (float) (cameraDistance * Math.sin(Math.toRadians(viewPitch+15)));

            Vector3f forward = getForward();
            float XtoZFactor = forward.getZ() / forward.getX();

            float distanceX = (float) ( distance / Math.sqrt(Math.pow(XtoZFactor, 2) + 1) * Math.signum(XtoZFactor) * Math.signum(forward.getZ()));
            float distanceZ = distanceX * XtoZFactor;

            float x = location.getX() + distanceX;
            float y = location.getY() + cameraHeight + height;
            float z = location.getZ() + distanceZ;
            float terrainHeight = CollisionCalculator.getTerrainHeight(x, z);
            y = Math.max(terrainHeight+0.5f, y);

            return new Location(x, y, z, -location.getYaw()+180, viewPitch+15);
        }
        if(viewMode == 2){

            float distance = (float) (cameraDistance * Math.abs(Math.cos(Math.toRadians(viewPitch+15))));
            float height = (float) (cameraDistance * Math.sin(Math.toRadians(viewPitch+15)));

            Vector3f forward = getForward();
            float XtoZFactor = forward.getZ() / forward.getX();

            float distanceX = -(float) ( distance / Math.sqrt(Math.pow(XtoZFactor, 2) + 1) * Math.signum(XtoZFactor) * Math.signum(forward.getZ()));
            float distanceZ = distanceX * XtoZFactor;

            float x = location.getX() + distanceX;
            float y = location.getY() + cameraHeight + height;
            float z = location.getZ() + distanceZ;
            float terrainHeight = CollisionCalculator.getTerrainHeight(x, z);
            y = Math.max(terrainHeight+0.5f, y);

            return new Location(x, y, z, -location.getYaw(), viewPitch+15);
        }
        return null;

    }

    private float get2DDistance(Location loc1, Location loc2){
        return (float) Math.sqrt(Math.pow(loc1.getX()-loc2.getX(), 2) + Math.pow(loc1.getZ()-loc2.getZ(), 2));
    }

    public void switchViewMode(){
        if(viewMode == 2){
            viewMode = 0;
        }else viewMode ++;
    }
    public int getViewMode() {
        return viewMode;
    }
    public void setViewMode(int viewMode) {
        this.viewMode = viewMode;
    }

    public float getSensibilityFactor() {
        return sensibilityFactor;
    }
    public void setSensibilityFactor(float sensibilityFactor) {
        this.sensibilityFactor = sensibilityFactor;
    }
}
