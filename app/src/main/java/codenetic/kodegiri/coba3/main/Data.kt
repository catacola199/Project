package codenetic.kodegiri.coba3.main

import codenetic.kodegiri.coba3.R

object Data {
    private val appsName = arrayOf(
        "Mobile Apps",
        "Dekstop Apps",
        "Website Apps",
        "Service Apps"


    )

    private val appsDetails = arrayOf(
        "Mobile Apps adalah sebuah aplikasi yang dibuat untuk perangkat mobile yang penggunanya harus melakukan pengunduhan atau download serta menginstall dari toko aplikasi seperti Google Play Store untuk perangkat Android dan Apple App Store untuk perangkat iOS dan masih banyak lagi toko aplikasi yang sesuai dengan platform smartphone.",
        "Dekstop Apps adalah aplikasi berbasis desktop yang berjalan di satu atau beberapa komputer (terhubung dalam satu jaringan) secara independen dan tidak memerlukan browser. ",
        "Website Apps adalah aplikasi yang dibuat berbasis web yang membutuhkan web server dan browser untuk menjalankannya.",
        "Service Apps adalah Maintenance sebuah aplikasi yang sudah dibuat oleh pihak perusahaan maupun pihak luar.")

    private val appsImages = intArrayOf(
        R.drawable.mobile,
        R.drawable.dekstop,
        R.drawable.website,
        R.drawable.service
    )

    val listData: ArrayList<catalog_isi>
        get() {
            val list = arrayListOf<catalog_isi>()
            for (position in appsName.indices) {
                val apps = catalog_isi()
                apps.name = appsName[position]
                apps.detail = appsDetails[position]
                apps.photo = appsImages[position]
                list.add(apps)
            }
            return list
        }
}