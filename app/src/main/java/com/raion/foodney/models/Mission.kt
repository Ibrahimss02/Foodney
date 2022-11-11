package com.raion.foodney.models

import com.google.android.gms.maps.model.LatLng

data class Mission(
    val id: String,
    val name: String,
    val coinReward: Int,
    var distance: Int? = null,
    val address: String,
    val rating: Float,
    val latLng: LatLng,
    val photoUrls: List<String>?,
    val reviews: List<Review>?
)

object MissionDummy {
    val missionData = arrayListOf(
        Mission(
            "mission_01",
            "Mie Bakar Celaket",
            120,
            null,
            "Jl. Jaksa Agung Suprapto Gg. 1 No.22, Samaan, Kec. Klojen, Kota Malang",
            4.8f,
            LatLng(-7.966209220384015, 112.63171430887834),
            null,
            null
        ),
        Mission(
            "mission_02",
            "Pecel Sambal Tumpang Bu Djarot",
            80,
            null,
            "Jl. MT. Haryono No.167, Dinoyo, Lowokwaru, Malang City, East Java 65144",
            4.9f,
            LatLng(-7.938878727084412, 112.60782401801859),
            null,
            null
        ),
        Mission(
            "mission_03",
            "Es Setrup \" Slamet \"",
            110,
            null,
            "Jl. Dewandaru No.70, Jatimulyo, Kec. Lowokwaru, Kota Malang, Jawa Timur 65141",
            4.3f,
            LatLng(-7.950389729124782, 112.62315515633314),
            null,
            null
        ),
        Mission(
            "mission_04",
            "Lalapan Cak Midi",
            50,
            null,
            "Jl. Cengger Ayam No.6, RW.02, Jatimulyo, Kec. Lowokwaru, Kota Malang, Jawa Timur 65141",
            4.8f,
            LatLng(-7.950111332655112, 112.63159937943044),
            null,
            null
        ),
        Mission(
            "mission_05",
            "Lalapan Belut Fresh",
            120,
            null,
            "Jl. Kalpataru No.25, Jatimulyo, Kec. Lowokwaru, Kota Malang, Jawa Timur 65141",
            4.3f,
            LatLng(-7.950764034885629, 112.63107697268342),
            null,
            null
        ),
        Mission(
            "mission_06",
            "Tahu Campur Dan Tahu Telor Cak Roon",
            60,
            null,
            "Jl. Kalpataru No.67, Jatimulyo, Kec. Lowokwaru, Kota Malang, Jawa Timur 65141",
            4.8f,
            LatLng(-7.949638468151272, 112.62848042778606),
            null,
            null
        )
    )

    val completedMission = arrayListOf<Mission>(
        // TODO: INSERT DUMMY DATA HERE
    )
}