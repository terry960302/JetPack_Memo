package com.ritier.my_memo.Model

data class MemoModel(val thumbnailPath : String?, val time : String, val desc : String) {
    constructor() : this(null, "", "")
}