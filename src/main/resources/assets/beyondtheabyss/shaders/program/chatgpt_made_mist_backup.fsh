#version 330

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;
uniform sampler2D ImageSampler;

uniform ivec2 iResolution;
uniform vec2 OutSize;
uniform float iTime;

in vec2 texCoord;
in vec4 near_4;
in vec4 far_4;

out vec4 fragColor;

#define near 0.05
#define far 1000.0
#define PI 3.14159265359

float hash(vec2 p) {
    return fract(sin(dot(p, vec2(127.1, 311.7))) * 43758.5453123);
}

float noise(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);
    vec2 u = f * f * (3.0 - 2.0 * f);

    float n = mix(mix(hash(i + vec2(0.0, 0.0)), hash(i + vec2(1.0, 0.0)), u.x),
                  mix(hash(i + vec2(0.0, 1.0)), hash(i + vec2(1.0, 1.0)), u.x), u.y);
    return n;
}

float fbm(vec2 p) {
    float value = 0.0;
    float amplitude = 0.5;
    float frequency = 1.0;
    for (int i = 0; i < 6; i++) {
        value += amplitude * noise(p * frequency);
        frequency *= 2.0;
        amplitude *= 0.5;
    }
    return value;
}

float linearizeDepth(float depth) {
    float z = depth * 2.0 - 1.0;
    return (2.0 * near * far) / (far + near - z * (far - near));
}

float mistDensity(vec3 pos) {
    float density = fbm(pos.xz * 0.1 + iTime * 0.1);
    return clamp(density - pos.y * 0.03, 0.0, 1.0);
}

void main() {
    vec3 origin = near_4.xyz / near_4.w;  // Ray's origin
    vec3 far3 = far_4.xyz / far_4.w;
    vec3 dir = normalize(far3 - origin);  // Ray's direction

    vec3 ro = origin;
    vec3 rd = dir;
    vec3 col = texture(DiffuseSampler, texCoord).xyz;

    float depth = texture(DepthSampler, texCoord).r;
    if (depth < 1.0) {
        float dist = linearizeDepth(depth);
        vec3 hitPos = ro + rd * dist;
        float mist = mistDensity(hitPos);
        vec3 mistColor = vec3(0.7, 0.75, 0.8);
        col = mix(col, mistColor, mist);
    }

    fragColor = vec4(col, 1.0);
}