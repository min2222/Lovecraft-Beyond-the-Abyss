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

float mistDensity(float height) {
    return clamp(exp(-height * 0.05) * 0.9, 0.0, 1.0);  // Increased density and height range
}

void main() {
    vec3 origin = near_4.xyz / near_4.w;  // Ray origin
    vec3 far3 = far_4.xyz / far_4.w;
    vec3 dir = normalize(far3 - origin);  // Ray direction

    vec3 ro = origin;
    vec3 rd = dir;

    vec3 col = texture(DiffuseSampler, texCoord).xyz;  // Base color from texture

    float depth = texture(DepthSampler, texCoord).r;
    float dist = linearizeDepth(depth);

    float height = ro.y + rd.y * dist;  // Approximate world height along the ray
    float mist = mistDensity(height);

    vec3 mistColor = vec3(0.7, 0.75, 0.8);  // Light bluish mist color
    col = mix(col, mistColor, mist * 0.8);  // Increased blending factor

    fragColor = vec4(col, 1.0);
}