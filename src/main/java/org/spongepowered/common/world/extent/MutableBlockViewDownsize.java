/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.common.world.extent;

import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.util.DiscreteTransform3;
import org.spongepowered.api.world.extent.ImmutableBlockVolume;
import org.spongepowered.api.world.extent.MutableBlockVolume;
import org.spongepowered.api.world.extent.UnmodifiableBlockVolume;
import org.spongepowered.api.world.extent.worker.MutableBlockVolumeWorker;
import org.spongepowered.common.util.gen.ArrayImmutableBlockBuffer;
import org.spongepowered.common.world.extent.worker.SpongeMutableBlockVolumeWorker;
import org.spongepowered.common.world.schematic.GlobalPalette;

public class MutableBlockViewDownsize extends AbstractBlockViewDownsize<MutableBlockVolume> implements MutableBlockVolume {

    public MutableBlockViewDownsize(MutableBlockVolume volume, Vector3i min, Vector3i max) {
        super(volume, min, max);
    }

    @Override
    public boolean setBlock(int x, int y, int z, BlockState block) {
        checkRange(x, y, z);
        return this.volume.setBlock(x, y, z, block);
    }

    @Override
    public MutableBlockVolume getBlockView(Vector3i newMin, Vector3i newMax) {
        checkRange(newMin.getX(), newMin.getY(), newMin.getZ());
        checkRange(newMax.getX(), newMax.getY(), newMax.getZ());
        return new MutableBlockViewDownsize(this.volume, newMin, newMax);
    }

    @Override
    public MutableBlockVolume getBlockView(DiscreteTransform3 transform) {
        return new MutableBlockViewTransform(this, transform);
    }

    @Override
    public MutableBlockVolumeWorker<? extends MutableBlockVolume> getBlockWorker() {
        return new SpongeMutableBlockVolumeWorker<>(this);
    }

    @Override
    public UnmodifiableBlockVolume getUnmodifiableBlockView() {
        return new UnmodifiableBlockVolumeWrapper(this);
    }

    @Override
    public ImmutableBlockVolume getImmutableBlockCopy() {
        char[] data = ExtentBufferUtil.copyToArray(this, this.min, this.max, this.size);
        return ArrayImmutableBlockBuffer.newWithoutArrayClone(GlobalPalette.instance, this.min, this.size, data);
    }

}
