package com.example.transactions.repository.mappers

import com.example.transactions.persistence.entities.AuthorizationEntity
import com.example.transactions.ui.vos.TransactionVO

class AuthorizationEntityMapper : GenericVOMapper<AuthorizationEntity, TransactionVO>() {

    override fun VOToBussiness(business: TransactionVO): AuthorizationEntity {
        return AuthorizationEntity(
            id = business.id,
            commerceCode = business.commerceCode,
            terminalCode = business.terminalCode,
            amount = business.amount,
            card = business.card,
            receiptId = "",
            rrn = "",
            statusCode = "",
            statusDescription = ""
        )
    }

    override fun bussinessToVO(vo: AuthorizationEntity): TransactionVO {
        return TransactionVO(
            id = vo.id,
            commerceCode = vo.commerceCode,
            terminalCode = vo.terminalCode,
            amount = vo.amount,
            card = vo.card
        )
    }


}