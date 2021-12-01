package models

import FIELD_SIZE

object FieldDsl {
    fun battleField(fieldSize: Int = FIELD_SIZE, code: BattleField.() -> Unit) {
        val battleField = BattleField(fieldSize)
        battleField.code()
    }
}