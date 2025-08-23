#version 330

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;
uniform sampler2D ImageSampler;
uniform sampler2D SandSampler;

uniform ivec2 iResolution;
uniform vec2 OutSize;
uniform float iTime;
uniform int ChunkCount;

in vec2 texCoord;
in vec4 near_4;
in vec4 far_4;

out vec4 fragColor;

#define NEAR 0.05
#define FAR 1000.0

float linearizeDepth(float depth) {
    float z = depth * 2.0 - 1.0;
    return (2.0 * NEAR * FAR) / (FAR + NEAR - z * (FAR - NEAR));
}

#define MOD2 vec2(0.16632, 0.17369)

float tri(float x) {
    return abs(fract(x) - 0.5);
}

float hash12(vec2 p) {
    p = fract(p * MOD2);
    p += dot(p.xy, p.yx + 19.19);
    return fract(p.x * p.y);
}

vec3 tri3(vec3 p) {
    float tx = tri(p.z + tri(p.y));
    float ty = tri(p.z + tri(p.x));
    float tz = tri(p.y + tri(p.x));
    return vec3(tx, ty, tz);
}

// Optimized 3D Noise: fewer iterations, reduced complexity
float Noise3d(vec3 p) {
    float z = 1.4;
    float rz = 0.0;
    vec3 bp = p;
    for (int i = 0; i < 2; i++) {
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

float map(vec3 p) {
    return p.y;
}

void getChunkAabb(int index, out vec3 bmin, out vec3 bmax) {
    int texW = textureSize(SandSampler, 0).x;
    int base = index * 2;

    int x0 = base % texW;
    int y0 = base / texW;

    int x1 = (base + 1) % texW;
    int y1 = (base + 1) / texW;

    bmin = texelFetch(SandSampler, ivec2(x0, y0), 0).xyz - 0.05;
    bmax = texelFetch(SandSampler, ivec2(x1, y1), 0).xyz - 0.05;
}

bool rayAABB(vec3 ro, vec3 rd, vec3 bmin, vec3 bmax, out float tmin, out float tmax) {
    vec3 invD = 1.0 / rd;
    vec3 t0 = (bmin - ro) * invD;
    vec3 t1 = (bmax - ro) * invD;

    vec3 tsm = min(t0, t1);
    vec3 tsM = max(t0, t1);

    tmin = max(max(tsm.x, tsm.y), max(tsm.z, 0.0));
    tmax = min(min(tsM.x, tsM.y), tsM.z);

    return tmax >= tmin;
}

float boxSDF(vec3 p, vec3 c, vec3 s) {
    vec3 box = abs(p - c) - s;
	return length(max(box, 0.0)) + min(max(box.x, max(box.y, box.z)), 0.0);
}

// Fog shape with cheaper math
float fogmap(vec3 p, float d, float timeX, float timeY) {
    p.xz -= timeX;
    p.y -= timeY;
    float noiseA = Noise3d(p * 0.008 + 0.1);
    float noiseB = Noise3d(p * 0.1);
    return max(noiseA - 0.1, 0.0) * noiseB * 0.3;
}

float march(vec3 ro, vec3 rd, out float drift, vec2 scUV, float timeX, float timeY, float depth) {
    float precis = 0.1;
    float mul = 0.34;
    float h;
    float d = hash12(gl_FragCoord.xy) * 1.5;
    drift = 0.0;
    
    float bestTnear = 1e20;
    float bestTfar = -1e20;
    vec3 bestMin = vec3(0.0), bestMax = vec3(0.0);

    for (int i = 0; i < ChunkCount; ++i) {
        vec3 bmin, bmax;
        getChunkAabb(i, bmin, bmax);

        float t0, t1;
        if (rayAABB(ro, rd, bmin, bmax, t0, t1)) {
			float tFarClamp = min(t1, depth);
            if (tFarClamp >= t0 && t0 < bestTnear) {
                bestTnear = t0;
                bestTfar = tFarClamp;
                bestMin = bmin;
                bestMax = bmax;
            }
        }
    }
    
    vec3 center = (bestMin + bestMax) * 0.5;
	vec3 extend = (bestMax - bestMin) * 0.5;

    for (int i = 0; i < 20; i++) {
        vec3 p = ro + rd * d;
        h = boxSDF(p, center, extend);
        if (h < precis * (1.0 + d * 0.05) || d > 90.0 || d > depth) break;
        drift += fogmap(p, d, timeX, timeY);
        d += h * mul;
        mul += 0.004;
    }

    drift = min(drift, 1.0);
    return d;
}

void main() {
    vec3 ro = near_4.xyz / near_4.w;
    vec3 rd = normalize(far_4.xyz / far_4.w - ro);
	
	float depth = texture(DepthSampler, texCoord).r;
	float linearDepth = linearizeDepth(depth);

    float fg;
    float timeX = iTime * 7.0;
    float timeY = iTime * 0.5;

    float rz = march(ro, rd, fg, texCoord, timeX, timeY, linearDepth);
    fg = pow(fg, 0.35);  // controls fog thickness falloff

    vec3 col = texture(DiffuseSampler, texCoord).rgb;
    vec3 fogColor = vec3(0.784, 0.604, 0.373);

    col = mix(col, fogColor, fg);
    	
    fragColor = vec4(col, 1.0);
}