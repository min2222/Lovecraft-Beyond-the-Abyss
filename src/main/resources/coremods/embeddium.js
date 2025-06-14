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
                var TypeInsnNode = Java.type("org.objectweb.asm.tree.TypeInsnNode");

                var asmapi = Java.type("net.minecraftforge.coremod.api.ASMAPI");
                asmapi.log("INFO", "Patching Embeddium BlockRenderer");

                var methods = classNode.methods;
                for (var i = 0; i < methods.size(); i++) {
                    var method = methods.get(i);
                    if (method.name === "writeGeometry") {
                        // Find the instruction after the Z assignment (original L11)
                        var targetIndex = -1;
                        for (var j = 0; j < method.instructions.size(); j++) {
                            var insn = method.instructions.get(j);

                            // Find the putfield for .z (last original coordinate assignment)
                            if (insn.getOpcode && insn.getOpcode() === Opcodes.PUTFIELD) {
                                if (insn instanceof FieldInsnNode && insn.name === "z") {
                                    targetIndex = j + 1;
                                    break;
                                }
                            }
                        }
                        if (targetIndex === -1) {
                            asmapi.log("WARN", "Couldn't find .z putfield; patch not applied!");
                            continue;
                        }

                        // Prepare our label nodes for branching
                        var labelIfNotUpsideDown = new LabelNode();

                        // Build the injected instructions (see your modified bytecode L12–L15)
                        var inject = new InsnList();

                        // Call MirroredCityUtil.isBlockUpsideDown(ctx.pos(), Minecraft.getInstance().level)
                        inject.add(new VarInsnNode(Opcodes.ALOAD, 1)); // ctx
                        inject.add(new MethodInsnNode(
                            Opcodes.INVOKEVIRTUAL,
                            "me/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderContext",
                            "pos",
                            "()Lnet/minecraft/core/BlockPos;",
                            false
                        ));
                        inject.add(new FieldInsnNode(
                            Opcodes.GETSTATIC,
                            "com/min01/beyondtheabyss/util/BTAClientUtil",
                            "MC",
                            "Lnet/minecraft/client/Minecraft;"
                        ));
                        inject.add(new FieldInsnNode(
                            Opcodes.GETFIELD,
                            "net/minecraft/client/Minecraft",
                            asmapi.mapField("f_91073_"),
                            "Lnet/minecraft/client/multiplayer/ClientLevel;"
                        ));
                        inject.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            "com/min01/beyondtheabyss/util/MirroredCityUtil",
                            "isBlockUpsideDown",
                            "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/Level;)Z",
                            false
                        ));
                        inject.add(new JumpInsnNode(Opcodes.IFEQ, labelIfNotUpsideDown));

                        // -- If upside down: recalculate X
                        inject.add(new VarInsnNode(Opcodes.ALOAD, 13)); // out
                        inject.add(new VarInsnNode(Opcodes.ALOAD, 1)); // ctx
                        inject.add(new MethodInsnNode(
                            Opcodes.INVOKEVIRTUAL,
                            "me/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderContext",
                            "origin",
                            "()Lorg/joml/Vector3fc;",
                            false
                        ));
                        inject.add(new MethodInsnNode(
                            Opcodes.INVOKEINTERFACE,
                            "org/joml/Vector3fc",
                            "x",
                            "()F",
                            true
                        ));
                        inject.add(new InsnNode(Opcodes.FCONST_1));
                        inject.add(new InsnNode(Opcodes.FADD));
                        inject.add(new VarInsnNode(Opcodes.ALOAD, 5)); // quad
                        inject.add(new VarInsnNode(Opcodes.ILOAD, 12)); // srcIndex
                        inject.add(new MethodInsnNode(
                            Opcodes.INVOKEINTERFACE,
                            "me/jellysquid/mods/sodium/client/model/quad/BakedQuadView",
                            "getX",
                            "(I)F",
                            true
                        ));
                        inject.add(new InsnNode(Opcodes.FSUB));
                        inject.add(new VarInsnNode(Opcodes.ALOAD, 3)); // offset
                        inject.add(new MethodInsnNode(
                            Opcodes.INVOKEVIRTUAL,
                            "net/minecraft/world/phys/Vec3",
                            asmapi.mapMethod("m_7096_"),
                            "()D",
                            false
                        ));
                        inject.add(new InsnNode(Opcodes.D2F));
                        inject.add(new InsnNode(Opcodes.FADD));
                        inject.add(new FieldInsnNode(
                            Opcodes.PUTFIELD,
                            "me/jellysquid/mods/sodium/client/render/chunk/vertex/format/ChunkVertexEncoder$Vertex",
                            "x",
                            "F"
                        ));

                        // -- If upside down: recalculate Y
                        inject.add(new VarInsnNode(Opcodes.ALOAD, 13)); // out
                        inject.add(new VarInsnNode(Opcodes.ALOAD, 1)); // ctx
                        inject.add(new MethodInsnNode(
                            Opcodes.INVOKEVIRTUAL,
                            "me/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderContext",
                            "origin",
                            "()Lorg/joml/Vector3fc;",
                            false
                        ));
                        inject.add(new MethodInsnNode(
                            Opcodes.INVOKEINTERFACE,
                            "org/joml/Vector3fc",
                            "y",
                            "()F",
                            true
                        ));
                        inject.add(new InsnNode(Opcodes.FCONST_1));
                        inject.add(new InsnNode(Opcodes.FADD));
                        inject.add(new VarInsnNode(Opcodes.ALOAD, 5)); // quad
                        inject.add(new VarInsnNode(Opcodes.ILOAD, 12)); // srcIndex
                        inject.add(new MethodInsnNode(
                            Opcodes.INVOKEINTERFACE,
                            "me/jellysquid/mods/sodium/client/model/quad/BakedQuadView",
                            "getY",
                            "(I)F",
                            true
                        ));
                        inject.add(new InsnNode(Opcodes.FSUB));
                        inject.add(new VarInsnNode(Opcodes.ALOAD, 3)); // offset
                        inject.add(new MethodInsnNode(
                            Opcodes.INVOKEVIRTUAL,
                            "net/minecraft/world/phys/Vec3",
                            asmapi.mapMethod("m_7098_"),
                            "()D",
                            false
                        ));
                        inject.add(new InsnNode(Opcodes.D2F));
                        inject.add(new InsnNode(Opcodes.FADD));
                        inject.add(new FieldInsnNode(
                            Opcodes.PUTFIELD,
                            "me/jellysquid/mods/sodium/client/render/chunk/vertex/format/ChunkVertexEncoder$Vertex",
                            "y",
                            "F"
                        ));

                        inject.add(labelIfNotUpsideDown);

                        // Insert after the z assignment
                        method.instructions.insert(method.instructions.get(targetIndex - 1), inject);
                        asmapi.log("INFO", "Successfully patched writeGeometry for upside-down check.");
                    }
					if (method.name === "isFaceVisible") {
						var instructions = method.instructions;
						var inject = new InsnList();
						var labelContinue = new LabelNode();

						// ctx is argument 1 (index 1), face is argument 2 (index 2)
						inject.add(new VarInsnNode(Opcodes.ALOAD, 1)); // ctx
						inject.add(new MethodInsnNode(
						    Opcodes.INVOKEVIRTUAL,
						    "me/jellysquid/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderContext",
						    "pos",
						    "()Lnet/minecraft/core/BlockPos;",
						    false
						));
						inject.add(new FieldInsnNode(
						    Opcodes.GETSTATIC,
						    "com/min01/beyondtheabyss/util/BTAClientUtil",
						    "MC",
						    "Lnet/minecraft/client/Minecraft;"
						));
						inject.add(new FieldInsnNode(
						    Opcodes.GETFIELD,
						    "net/minecraft/client/Minecraft",
						    asmapi.mapField("f_91073_"),
						    "Lnet/minecraft/client/multiplayer/ClientLevel;"
						));
						inject.add(new MethodInsnNode(
						    Opcodes.INVOKESTATIC,
						    "com/min01/beyondtheabyss/util/MirroredCityUtil",
						    "isBlockUpsideDown",
						    "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/Level;)Z",
						    false
						));
						inject.add(new JumpInsnNode(Opcodes.IFEQ, labelContinue));
						// If true, return true
						inject.add(new InsnNode(Opcodes.ICONST_1));
						inject.add(new InsnNode(Opcodes.IRETURN));
						// Else continue
						inject.add(labelContinue);

						// Insert at the beginning
						instructions.insert(inject);

						asmapi.log("INFO", "Patched isFaceVisible to check upside-down blocks.");
					}
                }

                return classNode;
            }
        }
    };
}