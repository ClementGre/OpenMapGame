package fr.themsou.entities;

import fr.themsou.models.TexturedModel;
import fr.themsou.utils.Location;
import org.lwjgl.util.vector.Vector3f;

public class OcularEntity extends LivingEntity{
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
            viewPitch += 20;
            Vector3f back = getForward();
            viewPitch -= 20;
            Location newLoc = location.clone();
            newLoc.setYaw(-location.getYaw()+180);
            newLoc.increasePosition(back.getX()*7, (3+back.getY()*4 <= 0.1) ? 0.1f : (3 + back.getY() * 4), back.getZ()*7);

            float distance = get2DDistance(new Location(location.getX(), location.getY()+2, location.getZ()), newLoc);
            double pitch = (Math.toDegrees(Math.atan(distance / (newLoc.getY()-location.getY())))-70)*-1;
            newLoc.setPitch((float) pitch);

            return newLoc;
        }
        if(viewMode == 2){

            viewPitch += 20;
            Vector3f back = getForward();
            viewPitch -= 20;
            Location newLoc = location.clone();
            newLoc.setYaw(-location.getYaw());
            newLoc.increasePosition(-back.getX()*7, (3+back.getY()*4 <= 0.1) ? 0.1f : (3 + back.getY() * 4), -back.getZ()*7);

            float distance = get2DDistance(new Location(location.getX(), location.getY()+2, location.getZ()), newLoc);
            double pitch = (Math.toDegrees(Math.atan(distance / (newLoc.getY()-location.getY())))-70)*-1;
            newLoc.setPitch((float) pitch);

            return newLoc;
        }
        return null;

    }

    private float get2DDistance(Location loc1, Location loc2){
        return (float) Math.sqrt(Math.pow(loc1.getX()-loc2.getX(), 2) + Math.pow(loc1.getZ()-loc2.getZ(), 2));
    }


}
