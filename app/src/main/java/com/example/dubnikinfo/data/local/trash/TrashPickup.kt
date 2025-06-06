package com.example.dubnikinfo.data.local.trash

data class TrashPickup(
    val type: Int = 0,
    val date: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now(),
)

fun pickupToEntity(pickup: TrashPickup): TrashEntity {
    return TrashEntity(
        type = pickup.type,
        date = pickup.date.seconds
    )
}

fun entityToPickup(entity: TrashEntity): TrashPickup {
    return TrashPickup(
        type = entity.type,
        date = com.google.firebase.Timestamp(entity.date, 0)
    )
}