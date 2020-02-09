#version 150

in vec2 pass_textureCoord;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 out_Color;

uniform sampler2D textureSample;
uniform vec3 lightColour;
uniform float shineDamper;
uniform float reflectivity;
uniform float useLightningShader;

void main(void){

    // NORMALIZE TOUTE (OU PRESQUE) LES VARIABLES IN
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);
    vec3 unitVectorToCamera = normalize(toCameraVector);

    vec3 diffuse = vec3(1, 1, 1);
    vec3 finalSpecular = vec3(0, 0, 0);
    if(useLightningShader == 1){
        // LAMPE
        float nDotl = (dot(unitNormal, unitLightVector)+1.5) / 2; // Calcule l'intensité de ressemblance entre la direction pointée des deux vecteurs (Fonction dot), entre la normale et la direction de la lampe
        float brightness = max(min(1.2, nDotl), 0.2); // permet de ne pas avoir de valeurs inférieures à 0.2. (Ambient lightning)

        diffuse = brightness * lightColour; // Calcule l'intensité de la couleur diffusé par la lampe.

        // REFLECTS
        vec3 lightDirection = -unitLightVector; // calcule le vecteur qui pointe de la lampe vers le vertex, inverse de unitLightVEctor (qui pointe la lampe).
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal); // calcule le vecteur de reflet de lightDirection par unitNormal

        float specularFactor = dot(reflectedLightDirection, unitVectorToCamera); // Calcule l'intensité de ressemblance entre la direction pointée des deux vecteurs (Fonction dot), entre le vecteur de reflexion et celui qui pointe vers la camera
        specularFactor = max(specularFactor, 0.0); // Permet de ne pas avoir de valeurs négatives

        float dampedFactor = pow(specularFactor, shineDamper); // pow : met le premier argument à la puissance du second (pow(4, 2) renvoie 4^2 = 16) : shineDamper est la variable qui définis si le seuil de la taille du reflet, plus il est petit, plus le reflet seras grand
        finalSpecular = dampedFactor * reflectivity * lightColour; // On multiplie les rgb values de la couleurs de base par les accentiateurs.
    }

    // TRANSPARENCY
    vec4 textureColour = texture(textureSample, pass_textureCoord);
    if(textureColour.a < 0.5){
        discard;
    }



    // OUT

    out_Color = vec4(diffuse, 1.0) * textureColour + vec4(finalSpecular, 1.0); // Définis la couleur finale du pixel en fonction de la texture, de la lampe et du reflet (addition).

}
