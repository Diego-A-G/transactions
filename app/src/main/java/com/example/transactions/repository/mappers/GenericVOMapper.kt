package com.example.transactions.repository.mappers

abstract class GenericVOMapper<V, B> {

    abstract fun VOToBussiness(business: B): V

    abstract fun bussinessToVO(vo: V): B
}