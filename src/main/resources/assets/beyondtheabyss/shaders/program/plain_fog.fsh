#version 330

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;
uniform sampler2D ImageSampler;
uniform sampler2D FogSampler;

uniform ivec2 iResolution;
uniform vec2 OutSize;
uniform float iTime;
uniform int ChunkCount;

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

void getChunkAabb(int index, out vec3 bmin, out vec3 bmax) {
    int texW = textureSize(FogSampler, 0).x;
    int base = index * 2;

    int x0 = base % texW;
    int y0 = base / texW;

    int x1 = (base + 1) % texW;
    int y1 = (base + 1) / texW;

    bmin = texelFetch(FogSampler, ivec2(x0, y0), 0).xyz - 0.05;
    bmax = texelFetch(FogSampler, ivec2(x1, y1), 0).xyz - 0.05;
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

void main() {
    vec3 ro = near_4.xyz / near_4.w;
    vec3 rd = normalize(far_4.xyz / far_4.w - ro);

    vec3 col = texture(DiffuseSampler, texCoord).rgb;
    float depth = texture(DepthSampler, texCoord).r;
    float linearDepth = linearizeDepth(depth);
    
    vec3 outCol = col;

    vec3 fogColor = vec3(0.7, 0.75, 0.8);
	float fogDensity = 0.025;
	
	float noiseValue = noise((texCoord * iResolution + iTime * 0.05) * 0.1);
	float fogFactor = 1.0 - exp(-fogDensity * linearDepth * (1.0 + noiseValue * 0.1));
	
    float bestTnear = 1e20;
    float bestTfar = -1e20;
    vec3 bestMin = vec3(0.0), bestMax = vec3(0.0);

    for (int i = 0; i < ChunkCount; ++i) {
        vec3 bmin, bmax;
        getChunkAabb(i, bmin, bmax);

        float t0, t1;
        if (rayAABB(ro, rd, bmin, bmax, t0, t1)) {
			float tFarClamp = min(t1, linearDepth);
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
	
	float t = 0.0;
	for(int i = 0; i < 20; i++) {
	    vec3 p = ro + rd * t;
		float d = boxSDF(p, center, extend);
	    if(t > 20.0 || t > linearDepth) break;
	    t += d;
	}
	
	if(t < 20.0) {
		if(t < linearDepth) {
			outCol = mix(outCol, fogColor, fogFactor);
		}
	}

    fragColor = vec4(outCol, 1.0);
}