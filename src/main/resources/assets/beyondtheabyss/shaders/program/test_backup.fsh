#version 330

uniform sampler2D Sampler0;
uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;

uniform vec2 OutSize;

in vec2 texCoord;
in vec2 oneTexel;
in vec3 position;
in mat3 viewmat;
in vec2 proj;

out vec4 fragColor;

#define renderdistance 80
#define fogstart 40

#define near 0.05
#define far  1000.0
float linearizeDepth(float depth) {
    float z = depth * 2.0 - 1.0;
    return (2.0 * near * far) / (far + near - z * (far - near));
}

#define MAX_STEPS 100

float sdBox(vec3 p, vec3 s) {
    p = abs(p)-s;
	return length(max(p, 0.))+min(max(p.x, max(p.y, p.z)), 0.);
}

float GetDist(vec3 p) {
    float d = sdBox(p, vec3(1));
    
    return d;
}

vec3 GetNormal(vec3 p) {
    vec2 e = vec2(.001, 0);
    vec3 n = GetDist(p) - 
        vec3(GetDist(p-e.xyy), GetDist(p-e.yxy),GetDist(p-e.yyx));
    
    return normalize(n);
}

vec4 shadertoy(vec3 ro, vec3 rd, vec2 uv)
{
	vec3 col = vec3(0);
	
	float dO=0.;
	
    float noncaptured = 1.;
    
    for(int i=0; i<MAX_STEPS; i++) {
    	vec3 p = ro + rd*dO;
        float dS = GetDist(p);
        noncaptured = smoothstep(0.0, 0.666, dS);
        if (dS < 0.0001) break;
        dO += dS;
        if(dO>=100) break;
    }
   
    float d = dO;

    if(d<100) {
        vec3 p = ro + rd * d;
        vec3 n = GetNormal(p);
        vec3 r = reflect(rd, n);

        float dif = dot(n, normalize(vec3(1,2,3)))*.5+.5;
        col = vec3(dif);
    }
    
    col = pow(col, vec3(.4545));	// gamma correction
    col += pow(texture(DiffuseSampler, texCoord).rgb,vec3(1.0)) * noncaptured;
    
    return vec4(col, d);
}

vec3 render(vec3 ro, vec3 rd, vec2 uv, float fardepth, vec3 mainColor) {
    float t = shadertoy(ro, rd, uv).w;
    vec3 col = shadertoy(ro, rd, uv).xyz;
    vec3 color = mainColor;
	if (t < fardepth) {
   		color = col;
	}
	else if (t < renderdistance) {
   		color = mainColor;
   	}
    
    return color;
}

//--------------------------------------------------------------------------------
void main() {
    //data
    vec3 color = vec3(0);
    vec3 maincolor = texture(DiffuseSampler, texCoord).rgb;
    vec2 uv = (texCoord * 2 - 1);
    float depth = linearizeDepth(texture(DepthSampler, texCoord).r);

    //ray start
    vec3 ro = position;
    vec3 rd = viewmat * vec3(uv/proj,-1);
    float l = length(rd);
    rd /= l;
    depth = depth * l;
    //render
    color += render(ro, rd, uv, min(depth, renderdistance), maincolor);

	fragColor = vec4(color, 1);
}
