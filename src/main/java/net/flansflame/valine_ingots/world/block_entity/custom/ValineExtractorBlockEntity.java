package net.flansflame.valine_ingots.world.block_entity.custom;

import net.flansflame.valine_ingots.ValineIngots;
import net.flansflame.valine_ingots.client.screen.custom.ValineExtractorMenu;
import net.flansflame.valine_ingots.component.ModComponents;
import net.flansflame.valine_ingots.util.InventoryDirectionEntry;
import net.flansflame.valine_ingots.util.InventoryDirectionWrapper;
import net.flansflame.valine_ingots.util.ModEnergyStorage;
import net.flansflame.valine_ingots.util.WrappedHandler;
import net.flansflame.valine_ingots.world.block.custom.ValineExtractorBlock;
import net.flansflame.valine_ingots.world.block_entity.ModBlockEntities;
import net.flansflame.valine_ingots.world.item.ModItems;
import net.flansflame.valine_ingots.world.recipe.ValineExtractorRecipe;
import net.flansflame.valine_ingots.world.tag.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class ValineExtractorBlockEntity extends BlockEntity implements MenuProvider {

    /*HANDLERS*/
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private final ItemStackHandler itemHandler = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack itemStack) {
            return switch (slot) {
                case 0 -> !itemStack.is(Items.IRON_NUGGET);
                case 1 -> false;
                case 2 -> itemStack.is(Items.COAL);
                case 3 -> itemStack.is(Items.IRON_NUGGET);
                case 4 -> itemStack.is(ModTags.Items.VALINE_MACHINE_UPGRADES);
                default -> super.isItemValid(slot, itemStack);
            };
        }
    };


    /*SLOTS*/
    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;
    public static final int ENERGY_ITEM_SLOT = 2;
    public static final int ADDITIONAL_SLOT = 3;
    public static final int UPGRADE_SLOT = 4;
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            new InventoryDirectionWrapper(itemHandler,
                    new InventoryDirectionEntry(Direction.DOWN, OUTPUT_SLOT, false),
                    new InventoryDirectionEntry(Direction.NORTH, INPUT_SLOT, true),
                    new InventoryDirectionEntry(Direction.SOUTH, OUTPUT_SLOT, false),
                    new InventoryDirectionEntry(Direction.EAST, OUTPUT_SLOT, false),
                    new InventoryDirectionEntry(Direction.WEST, INPUT_SLOT, true),
                    new InventoryDirectionEntry(Direction.UP, INPUT_SLOT, true)).directionsMap;

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize() >=
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count;
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() < this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }


    /*ENERGY_STORAGES*/
    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(20000, 2500) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    };

    public ModEnergyStorage getEnergyStorage() {
        return this.ENERGY_STORAGE;
    }

    private void extractEnergy() {
        this.ENERGY_STORAGE.extractEnergy(10, false);
    }

    private void fillUpOnEnergy() {
        if (hasEnergyItemInSlot(ENERGY_ITEM_SLOT) && this.ENERGY_STORAGE.getMaxEnergyStored() - this.ENERGY_STORAGE.getEnergyStored() >= 1024) {
            this.ENERGY_STORAGE.receiveEnergy(2500, false);
            this.itemHandler.getStackInSlot(ENERGY_ITEM_SLOT).shrink(1);
        }
    }

    private boolean hasEnergyItemInSlot(int energyItemSlot) {
        return !this.itemHandler.getStackInSlot(energyItemSlot).isEmpty() &&
                this.itemHandler.getStackInSlot(energyItemSlot).getItem() == Items.COAL;
    }

    private boolean hasEnoughEnergyToCraft() {
        return this.ENERGY_STORAGE.getEnergyStored() >= 10 * maxProgress;
    }



    /*DATA*/
    protected final ContainerData data;


    /*PROGRESSES*/
    private int progress = 0;
    private int maxProgress = 0;

    private void resetProgress() {
        this.progress = 0;
    }

    private boolean hasProgressFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProcess() {
        this.progress++;
    }


    /*CONSTRUCTOR*/
    public ValineExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VALINE_EXTRACTOR.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ValineExtractorBlockEntity.this.progress;
                    case 1 -> ValineExtractorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ValineExtractorBlockEntity.this.progress = value;
                    case 1 -> ValineExtractorBlockEntity.this.maxProgress = value;
                }
                ;
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }


    /*INITIALISES*/
    @Override
    public Component getDisplayName() {
        return Component.translatable("block." + ValineIngots.MOD_ID + ".valine_extractor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int container, Inventory inventory, Player player) {
        return new ValineExtractorMenu(container, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }
            if (directionWrappedHandlerMap.containsKey(side)) {
                Direction localDirection = this.getBlockState().getValue(ValineExtractorBlock.FACING);
                if (side == Direction.DOWN || side == Direction.UP) {
                    return directionWrappedHandlerMap.get(side).cast();
                }
                return switch (localDirection) {
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }
        }
        return super.getCapability(cap, side);
    }


    /*LOADS & SAVES*/
    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("progress", progress);
        tag.putInt("energy", ENERGY_STORAGE.getEnergyStored());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        progress = tag.getInt("progress");
        ENERGY_STORAGE.setEnergy(tag.getInt("energy"));
        super.load(tag);
    }


    /*DROPS*/
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }


    /*CRAFTING*/
    public void tick(Level level, BlockPos pPos, BlockState pState) {
        fillUpOnEnergy();

        if (isOutputSlotEmptyOrReceivable() && hasRecipe()) {
            increaseCraftingProcess();
            extractEnergy();
            setChanged(level, pPos, pState);

            if (hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void craftItem() {
        Optional<ValineExtractorRecipe> recipe = getCurrentRecipe();
        ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());

        this.itemHandler.extractItem(INPUT_SLOT, 1, false);
        this.itemHandler.extractItem(ADDITIONAL_SLOT, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(resultItem.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + resultItem.getCount()));
    }

    private boolean hasRecipe() {
        Optional<ValineExtractorRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());

        ItemStack upgradeItemStack = this.itemHandler.getStackInSlot(UPGRADE_SLOT);

        if (!upgradeItemStack.isEmpty() && upgradeItemStack.is(ModTags.Items.VALINE_MACHINE_UPGRADES)){
            maxProgress = (int) (getCurrentRecipe().get().getTick() / getProgressMult(upgradeItemStack));
        } else {
            maxProgress = getCurrentRecipe().get().getTick();
        }

        return canInsertAmountIntoOutputSlot(resultItem.getCount())
                && canInsertItemIntoOutputSlot(resultItem.getItem())
                && hasEnoughEnergyToCraft();
    }

    public static float getProgressMult(ItemStack itemStack){
        if (itemStack.is(ModItems.VALINE_MACHINE_UPGRADE_TIER_1.get())){
            return 1.5f;
        } else if (itemStack.is(ModItems.VALINE_MACHINE_UPGRADE_TIER_2.get())) {
            return 2f;
        } else if (itemStack.is(ModItems.VALINE_MACHINE_UPGRADE_TIER_3.get())) {
            return 3f;
        } else if (itemStack.is(ModItems.VALINE_MACHINE_UPGRADE_TIER_4.get())) {
            return 5f;
        } else {
            return 1f;
        }
    }

    private Optional<ValineExtractorRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(ValineExtractorRecipe.Type.INSTANCE, inventory, level);
    }


    /*OTHER-final's*/
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }
}
