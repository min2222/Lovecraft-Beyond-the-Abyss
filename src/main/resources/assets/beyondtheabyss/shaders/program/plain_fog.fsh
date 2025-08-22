#version 330

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;
uniform sampler2D ImageSampler;
uniform sampler2D FogSampler;

uniform ivec2 iResolution;
uniform vec2 OutSize;
uniform float iTime;
uniform mat4 InverseTransformMatrix;
uniform mat4 ViewMatrix;
uniform mat4 ProjectionMatrix;

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

void main() {
    vec3 ro = near_4.xyz / near_4.w;
    vec3 rd = normalize(far_4.xyz / far_4.w - ro);
    
	vec3 fogColor = vec3(0.7, 0.75, 0.8);
	float fogDensity = 0.025;
    
    vec3 col = texture(DiffuseSampler, texCoord).xyz;
    float mask = texture(FogSampler, texCoord).r;
	float depth = texture(DepthSampler, texCoord).r;
	float linearDepth = linearizeDepth(depth);

	vec2 ndc = texCoord * 2.0 - 1.0;
	vec4 clipPos = vec4(ndc, depth, 1.0);
	vec4 worldPosH = InverseTransformMatrix * clipPos;
	vec3 worldPos = worldPosH.xyz / worldPosH.w;
	
	vec4 clipPos2 = ProjectionMatrix * ViewMatrix * vec4(worldPos, 1.0);
	float worldDepth = clipPos2.z / clipPos2.w * 0.5 + 0.5;
	
	float noiseValue = noise((texCoord * iResolution + iTime * 0.05) * 0.1);
	float fogFactor = 1.0 - exp(-fogDensity * linearDepth * (1.0 + noiseValue * 0.1));
	fogFactor *= mask;
	
	if(worldDepth < depth + 0.001) {
	    col = mix(col, fogColor, fogFactor);
	}

    fragColor = vec4(col, 1.0);
}