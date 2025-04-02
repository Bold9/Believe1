package ie.setu.believe.models

interface BelieveStore {
    fun findAll(): List<BelieveModel>
    fun create(believe: BelieveModel)
    fun update(believe: BelieveModel)
}
