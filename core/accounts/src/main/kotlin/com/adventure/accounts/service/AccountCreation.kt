package com.adventure.accounts.service

import com.adventure.accounts.respository.BuyerRepository
import com.adventure.accounts.respository.SellerRepository
import com.adventure.apis.accounts.Commands.*
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class AccountCreation(
    private val buyerRepository: BuyerRepository,
    private val sellerRepository: SellerRepository
) {
    fun addBuyer(command: CreateBuyerAccount): Mono<String> {
        val buyerDetails = CreateBuyerAccount(
            buyerId = command.buyerId,
            details = UserDetails(
                firstName = command.details.firstName,
                lastName = command.details.lastName,
                dob = command.details.dob,
                email = command.details.email,
                gender = command.details.gender,
                country = command.details.country
            )
        )

        return Mono.fromCallable {
            buyerRepository.save(buyerDetails)
            "Successfully added buyer ${buyerDetails.buyerId}"
        }
    }
    fun addSeller(command: CreateSellerAccount): Mono<String> {
        val sellerDetails = CreateSellerAccount(
            sellerId = command.sellerId,
            details = UserDetails(
                firstName = command.details.firstName,
                lastName = command.details.lastName,
                dob = command.details.dob,
                email = command.details.email,
                gender = command.details.gender,
                country = command.details.country
            )
        )

        return Mono.fromCallable {
            sellerRepository.save(sellerDetails)
            "Successfully added buyer ${sellerDetails.sellerId}"
        }
    }
}