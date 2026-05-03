#version 330

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;
uniform sampler2D ImageSampler;
uniform sampler2D FogMaskSampler;

uniform ivec2 iResolution;
uniform vec2 OutSize;
uniform float iTime;

uniform vec2 MaskOriginXZ;
uniform vec2 MaskExtentXZ;
uniform float MaskYMin;
uniform float MaskYMax;
uniform float CameraWorldY;

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
    vec2 uv = (worldPos.xz - MaskOriginXZ) / MaskExtentXZ;

    if (any(lessThan(uv, vec2(0.0))) || any(greaterThan(uv, vec2(1.0)))) {
        return 0.0;
    }

    vec4 samp = texture(FogMaskSampler, uv);
    float mask = samp.r;
    if (mask < 0.001) {
        return 0.0;
    }

    float surfaceY = mix(MaskYMin, MaskYMax, samp.a);
    float absWorldY = worldPos.y + CameraWorldY;
    if (absWorldY < surfaceY + 1.0) {
        return 0.0;
    }

    float edge = 0.004;
    float fadeX = smoothstep(0.0, edge, uv.x) * (1.0 - smoothstep(1.0 - edge, 1.0, uv.x));
    float fadeY = smoothstep(0.0, edge, uv.y) * (1.0 - smoothstep(1.0 - edge, 1.0, uv.y));

    return mask * fadeX * fadeY;
}

void main() {
    vec3 ro = near_4.xyz / near_4.w;
    vec3 rd = normalize(far_4.xyz / far_4.w - ro);
    
	vec3 fogColor = vec3(0.7, 0.75, 0.8);
	float fogDensity = 0.05;
    
    vec3 col = texture(DiffuseSampler, texCoord).xyz;

	float depth = texture(DepthSampler, texCoord).r;
    float linearDepth = linearizeDepth(depth);
    float rayLen = min(linearDepth, 520.0);

    float accumulatedDensity = 0.0;
    const int STEP_COUNT = 160;
    float dt = rayLen / float(STEP_COUNT);

    for (int i = 0; i < STEP_COUNT; i++) {
    	float t = (float(i) + 0.5) * dt;
    	vec3 p = ro + rd * t;
    	accumulatedDensity += getDensity(p) * fogDensity * dt;
    }
    
    float noiseValue = noise((texCoord * iResolution + iTime * 0.05) * 0.1);
    float fogFactor = 1.0 - exp(-accumulatedDensity * (1.0 + noiseValue * 0.1));
    
    // Mix the original color with the fog color
    col = mix(col, fogColor, fogFactor);

    fragColor = vec4(col, 1.0);
}
