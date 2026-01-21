#version 330

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;
uniform sampler2D ImageSampler;
uniform sampler3D FogVolumeSampler;

uniform ivec2 iResolution;
uniform vec2 OutSize;
uniform float iTime;

uniform vec3 VolumeSize;

in vec2 texCoord;
in vec4 near_4;
in vec4 far_4;

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

float getDensity(vec3 worldPos) {
    vec3 tex_coord = (worldPos / VolumeSize) + 0.5;

    if (any(lessThan(tex_coord, vec3(0.0))) || any(greaterThan(tex_coord, vec3(1.0)))) {
        return 0.0;
    }

    float density = texture(FogVolumeSampler, tex_coord).r;
    
    float fade = 0.1;
    float fadeX = smoothstep(0.0, fade, tex_coord.x) * (1.0 - smoothstep(1.0 - fade, 1.0, tex_coord.x));
    float fadeY = smoothstep(0.0, fade, tex_coord.y) * (1.0 - smoothstep(1.0 - fade, 1.0, tex_coord.y));
    float fadeZ = smoothstep(0.0, fade, tex_coord.z) * (1.0 - smoothstep(1.0 - fade, 1.0, tex_coord.z));
    
    return density * fadeX * fadeY * fadeZ;
}

void main() {
    vec3 ro = near_4.xyz / near_4.w;
    vec3 rd = normalize(far_4.xyz / far_4.w - ro);
    
	vec3 fogColor = vec3(0.7, 0.75, 0.8);
	float fogDensity = 0.0025;
    
    vec3 col = texture(DiffuseSampler, texCoord).xyz;

	float depth = texture(DepthSampler, texCoord).r;
    
    // Linearize the depth
    float linearDepth = linearizeDepth(depth);
    
    float accumulatedDensity = 0.0;
    
    for (int i = 0; i < 100; i++) {
    	vec3 p = ro + rd * i;
    	float density = getDensity(p);
    	accumulatedDensity += density * fogDensity;
    	if (i > linearDepth) break;
    }
    
    // Calculate fog factor
    float noiseValue = noise((texCoord * iResolution + iTime * 0.05) * 0.1);
    float fogFactor = 1.0 - exp(-accumulatedDensity * linearDepth * (1.0 + noiseValue * 0.1));
    
    // Mix the original color with the fog color
    col = mix(col, fogColor, fogFactor);

    fragColor = vec4(col, 1.0);
}