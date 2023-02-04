package com.github.md5sha256.spigotutils;

import io.papermc.lib.PaperLib;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

public final class PaperUtils {

    private PaperUtils() {
        throw new IllegalStateException("Cannot init util class");
    }

    public static BlockState[] getTileEntities(Chunk chunk, boolean useSnapshot) {
        if (PaperLib.isPaper()) {
            return chunk.getTileEntities(useSnapshot);
        }
        return chunk.getTileEntities();
    }

    public static Collection<BlockState> getTileEntities(Chunk chunk, Predicate<Block> blockPredicate, boolean useSnapshot) {
        if (PaperLib.isPaper()) {
            return chunk.getTileEntities(blockPredicate, useSnapshot);
        }
        return Arrays.asList(chunk.getTileEntities());
    }


}
