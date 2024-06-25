#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec4 colorOriginal = texture(Sampler0, texCoord0) * vertexColor;
    vec3 cyan = vec3(colorOriginal.r, colorOriginal.g * 189, colorOriginal.b * 188);
    vec4 color = vec4(cyan, 0.35);
    fragColor = color * ColorModulator * linear_fog_fade(vertexDistance, FogStart, FogEnd);
}
