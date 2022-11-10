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
            LatLng(-7.9662245,112.6317123),
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
            LatLng(-7.9388781,112.6056287),
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
            LatLng(-7.9511815,112.6199206),
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
            LatLng(-7.9490856,112.6305077),
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
            LatLng(-7.9505644,112.6303137),
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
            LatLng(-7.9503696,112.6287766),
            null,
            null
        )
    )

    val completedMission = arrayListOf<Mission>(
        // TODO: INSERT DUMMY DATA HERE
    )
}