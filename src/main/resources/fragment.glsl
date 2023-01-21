#version 330 core
in vec3 pos;
in vec2 texCoord;


uniform float time = 0;
uniform sampler2D tex0;


void main()
{
    gl_FragColor = texture(tex0, tex);

}