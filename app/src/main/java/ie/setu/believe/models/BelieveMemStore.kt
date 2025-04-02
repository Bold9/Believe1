package ie.setu.believe.models

import timber.log.Timber.i

var lastId = 0L
internal fun getId() = lastId++

class BelieveMemStore : BelieveStore {

    val believe = ArrayList<BelieveModel>()

    override fun findAll(): List<BelieveModel> {
        return believe
    }

    override fun create(believe: BelieveModel) {
        believe.id = getId()
        believe.add(believe)
        logAll()
    }

    override fun update(believe: BelieveModel) {
        var foundBelieve: BelieveModel? = believe.find { p -> p.id == believe.id }
        if (foundBelieve != null) {
            foundBelieve.title = believe.title
            foundBelieve.description = believe.description
            foundBelieve.image = believe.image
            logAll()
        }
    }

    private fun logAll() {
        believe.forEach { i("$it") }
    }
}