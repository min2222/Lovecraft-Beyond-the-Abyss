#version 330

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;
uniform sampler2D ImageSampler;

uniform ivec2 iResolution;
uniform vec2 OutSize;
uniform vec3 PlayerPos;
uniform float iTime;
uniform int ViewDist;

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

// Standard SDF for a vertical cylinder
// h.x is radius, h.y is half height
float sdCylinder(vec3 p, vec3 pos, vec2 h) {
    vec3 d = abs(p - pos);
    float dist_xz = length(d.xz) - h.x;
    float dist_y = abs(d.y) - h.y;
    return max(dist_xz, dist_y);
}

// Ray-Cylinder Intersection (simplified)
// Returns distance to intersection, or -1.0 if no intersection
float intersectCylinder(vec3 ro, vec3 rd, vec3 pos, float radius, float height) {
    vec3 oc = ro - pos;
    vec2 rc = oc.xz;
    vec2 rdc = rd.xz;

    float a = dot(rdc, rdc);
    float b = 2.0 * dot(rc, rdc);
    float c = dot(rc, rc) - radius * radius;
    float discriminant = b * b - 4.0 * a * c;

    if (discriminant < 0.0) {
        return -1.0;
    }

    float sqrtD = sqrt(discriminant);
    float t1 = (-b - sqrtD) / (2.0 * a);
    float t2 = (-b + sqrtD) / (2.0 * a);
    
    // Check for valid intersection within the cylinder height
    if (t1 > 0.0) {
        vec3 hitPoint = ro + t1 * rd;
        if (abs(hitPoint.y - pos.y) <= height / 2.0) {
            return t1;
        }
    }
    if (t2 > 0.0) {
        vec3 hitPoint = ro + t2 * rd;
        if (abs(hitPoint.y - pos.y) <= height / 2.0) {
            return t2;
        }
    }

    return -1.0;
}

void main() {
	vec3 origin = near_4.xyz/near_4.w;
    vec3 far3 = far_4.xyz/far_4.w;
    vec3 dir = far3 - origin;
    dir = normalize(dir);
	
	vec3 ro = origin;
	vec3 rd = dir;
	
	vec3 fogColor = vec3(0.7, 0.75, 0.8);
	float fogDensity = 0.025F;
    
    vec3 col = texture(DiffuseSampler, texCoord).xyz;
	float depth = texture(DepthSampler, texCoord).r;
    float linearDepth = linearizeDepth(depth);
    
    // Define cylinder properties (you can pass these as uniforms)
    vec3 cylinderPos = vec3(0.0, 0.0, 0.0);
    float cylinderRadius = 9350.0;
    float cylinderHeight = 5000.0;
    
    // Check if player is inside the cylinder
    float playerDistFromCenter = length(PlayerPos.xz - cylinderPos.xz);
    bool isPlayerInside = playerDistFromCenter < cylinderRadius && abs(PlayerPos.y - cylinderPos.y) < cylinderHeight/2.0;

    float fogFactor = 0.0;
    
    int viewDist = ViewDist * 16;
    float dynamicFogDensity = fogDensity;
    if (isPlayerInside) {
        // Calculate the distance from the player to the surface of the cylinder
        float playerDistFromSurface = cylinderRadius - playerDistFromCenter;
        
        // Define a fade range
        // Fog is max when player is close to the surface (e.g., within 5 units)
        // Fog is completely gone when player is far from the surface (e.g., 200 units away)
        float fadeEnd = float(viewDist);
        float fadeStart = fadeEnd - (fadeEnd * 0.1);
        
        // Use smoothstep to create a smooth fade effect based on distance from the surface
        float fogFade = smoothstep(fadeEnd, fadeStart, playerDistFromSurface);
        dynamicFogDensity = fogDensity * fogFade;
    }

    if (isPlayerInside) {
        float distToCylinder = intersectCylinder(ro, rd, cylinderPos, cylinderRadius, cylinderHeight);
        
        if (distToCylinder > 0.0) {
            if (linearDepth > distToCylinder) {
                float fogDistance = linearDepth - distToCylinder;
                float noiseValue = noise((texCoord * iResolution + iTime * 0.05) * 0.1);
                fogFactor = 1.0 - exp(-dynamicFogDensity * fogDistance * (1.0 + noiseValue * 0.1));
            }
        } else {
            fogFactor = 0.0;
        }
    } else {
        float noiseValue = noise((texCoord * iResolution + iTime * 0.05) * 0.1);
        fogFactor = 1.0 - exp(-fogDensity * linearDepth * (1.0 + noiseValue * 0.1));
    }
    
    col = mix(col, fogColor, fogFactor);
    fragColor = vec4(col, 1.0);
}