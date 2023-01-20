#version 330

layout(location = 0) in vec3 vertexPos;

void main()
{
    gl_Postion = vec4(vertexPos, 1.0);

}