#version 150

in vec3 position;

out vec3 colour;

void main(void){

    gl_Position = vec4(position.x, position.y, position.z, 1.0);
    colour = vec3(position.x+0.5, 0, position.y+0.5);

}
