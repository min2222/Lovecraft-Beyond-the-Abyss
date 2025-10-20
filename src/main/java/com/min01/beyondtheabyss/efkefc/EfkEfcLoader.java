package com.min01.beyondtheabyss.efkefc;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.efkefc.EfkEfcRenderer.EfkEfcNode;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.entity.Entity;

public class EfkEfcLoader implements ResourceManagerReloadListener
{
	public static final Map<UUID, EfkEfcRenderer> ENTITY_RENDERERS = new HashMap<>();
	public static final Map<String, EfkEffectData> EFKEFC = new HashMap<>();

	@Override
	public void onResourceManagerReload(ResourceManager manager)
	{
		EFKEFC.clear();
		try
		{
			init(manager);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static EfkEfcRenderer getEfkEfcRenderer(Entity entity, String name, float partialTicks) 
	{
	    UUID uuid = entity.getUUID();
	    if(!entity.isAlive()) 
	    {
	        ENTITY_RENDERERS.remove(uuid);
	        return null;
	    }
	    return ENTITY_RENDERERS.computeIfAbsent(uuid, key ->
	    {
	        EfkEffectData data = EFKEFC.get(name);
	        return new EfkEfcRenderer(data.nodes, data.globalProperties, entity.tickCount, partialTicks);
	    });
	}

	public static void init(ResourceManager manager) throws IOException
	{
		add(manager, "ripple");
	}
	
	public static void add(ResourceManager manager, String name)
	{
	    ResourceLocation path = new ResourceLocation(BeyondtheAbyss.MODID, "effects/" + name + ".json");
	    EfkEffectData data = loadEfkEfcData(manager, path);
	    EFKEFC.put(name, data);
	}
	
	public static EfkEffectData loadEfkEfcData(ResourceManager mgr, ResourceLocation jsonPath)
	{
	    List<EfkEfcNode> nodes = new ArrayList<>();
	    Map<String, Object> globalProps = new LinkedHashMap<>();
	    try(InputStreamReader reader = new InputStreamReader(mgr.getResource(jsonPath).get().open()))
	    {
	        JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
	        JsonObject project = root.getAsJsonObject("EffekseerProject");
	        if(project.has("StartFrame")) 
	        {
	        	globalProps.put("StartFrame", parseJsonValue(project.get("StartFrame")));
	        }
	        if(project.has("EndFrame")) 
	        {
	        	globalProps.put("EndFrame", parseJsonValue(project.get("EndFrame")));
	        }
	        if(project.has("IsLoop"))
	        {
	        	globalProps.put("IsLoop", parseJsonValue(project.get("IsLoop")));
	        }
	        JsonObject object = project.getAsJsonObject("Root").getAsJsonObject("Children");
	        if(object.get("Node").isJsonArray())
	        {
	        	JsonArray nodeArray = object.getAsJsonArray("Node");
		        for(JsonElement elem : nodeArray) 
		        {
		            EfkEfcNode node = parseNode(elem.getAsJsonObject());
		            nodes.add(node);
		        }
	        }
	        else if(object.get("Node").isJsonObject())
	        {
	            EfkEfcNode node = parseNode(object.getAsJsonObject("Node"));
	            nodes.add(node);
	        }
	    }
	    catch(Exception e) 
	    {
	        e.printStackTrace();
	    }
	    return new EfkEffectData(nodes, globalProps);
	}

    public static EfkEfcNode parseNode(JsonObject json) 
    {
        EfkEfcNode node = new EfkEfcNode();
        node.name = json.has("Name") ? json.get("Name").getAsString() : "(Unnamed)";
        node.properties = new LinkedHashMap<>();
        for(Map.Entry<String, JsonElement> entry : json.entrySet())
        {
            String key = entry.getKey();
            JsonElement value = entry.getValue();
            if(key.equals("Name")) 
            	continue;
            if(key.equals("Children") && value.isJsonObject()) 
            {
                JsonObject childrenObj = value.getAsJsonObject();
                JsonElement nodeElement = childrenObj.get("Node");
                if(nodeElement != null)
                {
                    node.children = new ArrayList<>();
                    if(nodeElement.isJsonArray()) 
                    {
                        for(JsonElement child : nodeElement.getAsJsonArray())
                        {
                            node.children.add(parseNode(child.getAsJsonObject()));
                        }
                    } 
                    else if(nodeElement.isJsonObject()) 
                    {
                        node.children.add(parseNode(nodeElement.getAsJsonObject()));
                    }
                }
                continue;
            }
            node.properties.put(key, parseJsonValue(value));
        }
        return node;
    }

    public static Object parseJsonValue(JsonElement value)
    {
        if(value.isJsonPrimitive())
        {
            JsonPrimitive prim = value.getAsJsonPrimitive();
            if(prim.isBoolean()) {
            	return prim.getAsBoolean();
            }
            if(prim.isNumber()) 
            {
            	return prim.getAsNumber();
            }
            return prim.getAsString();
        }
        else if(value.isJsonObject())
        {
            Map<String, Object> map = new LinkedHashMap<>();
            for(Map.Entry<String, JsonElement> entry : value.getAsJsonObject().entrySet())
            {
                map.put(entry.getKey(), parseJsonValue(entry.getValue()));
            }
            return map;
        } 
        else if(value.isJsonArray())
        {
            List<Object> list = new ArrayList<>();
            for(JsonElement e : value.getAsJsonArray()) 
            {
                list.add(parseJsonValue(e));
            }
            return list;
        }
        return null;
    }
    
    public static class EfkEffectData
    {
        public List<EfkEfcNode> nodes;
        public Map<String, Object> globalProperties;

        public EfkEffectData(List<EfkEfcNode> nodes, Map<String, Object> globalProperties) 
        {
            this.nodes = nodes;
            this.globalProperties = globalProperties;
        }
    }
}