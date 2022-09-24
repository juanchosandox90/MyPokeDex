package com.sandoval.mypokedex.data.models.pokedex_list

import com.sandoval.mypokedex.domain.models.pokedex_list.DResult

data class Result(
    val name: String?, val url: String?
) {
    fun toDomainObject() = DResult(name = name ?: "", url = url ?: "")
}