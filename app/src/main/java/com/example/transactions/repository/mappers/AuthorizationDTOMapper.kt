package com.example.transactions.repository.mappers


import com.example.transactions.repository.dto.AuthRequestDTO
import com.example.transactions.ui.vos.TransactionVO

class AuthorizationDTOMapper : GenericVOMapper<AuthRequestDTO, TransactionVO>() {

    override fun VOToBussiness(business: TransactionVO): AuthRequestDTO {
        return AuthRequestDTO(
            id = business.id,
            commerceCode = business.commerceCode,
            terminalCode = business.terminalCode,
            amount = business.amount,
            card = business.card
        )
    }

    override fun bussinessToVO(vo: AuthRequestDTO): TransactionVO {
        return TransactionVO(
            id = vo.id,
            commerceCode = vo.commerceCode,
            terminalCode = vo.terminalCode,
            amount = vo.amount,
            card = vo.card
        )
    }

}