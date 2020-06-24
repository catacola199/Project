package codenetic.kodegiri.coba3.main

import codenetic.kodegiri.coba3.R

object DataSales {
    private val Name = arrayOf(
        "Daily Salesman Bulletin",
        "Review New Product",
        "Weekly Salesman Bulletin",
        "Monthly Salesman Bulletin"


    )
    private val series = arrayOf(
        "kd - 29132",
        "kd - 00123",
        "kd - 23923",
        "kd - 42312"


    )

    private val date = arrayOf(
        "03 Mar 2020",
        "07 Mei 2020",
        "13 Jan 2020",
        "28 Feb 2020"
    )
    private val views = arrayOf(
        "23 Views",
        "1 Views",
        "15 Views",
        "45 Views"
    )


    private val Image = intArrayOf(
        R.drawable.file,
        R.drawable.file,
        R.drawable.file,
        R.drawable.file
    )

    val listData: ArrayList<SalesBulletin_isi>
        get() {
            val list = arrayListOf<SalesBulletin_isi>()
            for (position in Name.indices) {
                val sb = SalesBulletin_isi()
                sb.Title = Name[position]
                sb.Series = series[position]
                sb.datetime = date[position]
                list.add(sb)
            }
            return list
        }
}