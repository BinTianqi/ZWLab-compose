
fun addZW(input:String, type:String):String{
    val length = input.length
    var count = 0
    var index = 0
    val builder = StringBuilder(input)
    // U+200B: ZW space   U+200C: ZW non-joiner
    // U+200D: ZW joiner   U+200E: Left-to-right mark
    val zw = when(type){"b"->"\u200B";"c"->"\u200C";"d"->"\u200D";"e"->"\u200E";else->" "}
    while(count<length){
        builder.insert(index,zw)
        count+=1
        index+=2
    }
    return builder.toString()
}
