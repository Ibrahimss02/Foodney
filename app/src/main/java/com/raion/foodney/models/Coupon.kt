package com.raion.foodney.models

import com.raion.foodney.R

data class Coupon(
    val id: String,
    val name: String,
    val couponDesc: String,
    val cost: Int,
    val image: Int?
) {
    constructor() : this("", "", "", 0, null)
}

object CouponDummy {
    val couponData = arrayListOf(
        // TODO: INSERT DUMMY DATA HERE
        Coupon(
            "coupon_01",
            "Mie Bakar Celaket",
            "Beli makanan apa saja dan dapatkan potongan sebesar Rp 10.000,00",
            60,
            R.drawable.iv_mie_bakar_celaket
        ),
        Coupon(
            "coupon_02",
            "Pecel Sambal Tumpang Bu Djarot",
            "Beli makanan apa saja dan dapatkan potongan sebesar Rp 5.000,00",
            45,
            R.drawable.iv_pecel_sambal_tumpang_bu_djarot
        ),
        Coupon(
            "coupon_03",
            "Es Setrup \" Slamet \"",
            "Beli makanan apa saja dan dapatkan potongan sebesar Rp 7.500,00",
            75,
            R.drawable.iv_es_setrup_slamet
        ),
        Coupon(
            "coupon_04",
            "Lalapan Cak Midi",
            "Beli makanan apa saja dan dapatkan potongan sebesar Rp 8.000,00",
            80,
            R.drawable.iv_lalapan_cak_midi
        ),
        Coupon(
            "coupon_05",
            "Lalapan Belut Fresh",
            "Beli makanan apa saja dan dapatkan potongan sebesar Rp 15.000,00",
            35,
            R.drawable.iv_lalapan_belut_fresh
        ),
        Coupon(
            "coupon_06",
            "Tahu Campur Dan Tahu Telor Cak Roon",
            "Beli makanan apa saja dan dapatkan potongan sebesar Rp 10.000,00",
            87,
            R.drawable.iv_tahu_campur_dan_telor_cak_roon
        )
    )
}
