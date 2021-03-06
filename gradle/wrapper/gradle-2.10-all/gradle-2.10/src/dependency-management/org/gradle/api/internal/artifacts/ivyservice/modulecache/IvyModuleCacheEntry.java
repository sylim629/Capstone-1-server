/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.artifacts.ivyservice.modulecache;

import org.apache.ivy.core.module.descriptor.ModuleDescriptor;
import org.gradle.api.artifacts.component.ModuleComponentIdentifier;
import org.gradle.internal.component.external.model.DefaultIvyModuleResolveMetaData;
import org.gradle.internal.component.external.model.MutableModuleComponentResolveMetaData;
import org.gradle.internal.component.model.ModuleSource;

import java.math.BigInteger;

class IvyModuleCacheEntry extends ModuleDescriptorCacheEntry {
    public IvyModuleCacheEntry(boolean isChanging, long createTimestamp, BigInteger moduleDescriptorHash, ModuleSource moduleSource) {
        super(TYPE_IVY, isChanging, createTimestamp, moduleDescriptorHash, moduleSource);
    }

    public MutableModuleComponentResolveMetaData createMetaData(ModuleComponentIdentifier componentIdentifier, ModuleDescriptor descriptor) {
        return configure(new DefaultIvyModuleResolveMetaData(componentIdentifier, descriptor));
    }
}
