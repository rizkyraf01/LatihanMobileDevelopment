package com.andan.simplenote

import java.util.*

data class NoteModel(
    var id: Int = getAutoId(),
    var title: String = "",
    var text: String= ""
){
    companion object{
        fun getAutoId(): Int{
            val random = Random()
            return random.nextInt(100)
        }
    }
}
