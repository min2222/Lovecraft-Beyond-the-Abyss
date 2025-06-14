function initializeCoreMod() {
    return {
        'embeddium-patch': {
            'target': {
                'type': 'CLASS',
                'name': 'me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer'
            },
            'transformer': function(classNode) {
				var Opcodes = Java.type("org.objectweb.asm.Opcodes");
				var InsnList = Java.type("org.objectweb.asm.tree.InsnList");
				var VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode");
				var MethodInsnNode = Java.type("org.objectweb.asm.tree.MethodInsnNode");
				var FieldInsnNode = Java.type("org.objectweb.asm.tree.FieldInsnNode");
				var JumpInsnNode = Java.type("org.objectweb.asm.tree.JumpInsnNode");
				var LabelNode = Java.type("org.objectweb.asm.tree.LabelNode");
				var InsnNode = Java.type("org.objectweb.asm.tree.InsnNode");
				var LdcInsnNode = Java.type("org.objectweb.asm.tree.LdcInsnNode");
				var asmapi = Java.type("net.minecraftforge.coremod.api.ASMAPI");

				asmapi.log("INFO", "Patching Embeddium BlockRenderer");

				var methods = classNode.methods;
				for (var i = 0; i < methods.size(); i++) {
				    var method = methods.get(i);
				    // Patch for writeGeometry
				    if (method.name === "writeGeometry" && method.desc === "(Lme/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderContext;Lme/jellysquid/mods/sodium/client/render/chunk/compile/buffers/ChunkModelBuilder;Lnet/minecraft/world/phys/Vec3;Lme/jellysquid/mods/sodium/client/render/chunk/terrain/material/Material;Lme/jellysquid/mods/sodium/client/model/quad/BakedQuadView;[ILme/jellysquid/mods/sodium/client/model/light/data/QuadLightData;)V") {
				        asmapi.log("INFO", "Found writeGeometry method");
				        
				        // Find the instruction sequence after setting the z coordinate (after L11)
				        var instructions = method.instructions;
				        var iterator = instructions.iterator();
				        var targetNode = null;
				        
				        while (iterator.hasNext()) {
				            var node = iterator.next();
				            // Look for the putfield instruction that sets the z coordinate
				            if (node.getOpcode() === Opcodes.PUTFIELD && 
				                node.name === "z" && 
				                node.owner === "me/jellysquid/mods/sodium/client/render/chunk/vertex/format/ChunkVertexEncoder$Vertex") {
				                targetNode = node;
				                break;
				            }
				        }
				        
				        if (targetNode) {
				            asmapi.log("INFO", "Found z coordinate setting, inserting upside-down check");
				            
				            var newInstructions = new InsnList();
				            
				            // Create labels for the if statement
				            var skipLabel = new LabelNode();
				            
				            // Load context (arg1) to get pos()
				            newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 1)); // ctx
				            newInstructions.add(new MethodInsnNode(
				                Opcodes.INVOKEVIRTUAL,
				                "me/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderContext",
				                "pos",
				                "()Lnet/minecraft/core/BlockPos;",
				                false
				            ));
				            
				            // Load Minecraft level
				            newInstructions.add(new FieldInsnNode(
				                Opcodes.GETSTATIC,
				                "com/min01/beyondtheabyss/util/BTAClientUtil",
				                "MC",
				                "Lnet/minecraft/client/Minecraft;"
				            ));
				            newInstructions.add(new FieldInsnNode(
				                Opcodes.GETFIELD,
				                "net/minecraft/client/Minecraft",
				                asmapi.mapField("f_91074_"), // level field
				                "Lnet/minecraft/client/multiplayer/ClientLevel;"
				            ));
				            
				            // Call isBlockUpsideDown
				            newInstructions.add(new MethodInsnNode(
				                Opcodes.INVOKESTATIC,
				                "com/min01/beyondtheabyss/util/MirroredCityUtil",
				                "isBlockUpsideDown",
				                "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/Level;)Z",
				                false
				            ));
				            
				            // If false, jump to skipLabel
				            newInstructions.add(new JumpInsnNode(Opcodes.IFEQ, skipLabel));
				            
				            // Inside if block - modify x and y coordinates
				            // Modify x coordinate (1.0 - quad.getX(srcIndex))
				            newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 13)); // out
				            newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 1));  // ctx
				            newInstructions.add(new MethodInsnNode(
				                Opcodes.INVOKEVIRTUAL,
				                "me/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderContext",
				                "origin",
				                "()Lorg/joml/Vector3fc;",
				                false
				            ));
				            newInstructions.add(new MethodInsnNode(
				                Opcodes.INVOKEINTERFACE,
				                "org/joml/Vector3fc",
				                "x",
				                "()F",
				                true
				            ));
				            newInstructions.add(new LdcInsnNode(1.0));
				            newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 5));  // quad
				            newInstructions.add(new VarInsnNode(Opcodes.ILOAD, 12)); // srcIndex
				            newInstructions.add(new MethodInsnNode(
				                Opcodes.INVOKEINTERFACE,
				                "me/jellysquid/mods/sodium/client/model/quad/BakedQuadView",
				                "getX",
				                "(I)F",
				                true
				            ));
				            newInstructions.add(new InsnNode(Opcodes.FSUB));
				            newInstructions.add(new InsnNode(Opcodes.FADD));
				            newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 3));  // offset
				            newInstructions.add(new MethodInsnNode(
				                Opcodes.INVOKEVIRTUAL,
				                "net/minecraft/world/phys/Vec3",
				                asmapi.mapMethod("m_7096_"),
				                "()D",
				                false
				            ));
				            newInstructions.add(new InsnNode(Opcodes.D2F));
				            newInstructions.add(new InsnNode(Opcodes.FADD));
				            newInstructions.add(new FieldInsnNode(
				                Opcodes.PUTFIELD,
				                "me/jellysquid/mods/sodium/client/render/chunk/vertex/format/ChunkVertexEncoder$Vertex",
				                "x",
				                "F"
				            ));
				            
				            // Modify y coordinate (1.0 - quad.getY(srcIndex))
				            newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 13)); // out
				            newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 1));  // ctx
				            newInstructions.add(new MethodInsnNode(
				                Opcodes.INVOKEVIRTUAL,
				                "me/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderContext",
				                "origin",
				                "()Lorg/joml/Vector3fc;",
				                false
				            ));
				            newInstructions.add(new MethodInsnNode(
				                Opcodes.INVOKEINTERFACE,
				                "org/joml/Vector3fc",
				                "y",
				                "()F",
				                true
				            ));
				            newInstructions.add(new LdcInsnNode(1.0));
				            newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 5));  // quad
				            newInstructions.add(new VarInsnNode(Opcodes.ILOAD, 12)); // srcIndex
				            newInstructions.add(new MethodInsnNode(
				                Opcodes.INVOKEINTERFACE,
				                "me/jellysquid/mods/sodium/client/model/quad/BakedQuadView",
				                "getY",
				                "(I)F",
				                true
				            ));
				            newInstructions.add(new InsnNode(Opcodes.FSUB));
				            newInstructions.add(new InsnNode(Opcodes.FADD));
				            newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 3));  // offset
				            newInstructions.add(new MethodInsnNode(
				                Opcodes.INVOKEVIRTUAL,
				                "net/minecraft/world/phys/Vec3",
				                asmapi.mapMethod("m_7098_"),
				                "()D",
				                false
				            ));
				            newInstructions.add(new InsnNode(Opcodes.D2F));
				            newInstructions.add(new InsnNode(Opcodes.FADD));
				            newInstructions.add(new FieldInsnNode(
				                Opcodes.PUTFIELD,
				                "me/jellysquid/mods/sodium/client/render/chunk/vertex/format/ChunkVertexEncoder$Vertex",
				                "y",
				                "F"
				            ));
				            
				            // Skip label
				            newInstructions.add(skipLabel);
				            
				            // Insert the new instructions after setting the z coordinate
				            method.instructions.insert(targetNode, newInstructions);
				            asmapi.log("INFO", "Injected writeGeometry upside-down patch");
				        } else {
				            asmapi.log("INFO", "Could not find target location in writeGeometry");
				        }
				    }
					if (method.name === "isFaceVisible") {
						// Inject at the start of the method
						var insn = new InsnList();
						var continueLabel = new LabelNode();

						// ctx.pos()
						insn.add(new VarInsnNode(Opcodes.ALOAD, 1)); // load ctx (index 1)
						insn.add(new MethodInsnNode(
						    Opcodes.INVOKEVIRTUAL,
						    "me/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderContext",
						    "pos",
						    "()Lnet/minecraft/core/BlockPos;",
						    false
						));

						// BTAClientUtil.MC
						insn.add(new FieldInsnNode(
						    Opcodes.GETSTATIC,
						    "com/min01/beyondtheabyss/util/BTAClientUtil", // <-- Replace with your actual package
						    "MC",
						    "Lnet/minecraft/client/Minecraft;"
						));

						// BTAClientUtil.MC.level
						insn.add(new FieldInsnNode(
						    Opcodes.GETFIELD,
						    "net/minecraft/client/Minecraft",
						    asmapi.mapField("f_91073_"),
						    "Lnet/minecraft/client/multiplayer/ClientLevel;"
						));

						// MirroredCityUtil.isBlockUpsideDown(pos, level)
						insn.add(new MethodInsnNode(
						    Opcodes.INVOKESTATIC,
						    "com/min01/beyondtheabyss/util/MirroredCityUtil", // <-- Replace with actual package
						    "isBlockUpsideDown",
						    "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/Level;)Z",
						    false
						));

						// if false, jump to continue
						insn.add(new JumpInsnNode(Opcodes.IFEQ, continueLabel));

						// return true
						insn.add(new InsnNode(Opcodes.ICONST_1));
						insn.add(new InsnNode(Opcodes.IRETURN));

						// continue label
						insn.add(continueLabel);

						method.instructions.insert(insn);
						
						asmapi.log("INFO", "Injected isFaceVisible upside-down patch");
					}
				}
                return classNode;
            }
        }
    };
}