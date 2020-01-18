#version 150

in vec2 pass_textureCoords;

out vec4 out_Color;

uniform sampler2D textureSample;

void main(void){

    out_Color = texture(textureSample, pass_textureCoords);

}
