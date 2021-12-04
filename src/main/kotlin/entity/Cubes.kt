package entity

import models.CubeState
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Cubes : IntIdTable() {
    val x = integer("x")
    val y = integer("y")
    val z = integer("z")
    val cubeState = enumeration("cubeState", CubeState::class)
}

class Cube(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Cube>(Cubes)

    var x by Cubes.x
    var y by Cubes.y
    var z by Cubes.z
    var cubeState by Cubes.cubeState

    override fun toString(): String {
        return "Cube(x=$x, y=$y, z=$z, state=$cubeState)"
    }
}