package com.adventure.store.service

import com.adventure.apis.store.Commands
import com.adventure.apis.store.Commands.AddStock
import com.adventure.store.repositories.ProductRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ProductService(private val productRepository: ProductRepository) {
    fun addProduct(command: AddStock): Mono<String> {
        val productDetails = AddStock(
            storeId = command.storeId,
            productId = command.productId,
            productName = command.productName,
            productCategory = command.productCategory,
            productDescription = command.productDescription,
            price = command.price
        )
        return Mono.fromCallable {
            productRepository.addStock(productDetails)
            "Successfully added product ${productDetails.productId}"
        }
    }
}