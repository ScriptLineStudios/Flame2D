#version 330 core
layout (location=0) in vec3 aPos;
layout (loaction=1) in vec2 texCoord;

out vec2 tex;
out vec3 pos;

void main()
{
    gl_Position = vec4(aPos, 1.0);
    pos = aPos;
    tex = texCoord;
}
