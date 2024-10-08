package com.adventure.accounts.command

import com.adventure.apis.accounts.Commands.CreateAccount
import com.adventure.apis.accounts.Commands.SuspendAccount
import com.adventure.apis.accounts.Events.AccountCreated
import com.adventure.apis.accounts.Events.AccountSuspended
import com.adventure.apis.accounts.State.*
import com.adventure.apis.accounts.State.AccountStatus.ACTIVE
import com.adventure.apis.accounts.State.AccountStatus.SUSPENDED
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventhandling.gateway.EventGateway
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.extensions.kotlin.applyEvent
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.slf4j.LoggerFactory
import java.util.UUID

@Aggregate
class AccountAggregate() {

    @AggregateIdentifier
    lateinit var accountId: UUID
    lateinit var accountStatus: AccountStatus
    lateinit var accountRole: Role
    private val logger = LoggerFactory.getLogger(this::class.java)

    @CommandHandler
    constructor(command: CreateAccount) : this() {
        logger.info("Handling command $command")
        val accountCreatedEvent = AccountCreated(
            accountId = command.accountId,
            accountStatus = ACTIVE,
            firstName = command.firstName,
            lastName = command.lastName,
            dateOfBirth = command.dateOfBirth,
            email = command.email,
            gender = command.gender,
            country = command.country,
            role = command.role
        )

        applyEvent(accountCreatedEvent)
        logger.info("published event :: $accountCreatedEvent")
    }

    @EventSourcingHandler
    fun handle(event: AccountCreated) {
        logger.info("Updated account status to ${event.accountStatus}")
        accountId = event.accountId
        accountStatus = event.accountStatus
        accountRole = event.role
    }

    @CommandHandler
    fun on(command: SuspendAccount) {
        val accountSuspendedEvent = AccountSuspended(
            accountId = command.accountId,
            accountStatus = SUSPENDED
        )
        if (accountStatus == ACTIVE) {
            applyEvent(accountSuspendedEvent)
        }
    }

    @EventSourcingHandler
    fun handle(event: AccountSuspended) {
        accountId = event.accountId
        accountStatus = event.accountStatus
    }

}