#version 150

in vec3 position;
in vec2 textureCoord;
in vec3 normal;

out vec2 pass_textureCoord;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCameraVector;
out float visibility;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

uniform vec3 lightPosition;
uniform float useFakeLightning;
uniform float useLightningShader;

const float density = 0.007;
const float fogGradient = 1.5;

void main(void){

    vec4 worldPosition = transformationMatrix * vec4(position.x, position.y, position.z, 1.0); // calcule la position réelle du vertex
    vec4 worldPositionRelativeToCamera = viewMatrix * worldPosition; // Calcule la position de l'objet avec la caméra comme origine (N'utilise pas la projection matrix)

    gl_Position = projectionMatrix * viewMatrix * worldPosition; // Définis la position en fonction de la viewMatrix et de la projectionmatrix
    pass_textureCoord = textureCoord; // Variable qui passe les coordonés de texture pour le fragment shader

    if(useFakeLightning == 1){
        surfaceNormal = (transformationMatrix * vec4(0, 1, 0, 0)).xyz;
    }else surfaceNormal = (transformationMatrix * vec4(normal, 0.0)).xyz; // calcule la normale (réele) du vertex (Variable normal multiplié à la transformationMatrix)

    toLightVector = lightPosition - worldPosition.xyz; // calcule un vecteur qui pointe vers la lampe (Position de la lampe - celle de l'objet)
    toCameraVector = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz; // calcule un vecteur qui va vers la camera (Position de la caméra - celle de l'objet)

    if(useLightningShader == 1){
        // FOG : Calcul visibility

        float distanceToCamera = length(worldPositionRelativeToCamera.xyz);
        visibility = exp(-pow((distanceToCamera*density), fogGradient));
        visibility = clamp(visibility, 0, 1);
    }else visibility = 1;



}
