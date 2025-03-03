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

void main() {
	vec3 origin = near_4.xyz/near_4.w;  //ray's origin
    vec3 far3 = far_4.xyz/far_4.w;
    vec3 dir = far3 - origin;
    dir = normalize(dir);        //ray's direction
	
	vec3 ro = origin;
	vec3 rd = dir;
    
    vec3 col = texture(DiffuseSampler, texCoord).xyz;

	fragColor = vec4( col, 1.0 );
}