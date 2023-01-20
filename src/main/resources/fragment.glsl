#version 330 core
in vec3 pos;
uniform float time = 0;


void main()
{
    gl_FragColor = vec4(pos.x * sin(time), pos.y * cos(time), pos.z, 1.0);
}