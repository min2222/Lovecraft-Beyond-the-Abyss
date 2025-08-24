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

int unpackRGBAtoInt(vec4 color) {
    ivec4 c = ivec4(color * 255.0 + 0.5);
    return (c.r & 0xFF) | ((c.g & 0xFF) << 8) | ((c.b & 0xFF) << 16) | ((c.a & 0xFF) << 24);
}

void getChunkAabb(int index, out vec3 bmin, out vec3 bmax) {
    int texW = textureSize(FogSampler, 0).x;
    int base = index * 6;

    int minX = unpackRGBAtoInt(texelFetch(FogSampler, ivec2((base+0)%texW, (base+0)/texW), 0));
    int minY = unpackRGBAtoInt(texelFetch(FogSampler, ivec2((base+1)%texW, (base+1)/texW), 0));
    int minZ = unpackRGBAtoInt(texelFetch(FogSampler, ivec2((base+2)%texW, (base+2)/texW), 0));

    int maxX = unpackRGBAtoInt(texelFetch(FogSampler, ivec2((base+3)%texW, (base+3)/texW), 0));
    int maxY = unpackRGBAtoInt(texelFetch(FogSampler, ivec2((base+4)%texW, (base+4)/texW), 0));
    int maxZ = unpackRGBAtoInt(texelFetch(FogSampler, ivec2((base+5)%texW, (base+5)/texW), 0));

    bmin = vec3(minX, minY, minZ) - 0.05;
    bmax = vec3(maxX, maxY, maxZ) - 0.05;
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
	    if(t > linearDepth) break;
	    t += d;
	}
	
	if(t < linearDepth) {
		outCol = mix(outCol, fogColor, fogFactor);
	}

    fragColor = vec4(outCol, 1.0);
}