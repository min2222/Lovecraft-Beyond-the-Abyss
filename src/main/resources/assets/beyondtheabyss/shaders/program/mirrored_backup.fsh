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
float linearizeDepth(float depth) {
    float z = depth * 2.0 - 1.0;
    return (2.0 * near * far) / (far + near - z * (far - near));
}

bool intersect(vec3 ro, vec3 rd, out float t) {
    // Dummy raymarching intersection for testing (always miss)
    t = 1e10; // Large distance means no intersection
    return false;
}

void main() {
    vec3 origin = near_4.xyz / near_4.w;  // Ray Origin
    vec3 far3 = far_4.xyz / far_4.w;      // Ray Far Point
    vec3 dir = normalize(far3 - origin);  // Ray Direction

    vec3 col = vec3(0.0);
    float t;

    if (intersect(origin, dir, t)) {
        // Future Raymarching Intersection
        col = vec3(1.0, 0.0, 0.0); // Test Red Color
    } else {
        // Sample Normal Background
        vec3 skyNormal = texture(DiffuseSampler, texCoord).rgb;

        // Sample Upside-Down Background
        vec2 uvFlip = vec2(texCoord.x, 1.0 - texCoord.y);
        vec3 skyUpsideDown = texture(DiffuseSampler, uvFlip).rgb;

        // Smooth Horizon Gradient (Inverted for Top-Down Effect)
        float gradient = smoothstep(0.4, 0.6, 1.0 - texCoord.y);

        // Mix Both Backgrounds
        col = mix(skyUpsideDown, skyNormal, gradient);
    }

    fragColor = vec4(col, 1.0);
}