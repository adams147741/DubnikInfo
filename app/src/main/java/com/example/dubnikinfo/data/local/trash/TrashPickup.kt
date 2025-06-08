package com.example.dubnikinfo.data.local.trash

/**
 * Data class representing a trash pickup
 */
data class TrashPickup(
    val type: Int = 0,
    val date: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now(),
)

/**
 * Converts a TrashPickup to a TrashEntity
 * @param pickup TrashPickup - the trash pickup to be converted
 * @return TrashEntity - the trash entity
 */
fun pickupToEntity(pickup: TrashPickup): TrashEntity {
    return TrashEntity(
        type = pickup.type,
        date = pickup.date.seconds
    )
}

/**
 * Converts a TrashEntity to a TrashPickup
 * @param entity TrashEntity - the trash entity to be converted
 * @return TrashPickup - the trash pickup
 */
fun entityToPickup(entity: TrashEntity): TrashPickup {
    return TrashPickup(
        type = entity.type,
        date = com.google.firebase.Timestamp(entity.date, 0)
    )
}