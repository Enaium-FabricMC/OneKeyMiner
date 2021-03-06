/**
 * Copyright 2022 Enaium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.enaium.onekeyminer.util;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

/**
 * @author Enaium
 */
public class BlockUtil {
    public static String getName(Identifier identifier) {
        return identifier.getNamespace() + ":" + identifier.getPath().substring(identifier.getPath().lastIndexOf("/") + 1);
    }

    public static BlockState getBlockState(BlockPos blockPos) {
        return MinecraftClient.getInstance().world.getBlockState(blockPos);
    }
}
