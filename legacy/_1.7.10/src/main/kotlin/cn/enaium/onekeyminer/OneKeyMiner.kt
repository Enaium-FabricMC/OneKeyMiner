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

package cn.enaium.onekeyminer

import cn.enaium.onekeyminer.callback.FinishMiningCallback
import cn.enaium.onekeyminer.callback.UseOnBlockCallback
import cn.enaium.onekeyminer.callback.impl.FinishMiningCallbackImpl
import cn.enaium.onekeyminer.callback.impl.UseOnBlockCallbackImpl
import cn.enaium.onekeyminer.command.OneKeyMinerCommand
import net.legacyfabric.fabric.api.registry.CommandRegistry

fun initializer() {
    println("Hello OneKeyMiner world!")
    CommandRegistry.INSTANCE.register(OneKeyMinerCommand())
    FinishMiningCallback.EVENT.register(FinishMiningCallbackImpl())
    UseOnBlockCallback.EVENT.register(UseOnBlockCallbackImpl())

    Config.load()
    Runtime.getRuntime().addShutdownHook(Thread(Config::save))
}

fun client() {

}