package com.adventure.apis.store

import com.adventure.apis.store.State.StoreCategory
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDateTime
import java.util.UUID

class Commands {
    data class CreateStore(
        @TargetAggregateIdentifier
        val storeId: UUID,
        val sellerId: UUID,
        val category: StoreCategory,
        val storeName: String,
    )

    data class AddStock(
        @TargetAggregateIdentifier
        val storeId: UUID,
        val sellerId: UUID,
        val productId: UUID,
        val productName: String,
        val productCategory: String,
        val productDescription: String,
        val price: Double,
        val remainingQuantity: Int,
        val timeAdded: LocalDateTime
    )
    data class CloseStore(@TargetAggregateIdentifier val storeId: UUID)

    data class ReOpenStore(@TargetAggregateIdentifier val storeId: UUID)
}