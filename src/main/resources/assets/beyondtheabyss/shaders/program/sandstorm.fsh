#version 330

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;
uniform sampler2D ImageSampler;
uniform sampler2D SandMaskSampler;

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

#define NEAR 0.05
#define FAR_PLANE 1000.0
#define MARCH_CAP 520.0
#define FOG_STEPS 112

float linearizeDepth(float depth) {
    float z = depth * 2.0 - 1.0;
    return (2.0 * NEAR * FAR_PLANE) / (FAR_PLANE + NEAR - z * (FAR_PLANE - NEAR));
}

float getDensity(vec3 worldPos) {
    vec2 uv = (worldPos.xz - MaskOriginXZ) / MaskExtentXZ;

    if (any(lessThan(uv, vec2(0.0))) || any(greaterThan(uv, vec2(1.0)))) {
        return 0.0;
    }

    vec4 samp = texture(SandMaskSampler, uv);
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

float tri(float x) {
    return abs(fract(x) - 0.5);
}

vec3 tri3(vec3 p) {
    return vec3(
        tri(p.z + tri(p.y)),
        tri(p.z + tri(p.x)),
        tri(p.y + tri(p.x)));
}

float Noise3d(vec3 p) {
    float z = 1.4;
    float rz = 0.0;
    vec3 bp = p;
    for (int i = 0; i < 3; i++) {
        vec3 dg = tri3(bp);
        p += dg;
        bp *= 2.0;
        z *= 1.5;
        p *= 1.3;
        rz += tri(p.z + tri(p.x + tri(p.y))) / z;
        bp += 0.14;
    }
    return rz;
}

float fogmap(vec3 p, float d) {
    p.xz -= iTime * 7.0 + sin(p.z * 0.3) * 3.0;
    p.y -= iTime * 0.5;
    return (max(Noise3d(p * 0.008 + 0.1) - 0.1, 0.0) * Noise3d(p * 0.1)) * 0.3;
}

float sandSparkle(vec3 p) {
    float n = Noise3d(p * 0.42 + vec3(iTime * 3.1, iTime * 0.4, iTime * 2.6));
    return 0.55 + 0.45 * n * n;
}

void marchSand(vec3 ro, vec3 rd, out float drift, float linearDepth) {
    float rayLen = min(linearDepth, MARCH_CAP);
    drift = 0.0;
    if (rayLen < 1e-4) {
        return;
    }

    float dt = rayLen / float(FOG_STEPS);

    for (int i = 0; i < FOG_STEPS; i++) {
        float t = (float(i) + 0.5) * dt;
        vec3 p = ro + rd * t;
        float shell = getDensity(p);
        if (shell < 1e-6) {
            continue;
        }
        drift += shell * fogmap(p, t) * sandSparkle(p) * dt;
    }

    drift = min(drift, 1.0);
}

void main() {
    vec3 ro = near_4.xyz / near_4.w;
    vec3 rd = normalize(far_4.xyz / far_4.w - ro);

    float depth = linearizeDepth(texture(DepthSampler, texCoord).r);

    float fg;
    marchSand(ro, rd, fg, depth);

    vec3 col = texture(DiffuseSampler, texCoord).rgb;
    vec3 fogColor = vec3(0.784, 0.604, 0.373);

    col = mix(col, fogColor, fg);
    fragColor = vec4(col, 1.0);
}
