/*
 * Copyright 2022 Enaium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.enaium.onekeyminer.mixin;

import cn.enaium.onekeyminer.Config;
import cn.enaium.onekeyminer.util.BlockUtil;
import net.minecraft.item.*;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {
    @Shadow
    public ServerPlayerEntity player;
    @Shadow
    public ServerWorld world;

    @Shadow
    public abstract boolean tryBreakBlock(BlockPos pos);

    @Inject(at = @At(value = "HEAD"), method = "method_21717")
    private void finishMining(BlockPos pos, PlayerActionC2SPacket.Action action, CallbackInfo ci) {
        ItemStack stack = player.inventory.getInvStack(player.inventory.selectedSlot);
        if (stack != null) {
            boolean canMine = stack.getItem().canMine(world.getBlockState(pos), world, pos, player);
            if (canMine && (stack.getItem() instanceof MiningToolItem || stack.getItem() instanceof ShearsItem) && player.isSneaking()) {
                Config.Model config = Config.getModel();
                List<String> list = new ArrayList<>();
                if (stack.getItem() instanceof AxeItem) {
                    list.addAll(config.axe);
                } else if (stack.getItem() instanceof HoeItem) {
                    list.addAll(config.hoe);
                } else if (stack.getItem() instanceof PickaxeItem) {
                    list.addAll(config.pickaxe);
                } else if (stack.getItem() instanceof ShovelItem) {
                    list.addAll(config.shovel);
                } else if (stack.getItem() instanceof ShearsItem) {
                    list.addAll(config.shears);
                }
                final String name = BlockUtil.getName(world, pos);
                if (list.contains(name)) {
                    breakBreakBlock(name, pos, pos, config.limit, new ArrayList<>());
                }
            }
        }
    }


    private void breakBreakBlock(String name, BlockPos target, BlockPos blockPos, int limit, List<BlockPos> searched) {
        int radius = 1;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos newBlockPos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                    final double cbrt = Math.ceil(Math.cbrt(limit));
                    if (name.equals(BlockUtil.getName(world, newBlockPos))
                            && !searched.contains(newBlockPos)
                            && searched.size() < (cbrt * cbrt * cbrt)) {

                        if ((Config.getModel().pickaxe.contains(name) || Config.getModel().shovel.contains(name)) && newBlockPos.getManhattanDistance(target) > cbrt) {
                            break;
                        }

                        searched.add(newBlockPos);
                        tryBreakBlock(newBlockPos);
                        breakBreakBlock(name, target, newBlockPos, limit, searched);
                    }
                }
            }
        }
    }
}
