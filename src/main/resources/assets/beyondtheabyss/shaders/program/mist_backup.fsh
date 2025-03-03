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

#define time iTime
#define MOD2 vec2(.16632,.17369)

float smin( float a, float b)
{
	const float k = 2.7;
	float h = clamp( 0.5 + 0.5*(b-a)/k, 0.0, 1.0 );
	return mix( b, a, h ) - k*h*(1.0-h);
}

float tri(in float x){return abs(fract(x)-.5);}

float hash12(vec2 p)
{
	p  = fract(p * MOD2);
    p += dot(p.xy, p.yx+19.19);
    return fract(p.x * p.y);
}

vec3 tri3(in vec3 p){return vec3( tri(p.z+tri(p.y)), tri(p.z+tri(p.x)), tri(p.y+tri(p.x)));}
float Noise3d(in vec3 p)
{
    float z=1.4;
	float rz = 0.;
    vec3 bp = p;
	for (float i=0.; i<= 2.; i++ )
	{
        vec3 dg = tri3(bp);
        p += (dg);

        bp *= 2.;
		z *= 1.5;
		p *= 1.3;
        
        rz+= (tri(p.z+tri(p.x+tri(p.y))))/z;
        bp += 0.14;
	}
	return rz;
}

float map(vec3 p)
{
    return p.y;
}

float fogmap(in vec3 p, in float d)
{
    p.xz -= time*7.+sin(p.z*.3)*3.;
    p.y -= time*.5;
    return (max(Noise3d(p*.008+.1)-.1,0.0)*Noise3d(p*.1))*.3;
}

float march(in vec3 ro, in vec3 rd, out float drift, in vec2 scUV)
{
    float depth = linearizeDepth(texture(DepthSampler, texCoord).r);
	float precis = 0.1;
    float mul = .34;
    float h;
    float d = hash12(scUV)*1.5;
    drift = 0.0;
    for( int i=0; i<5; i++ )
    {
        vec3 p = ro+rd*d;
        h = map(p);
        if(h < precis*(1.0+d*.05) || d > 50 || d > depth) break;
        drift +=  fogmap(p, d) * 3.;
        d += h*mul;
        mul+=.004;
	 }
    drift = min(drift, 1.0);
	return d;
}

void main() {
	vec3 origin = near_4.xyz/near_4.w;  //ray's origin
    vec3 far3 = far_4.xyz/far_4.w;
    vec3 dir = far3 - origin;
    dir = normalize(dir);        //ray's direction
	
	vec3 ro = origin;
 
	vec3 rd = dir;
	
    float fg;
	float rz = march(ro,rd, fg, texCoord);
    
    vec3 col = texture(DiffuseSampler, texCoord).xyz;

    // Fog mix...
    col = mix(col, vec3(0.6, .65, .7), fg);
    
	fragColor = vec4( col, 1.0 );
}