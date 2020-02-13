#version 150

in vec2 pass_textureCoord;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;

out vec4 out_Color;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

uniform vec3 skyColour;
uniform vec3 lightColour;
uniform float shineDamper;
uniform float reflectivity;

void main(void){

    // TEXTURE

    vec4 blendMapColour = texture(blendMap, pass_textureCoord); // Récupère la couleur de la blendMap qui correspond à ce pixel
    vec2 tiledCoords = pass_textureCoord * 40.0; // Scale les coordonés de texture pour répéter les textures

    // Calcule les couleurs de chacunes des textures en fonction de la BlendMap
    vec4 backTextureColour = texture(backgroundTexture, tiledCoords) * (1 - blendMapColour.r - blendMapColour.g - blendMapColour.b);
    vec4 rTextureColour = texture(rTexture, tiledCoords) * blendMapColour.r;
    vec4 gTextureColour = texture(gTexture, tiledCoords) * blendMapColour.g;
    vec4 bTextureColour = texture(bTexture, tiledCoords) * blendMapColour.b;
    vec4 totalColour = backTextureColour + rTextureColour + gTextureColour + bTextureColour; // Calcule la couleur finale à partir des couleurs des différentes textures


    // NORMALIZE LES VARIABLES IN

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightVector = normalize(toLightVector);
    vec3 unitVectorToCamera = normalize(toCameraVector);

    // LAMPE

    float nDotl = (dot(unitNormal, unitLightVector)+1.5) / 2; // Calcule l'intensité de ressemblance entre la direction pointée des deux vecteurs (Fonction dot), entre la normale et la direction de la lampe
    float brightness = clamp(nDotl, 0.2, 1.2); // permet de ne pas avoir de valeurs inférieures à 0.2. (Ambient lightning)

    vec3 diffuse = brightness * lightColour; // Calcule l'intensité de la couleur diffusé par la lampe.

    // REFLECTS

    vec3 lightDirection = -unitLightVector; // calcule le vecteur qui pointe de la lampe vers le vertex, inverse de unitLightVEctor (qui pointe la lampe).
    vec3 reflectedLightDirection = reflect(lightDirection, unitNormal); // calcule le vecteur de reflet de lightDirection par unitNormal

    float specularFactor = dot(reflectedLightDirection, unitVectorToCamera); // Calcule l'intensité de ressemblance entre la direction pointée des deux vecteurs (Fonction dot), entre le vecteur de reflexion et celui qui pointe vers la camera
    specularFactor = max(specularFactor, 0.0); // Permet de ne pas avoir de valeurs négatives

    float dampedFactor = pow(specularFactor, shineDamper); // pow : met le premier argument à la puissance du second (pow(4, 2) renvoie 4^2 = 16) : shineDamper est la variable qui définis si le seuil de la taille du reflet, plus il est petit, plus le reflet seras grand
    vec3 finalSpecular = dampedFactor * reflectivity * lightColour; // On multiplie les rgb values de la couleurs de base par les accentiateurs.

    out_Color = vec4(diffuse, 1.0) * totalColour + vec4(finalSpecular, 1.0); // Définis la couleur finale du pixel en fonction de la texture, de la lampe et du reflet (addition).
    out_Color = mix(vec4(skyColour, 1), out_Color, visibility); // Mélange la couleur du fog avec la couleur finale en fonction de la visibility

}
