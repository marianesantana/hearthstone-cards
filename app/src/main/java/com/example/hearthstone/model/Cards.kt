package com.example.hearthstone.model


data class SingleCard(val flavor: String,
                      val name: String,
                      val cardSet: String,
                      val type: String,
                      val faction: String,
                      val rarity: String,
                      val cost: Int,
                      val attack: Int,
                      val health: Int,
                      val text: String,
                      val img: String,
                      val artist: String,
                      val race: String,
                      val cardId: Int
                      )

data class CardBacks(val cardBackId: Int,
                    val name: String,
                    val description: String,
                    val source: Int,
                    val img: String,
                    val imgAnimated: String,

)

/*
A imagem principal do cartão
Nome
Descrição "flavor"
Descrição curta
Set pertencente - get info
Tipo - get info
Facção - get info
Raridade - getallcards
Ataque
Custo - getallcards
Health
 */