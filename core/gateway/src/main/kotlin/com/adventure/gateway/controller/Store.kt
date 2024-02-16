package com.adventure.gateway.controller

import com.adventure.apis.store.Commands
import com.adventure.apis.store.QueryResults
import com.adventure.apis.store.Requests.*
import com.adventure.gateway.utils.Mappings.ADD_STOCK_MAPPING
import com.adventure.gateway.utils.Mappings.CREATE_STORE_MAPPING
import com.adventure.gateway.utils.Mappings.MANAGE_STORE_MAPPING
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.*

@RestController
class  Store(private val command: ReactorCommandGateway) {
    @PostMapping(CREATE_STORE_MAPPING)

    fun createStore(
        @PathVariable("seller_id") sellerId: UUID,
        @RequestBody request: CreateStoreRequest
    ): Mono<ResponseEntity<String>> {
        val createStoreCommand = Commands.CreateStore(
            storeId = UUID.randomUUID(),
            sellerId = sellerId,
            category = request.category,
            storeName = request.storeName
        )
        return command.send<ResponseEntity<String>>(createStoreCommand)
            .then()
            .thenReturn(ResponseEntity.ok("Successfully Created your store"))
    }

    @PostMapping(ADD_STOCK_MAPPING)
    fun addStock(
        @PathVariable("seller_id") sellerId: UUID,
        @PathVariable("store_id") storeId: UUID,
        @RequestBody request: AddStockRequest
    ): Mono<ResponseEntity<String>> {
        val addStockCommand = Commands.AddStock(
            sellerId = sellerId,
            storeId = storeId,
            productId = UUID.randomUUID(),
            productName = request.productName,
            productCategory = request.productCategory,
            productDescription = request.productDescription,
            price = request.price,
            remainingQuantity = request.quantity,
            likes = request.likes,
            timeAdded = request.timeAdded
        )
        return command.send<ResponseEntity<String>>(addStockCommand)
            .then()
            .thenReturn(ResponseEntity.ok("${request.productName} Added"))
    }

    @GetMapping(MANAGE_STORE_MAPPING)
    fun manageStore(@PathVariable("store_id") storeId: UUID): Mono<ResponseEntity<QueryResults.ManageStoreQueryResults>> {
        TODO()
    }
}