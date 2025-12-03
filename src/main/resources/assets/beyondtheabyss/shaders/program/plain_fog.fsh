#version 330

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;
uniform sampler2D SceneSampler;

uniform vec2 iResolution;
uniform vec2 OutSize;
uniform float iTime;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

#define near 0.05
#define far 1000.0
float linearizeDepth(float depth) {
    float z = depth * 2.0 - 1.0;
    return (2.0 * near * far) / (far + near - z * (far - near));
}

float noise(vec2 coord) {
    return fract(sin(dot(coord, vec2(12.9898, 78.233))) * 43758.5453);
}

void main() {
	vec3 fogColor = vec3(0.7, 0.75, 0.8);
	float fogDensity = 0.025;
    
    vec4 col = texture(DiffuseSampler, texCoord);
    vec4 scene = texture(SceneSampler, texCoord);
    vec3 bg = scene.xyz;
    float alpha = col.a;
    float sceneAlpha = scene.a;
	float depth = texture(DepthSampler, texCoord).r;
    
    // Linearize the depth
    float linearDepth = linearizeDepth(depth);
    
    // Calculate fog factor
    float noiseValue = noise((texCoord * iResolution + iTime * 0.05) * 0.1);
    float fogFactor = 1.0 - exp(-fogDensity * linearDepth * (1.0 + noiseValue * 0.1));
    
    // Mix the original color with the fog color
    bg = mix(bg, fogColor, fogFactor);
    
    fragColor = vec4(bg, alpha);
}