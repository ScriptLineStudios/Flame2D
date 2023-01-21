#version 330 core
uniform sampler2D img;

in vec2 outCoord;
//uniform sampler2D img;

void main()
{
    gl_FragColor = texture(img, outCoord);
//    gl_FragColor = vec4(1.0f, 1.0f, 0.0f, 1.0f);
}