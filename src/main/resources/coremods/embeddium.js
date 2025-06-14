function initializeCoreMod() {
    return {
        'embeddium-patch': {
            'target': {
                'type': 'CLASS',
                'name': 'me.jellysquid.mods.sodium.mixin.MixinConfig'
            },
            'transformer': function(classNode) {
                var Opcodes = Java.type("org.objectweb.asm.Opcodes");
                var MethodInsnNode = Java.type("org.objectweb.asm.tree.MethodInsnNode");
                var InsnList = Java.type("org.objectweb.asm.tree.InsnList");
                var LdcInsnNode = Java.type("org.objectweb.asm.tree.LdcInsnNode");
                var VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode");
                var InsnNode = Java.type("org.objectweb.asm.tree.InsnNode");
                var asmapi = Java.type("net.minecraftforge.coremod.api.ASMAPI");

                asmapi.log("BTA", "Patching Embeddium MixinConfig");

                // Find the constructor
                var methods = classNode.methods;
                for (var i = 0; i < methods.size(); i++) {
                    var method = methods.get(i);
                    
                    if (method.name === "<init>") { // Constructor
                        var instructions = method.instructions;
                        
                        // Find the RETURN opcode at the end of the constructor
                        var returnInsn = null;
                        for (var j = 0; j < instructions.size(); j++) {
                            var insn = instructions.get(j);
                            if (insn.getOpcode() === Opcodes.RETURN) {
                                returnInsn = insn;
                                break;
                            }
                        }
                        
                        if (returnInsn) {
                            // Create instructions to add before RETURN
                            var newInstructions = new InsnList();
                            
                            // this.addMixinRule("core.render.world", true);
                            newInstructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
                            newInstructions.add(new LdcInsnNode("core.render.world"));
                            newInstructions.add(new InsnNode(Opcodes.ICONST_1));
                            newInstructions.add(new MethodInsnNode(
                                Opcodes.INVOKEVIRTUAL,
                                "me/jellysquid/mods/sodium/mixin/MixinConfig",
                                "addMixinRule",
                                "(Ljava/lang/String;Z)V",
                                false
                            ));
                            
                            // Insert the new instructions before the RETURN
                            instructions.insertBefore(returnInsn, newInstructions);
                        }
                        break;
                    }
                }

                return classNode;
            }
        }
    };
}