#version 150

uniform sampler2D DiffuseSampler; 
uniform sampler2D FogSampler;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec3 scene = texture(DiffuseSampler, texCoord).rgb;
    float fogFactor = texture(FogSampler, texCoord).r;
    vec3 fogColor = vec3(0.7, 0.75, 0.8);
    fragColor = vec4(mix(scene, fogColor, fogFactor), 1.0);
}