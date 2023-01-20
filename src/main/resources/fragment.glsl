#version 330 core
in vec3 pos;
in vec2 tex;


uniform float time = 0;
uniform sampler2D img;


void main()
{
    gl_FragColor = texture(img, tex);

}